/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2015 ownCloud Inc.
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.files;

import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.network.WebdavEntry;
import com.owncloud.android.lib.common.network.WebdavUtils;
import com.owncloud.android.lib.common.operations.RemoteOperation;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.common.utils.Log_OC;
import com.owncloud.android.lib.resources.files.model.RemoteFile;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;

import java.util.ArrayList;

/**
 * Remote operation performing the read of remote file or folder in the ownCloud server.
 *
 * @author David A. Velasco
 * @author masensio
 */

public class ReadFolderRemoteOperation extends RemoteOperation {

    private static final String TAG = ReadFolderRemoteOperation.class.getSimpleName();

    private String mRemotePath;
    private ArrayList<Object> mFolderAndFiles;

    /**
     * Constructor
     *
     * @param remotePath Remote path of the file.
     */
    public ReadFolderRemoteOperation(String remotePath) {
        mRemotePath = remotePath;
    }

    /**
     * Performs the read operation.
     *
     * @param client Client object to communicate with the remote ownCloud server.
     */
    @Override
    protected RemoteOperationResult run(OwnCloudClient client) {
        RemoteOperationResult result = null;
        PropFindMethod query = null;

        try {
            // remote request
            query = new PropFindMethod(client.getFilesDavUri(mRemotePath),
                    WebdavUtils.getAllPropSet(),    // PropFind Properties
                    DavConstants.DEPTH_1);
            int status = client.executeMethod(query);

            // check and process response
            boolean isSuccess = (status == HttpStatus.SC_MULTI_STATUS || status == HttpStatus.SC_OK);
            
            if (isSuccess) {
                // get data from remote folder
                MultiStatus dataInServer = query.getResponseBodyAsMultiStatus();
                readData(dataInServer, client);

                // Result of the operation
                result = new RemoteOperationResult(true, query);
                // Add data to the result
                if (result.isSuccess()) {
                    result.setData(mFolderAndFiles);
                }
            } else {
                // synchronization failed
                client.exhaustResponse(query.getResponseBodyAsStream());
                result = new RemoteOperationResult(false, query);
            }
        } catch (Exception e) {
            result = new RemoteOperationResult(e);
        } finally {
            if (query != null)
                query.releaseConnection();  // let the connection available for other methods

            if (result == null) {
                result = new RemoteOperationResult(new Exception("unknown error"));
                Log_OC.e(TAG, "Synchronized " + mRemotePath + ": failed");
            } else {
                if (result.isSuccess()) {
                    Log_OC.i(TAG, "Synchronized " + mRemotePath + ": " + result.getLogMessage());
                } else {
                    if (result.isException()) {
                        Log_OC.e(TAG, "Synchronized " + mRemotePath + ": " + result.getLogMessage(),
                                result.getException());
                    } else {
                        Log_OC.e(TAG, "Synchronized " + mRemotePath + ": " + result.getLogMessage());
                    }
                }
            }
        }
        
        return result;
    }

    public boolean isMultiStatus(int status) {
        return (status == HttpStatus.SC_MULTI_STATUS);
    }

    /**
     * Read the data retrieved from the server about the contents of the target folder
     *
     * @param remoteData Full response got from the server with the data of the target
     *                   folder and its direct children.
     * @param client     Client instance to the remote server where the data were
     *                   retrieved.
     * @return
     */
    private void readData(MultiStatus remoteData, OwnCloudClient client) {
        mFolderAndFiles = new ArrayList<>();

        // parse data from remote folder 
        WebdavEntry we = new WebdavEntry(remoteData.getResponses()[0], client.getFilesDavUri().getEncodedPath());
        mFolderAndFiles.add(new RemoteFile(we));

        // loop to update every child
        RemoteFile remoteFile;
        for (int i = 1; i < remoteData.getResponses().length; ++i) {
            /// new OCFile instance with the data from the server
            we = new WebdavEntry(remoteData.getResponses()[i], client.getFilesDavUri().getEncodedPath());
            remoteFile = new RemoteFile(we);
            mFolderAndFiles.add(remoteFile);
        }

    }
}
