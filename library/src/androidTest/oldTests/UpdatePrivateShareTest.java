/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2019-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android;

import android.util.Log;

import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.resources.shares.OCShare;
import com.owncloud.android.lib.resources.shares.RemoveRemoteShareOperation;
import com.owncloud.android.lib.resources.shares.ShareType;
import com.owncloud.android.lib.resources.shares.UpdateRemoteShareOperation;
import com.owncloud.android.lib.testclient.TestActivity;

import java.io.File;

/**
 * Class to test UpdateRemoteShareOperation
 * with private shares
 */
public class UpdatePrivateShareTest extends RemoteTest {
    private static final String LOG_TAG = UpdatePrivateShareTest.class.getCanonicalName();

    /* File to share and update */
    private static final String FILE_TO_SHARE = "/fileToShare.txt";

    /* Folder to share and update */
    private static final String FOLDER_TO_SHARE = "/folderToShare";

    /* Sharees */
    private static final String USER_SHAREE = "admin";
    private static final String GROUP_SHAREE = "admin";

    private String mFullPath2FileToShare;
    private String mFullPath2FolderToShare;

    private OCShare mFileShare;
    private OCShare mFolderShare;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Log.v(LOG_TAG, "Setting up the remote fixture...");

        // Upload the file
        mFullPath2FileToShare = mBaseFolderPath + FILE_TO_SHARE;

        File textFile = getFile(TestActivity.ASSETS__TEXT_FILE_NAME);
        RemoteOperationResult result = getActivity().uploadFile(textFile.getAbsolutePath(), mFullPath2FileToShare,
                "txt/plain", null);

        if (!result.isSuccess()) {
            Utils.logAndThrow(LOG_TAG, result);
        }

        // Share the file privately with other user
        result = getActivity().createShare(mFullPath2FileToShare, ShareType.USER, USER_SHAREE, false, "",
                OCShare.MAXIMUM_PERMISSIONS_FOR_FILE);

        if (result.isSuccess()) {
            mFileShare = (OCShare) result.getData().get(0);
        } else {
            mFileShare = null;
        }

        // Create the folder
        mFullPath2FolderToShare = mBaseFolderPath + FOLDER_TO_SHARE;
        result = getActivity().createFolder(mFullPath2FolderToShare, true);

        if (!result.isSuccess()) {
            Utils.logAndThrow(LOG_TAG, result);
        }

        // Share the folder privately with a group
        result = getActivity().createShare(mFullPath2FolderToShare, ShareType.GROUP, GROUP_SHAREE, false, "",
                OCShare.MAXIMUM_PERMISSIONS_FOR_FOLDER);

        if (result.isSuccess()) {
            mFolderShare = (OCShare) result.getData().get(0);
        } else {
            mFolderShare = null;
        }

        Log.v(LOG_TAG, "Remote fixture created.");
    }

    public void testUpdateSharePermissions() {
        Log.v(LOG_TAG, "testUpdateSharePermissions in");

        if (mFileShare != null) {
            /// successful tests
            // Update Share permissions on a shared file
            UpdateRemoteShareOperation updateShare = new UpdateRemoteShareOperation(mFileShare.getRemoteId());
            updateShare.setPermissions(OCShare.READ_PERMISSION_FLAG);    // minimum permissions
            RemoteOperationResult result = updateShare.execute(mClient);
            assertTrue(result.isSuccess());

            // Update Share permissions on a shared folder
            updateShare = new UpdateRemoteShareOperation(mFolderShare.getRemoteId());
            updateShare.setPermissions(OCShare.READ_PERMISSION_FLAG + OCShare.DELETE_PERMISSION_FLAG);
            result = updateShare.execute(mClient);
            assertTrue(result.isSuccess());


            /// unsuccessful tests
            // Update Share with invalid permissions
            updateShare = new UpdateRemoteShareOperation(mFileShare.getRemoteId());
            // greater than maximum value
            updateShare.setPermissions(OCShare.MAXIMUM_PERMISSIONS_FOR_FOLDER + 1);
            result = updateShare.execute(mClient);
            assertFalse(result.isSuccess());

            // Unshare the file before next unsuccessful tests
            RemoveRemoteShareOperation unshare = new RemoveRemoteShareOperation((int) mFileShare.getRemoteId());
            result = unshare.execute(mClient);

            if (result.isSuccess()) {
                // Update Share permissions on unknown share
                UpdateRemoteShareOperation updateNoShare = new UpdateRemoteShareOperation(mFileShare.getRemoteId());
                updateNoShare.setPermissions(OCShare.READ_PERMISSION_FLAG);    // minimum permissions
                result = updateNoShare.execute(mClient);
                assertFalse(result.isSuccess());
            }
        }
    }

    @Override
    protected void tearDown() throws Exception {
        Log.v(LOG_TAG, "Deleting remote fixture...");

        if (mFullPath2FileToShare != null) {
            RemoteOperationResult removeResult = getActivity().removeFile(mFullPath2FileToShare);
            if (!removeResult.isSuccess()) {
                Utils.logAndThrow(LOG_TAG, removeResult);
            }
        }

        if (mFullPath2FolderToShare != null) {
            RemoteOperationResult removeResult = getActivity().removeFile(mFullPath2FolderToShare);
            if (!removeResult.isSuccess()) {
                Utils.logAndThrow(LOG_TAG, removeResult);
            }
        }
        
        super.tearDown();
        Log.v(LOG_TAG, "Remote fixture delete.");
    }
}
