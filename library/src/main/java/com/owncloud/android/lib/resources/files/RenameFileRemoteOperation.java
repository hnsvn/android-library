/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2015 ownCloud Inc.
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.files;

import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.operations.RemoteOperation;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.common.operations.RemoteOperationResult.ResultCode;
import com.owncloud.android.lib.common.utils.Log_OC;

import org.apache.jackrabbit.webdav.client.methods.MoveMethod;

import java.io.File;


/**
 * Remote operation performing the rename of a remote file or folder in the ownCloud server.
 *
 * @author David A. Velasco
 * @author masensio
 */
public class RenameFileRemoteOperation extends RemoteOperation {

    private static final String TAG = RenameFileRemoteOperation.class.getSimpleName();

    private static final int RENAME_READ_TIMEOUT = 600000;
    private static final int RENAME_CONNECTION_TIMEOUT = 5000;

    private String mOldName;
    private String mOldRemotePath;
    private String mNewName;
    private String mNewRemotePath;


    /**
     * Constructor
     *
     * @param oldName       Old name of the file.
     * @param oldRemotePath Old remote path of the file.
     * @param newName       New name to set as the name of file.
     * @param isFolder      'true' for folder and 'false' for files
     */
    public RenameFileRemoteOperation(String oldName, String oldRemotePath, String newName,
                                     boolean isFolder) {
        mOldName = oldName;
        mOldRemotePath = oldRemotePath;
        mNewName = newName;

        String parent = (new File(mOldRemotePath)).getParent();
        parent = (parent.endsWith(FileUtils.PATH_SEPARATOR)) ? parent : parent +
            FileUtils.PATH_SEPARATOR;
        mNewRemotePath = parent + mNewName;
        if (isFolder) {
            mNewRemotePath += FileUtils.PATH_SEPARATOR;
        }
    }

    /**
     * Performs the rename operation.
     *
     * @param client Client object to communicate with the remote ownCloud server.
     */
    @Override
    protected RemoteOperationResult run(OwnCloudClient client) {
        RemoteOperationResult result = null;

        MoveMethod move = null;
        try {
            if (mNewName.equals(mOldName)) {
                return new RemoteOperationResult(ResultCode.OK);
            }

            // check if a file with the new name already exists
            RemoteOperationResult existenceResult = new ExistenceCheckRemoteOperation(mNewRemotePath, false)
                    .execute(client);
            if (existenceResult.isSuccess()) {
                return new RemoteOperationResult(ResultCode.INVALID_OVERWRITE);
            }

            move = new MoveMethod(client.getFilesDavUri(mOldRemotePath),
                    client.getFilesDavUri(mNewRemotePath), true);
            client.executeMethod(move, RENAME_READ_TIMEOUT, RENAME_CONNECTION_TIMEOUT);
            result = new RemoteOperationResult(move.succeeded(), move);
            Log_OC.i(TAG, "Rename " + mOldRemotePath + " to " + mNewRemotePath + ": " +
                    result.getLogMessage()
            );
            client.exhaustResponse(move.getResponseBodyAsStream());

        } catch (Exception e) {
            result = new RemoteOperationResult(e);
            Log_OC.e(TAG, "Rename " + mOldRemotePath + " to " +
                    ((mNewRemotePath == null) ? mNewName : mNewRemotePath) + ": " +
                    result.getLogMessage(), e);

        } finally {
            if (move != null)
                move.releaseConnection();
        }

        return result;
    }
}
