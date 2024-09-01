/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2017-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2017 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.users;

import com.hnscloud.common.HnscloudClient;
import com.hnscloud.operations.PostMethod;
import com.owncloud.android.lib.common.operations.RemoteOperation;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.common.utils.Log_OC;

import org.json.JSONObject;

import java.net.HttpURLConnection;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * Remote operation performing the storage of the public key for an user
 */

public class SendCSRRemoteOperation extends RemoteOperation<String> {

    private static final String TAG = SendCSRRemoteOperation.class.getSimpleName();
    private static final String PUBLIC_KEY_URL = "/ocs/v2.php/apps/end_to_end_encryption/api/v1/public-key";
    private static final String CSR = "csr";

    // JSON node names
    private static final String NODE_OCS = "ocs";
    private static final String NODE_DATA = "data";
    private static final String NODE_PUBLIC_KEY = "public-key";

    private final String csr;

    /**
     * Constructor
     */
    public SendCSRRemoteOperation(String csr) {
        this.csr = csr;
    }

    /**
     * @param client Client object
     */
    @Override
    public RemoteOperationResult<String> run(HnscloudClient client) {
        PostMethod postMethod = null;
        RemoteOperationResult<String> result;

        try {
            // remote request
            RequestBody body = new FormBody
                    .Builder()
                    .add(CSR, csr)
                    .build();

            postMethod = new PostMethod(
                    client.getBaseUri() + PUBLIC_KEY_URL + JSON_FORMAT,
                    true,
                    body);

            int status = client.execute(postMethod);

            if (status == HttpURLConnection.HTTP_OK) {
                String response = postMethod.getResponseBodyAsString();

                // Parse the response
                JSONObject respJSON = new JSONObject(response);
                String key = (String) respJSON.getJSONObject(NODE_OCS).getJSONObject(NODE_DATA).get(NODE_PUBLIC_KEY);

                result = new RemoteOperationResult<>(true, postMethod);
                result.setResultData(key);
            } else {
                result = new RemoteOperationResult<>(false, postMethod);
            }

        } catch (Exception e) {
            result = new RemoteOperationResult<>(e);
            Log_OC.e(TAG, "Fetching of signing CSR failed: " + result.getLogMessage(), result.getException());
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return result;
    }

}
