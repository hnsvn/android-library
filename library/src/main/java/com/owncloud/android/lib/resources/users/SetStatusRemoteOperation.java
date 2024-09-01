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

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Remote operation performing setting of status
 */
public class SetStatusRemoteOperation extends OCSRemoteOperation<Boolean> {

    private static final String TAG = SetStatusRemoteOperation.class.getSimpleName();
    private static final String SET_STATUS_URL = "/ocs/v2.php/apps/user_status/api/v1/user_status/status";

    private final StatusType type;

    public SetStatusRemoteOperation(StatusType type) {
        this.type = type;
    }

    /**
     * @param client Client object
     */
    @Override
    public RemoteOperationResult<Boolean> run(HnscloudClient client) {
        PutMethod putMethod = null;
        RemoteOperationResult<Boolean> result;

        try {
            // request body
            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(json, "{\"statusType\": \"" + type.getString() + "\"}");

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
            Log_OC.e(TAG, "Setting of own status failed: " + result.getLogMessage(), result.getException());
        } finally {
            if (putMethod != null) {
                putMethod.releaseConnection();
            }
        }
        return result;
    }
}
