/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2019-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2019-2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2018 Bartosz Przybylski <bart.p.pl@gmail.com>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.users;

import com.hnscloud.common.HnscloudClient;
import com.hnscloud.operations.PutMethod;
import com.owncloud.android.lib.common.operations.RemoteOperation;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.common.utils.Log_OC;

import org.apache.commons.httpclient.HttpStatus;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class SetUserInfoRemoteOperation extends RemoteOperation<Boolean> {

    private static final String TAG = SetUserInfoRemoteOperation.class.getSimpleName();

    private static final String OCS_ROUTE_PATH = "/ocs/v2.php/cloud/users/";

    public enum Field {
        EMAIL("email"),
        DISPLAYNAME("displayname"),
        PHONE("phone"),
        ADDRESS("address"),
        WEBSITE("website"),
        TWITTER("twitter");

        private final String fieldName;

        Field(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }
    }

    private final Field field;
    private final String value;

    public SetUserInfoRemoteOperation(Field field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public RemoteOperationResult<Boolean> run(HnscloudClient client) {
        RemoteOperationResult<Boolean> result;
        PutMethod method = null;

        try {
            // request body
            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(json, "{\"key\": \"" + field.getFieldName() + "\"" +
                    ", \"value\": \"" + value + "\"}");

            // remote request
            method = new PutMethod(client.getBaseUri() + OCS_ROUTE_PATH + client.getUserIdPlain(),
                    true,
                    requestBody);

            int status = client.execute(method);

            if (status == HttpStatus.SC_OK) {
                result = new RemoteOperationResult<>(true, method);

            } else {
                result = new RemoteOperationResult<>(false, method);
                String response = method.getResponseBodyAsString();
                Log_OC.e(TAG, "Failed response while setting user information");
                Log_OC.e(TAG, "*** status code: " + status + "; response: " + response);
            }
        } catch (Exception e) {
            result = new RemoteOperationResult<>(e);
            Log_OC.e(TAG, "Exception while setting OC user information", e);
        } finally {
            if (method != null)
                method.releaseConnection();
        }

        return result;
    }
}
