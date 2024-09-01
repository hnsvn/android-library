/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2020-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.users;

import com.hnscloud.common.HnscloudClient;
import com.hnscloud.operations.PutMethod;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.common.utils.Log_OC;
import com.owncloud.android.lib.resources.OCSRemoteOperation;

import org.apache.commons.httpclient.HttpStatus;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Remote operation performing setting of predefined custome status message
 */
public class SetPredefinedCustomStatusMessageRemoteOperation extends OCSRemoteOperation<ArrayList<PredefinedStatus>> {

    private static final String TAG = SetPredefinedCustomStatusMessageRemoteOperation.class.getSimpleName();
    private static final String SET_STATUS_URL = "/ocs/v2.php/apps/user_status/api/v1/user_status/message/predefined";

    private final String messageId;
    private final Long clearAt;

    public SetPredefinedCustomStatusMessageRemoteOperation(String messageId, Long clearAt) {
        this.messageId = messageId;
        this.clearAt = clearAt;
    }

    /**
     * @param client Client object
     */
    @Override
    public RemoteOperationResult<ArrayList<PredefinedStatus>> run(HnscloudClient client) {
        PutMethod putMethod = null;
        RemoteOperationResult<ArrayList<PredefinedStatus>> result;

        try {
            // request body
            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(json,
                    "{\"messageId\": \"" + messageId + "\", " +
                            "\"clearAt\": " + clearAt + "}");

            // remote request
            putMethod = new PutMethod(client.getBaseUri() + SET_STATUS_URL, true, requestBody);

            int status = client.execute(putMethod);

            if (status == HttpStatus.SC_OK) {
                result = new RemoteOperationResult<>(true, putMethod);
            } else {
                result = new RemoteOperationResult<>(false, putMethod);
                putMethod.releaseConnection();
            }
        } catch (Exception e) {
            result = new RemoteOperationResult<>(e);
            Log_OC.e(TAG, "Setting of predefined custom status failed: " + result.getLogMessage(),
                    result.getException());
        } finally {
            if (putMethod != null) {
                putMethod.releaseConnection();
            }
        }
        return result;
    }
}
