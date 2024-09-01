/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2024 Alper Ozturk <alper.ozturk@hns.vn>
 * SPDX-License-Identifier: MIT
 */

package com.owncloud.android.lib.resources.assistant

import com.hnscloud.common.HnscloudClient
import com.hnscloud.operations.DeleteMethod
import com.owncloud.android.lib.common.operations.RemoteOperation
import com.owncloud.android.lib.common.operations.RemoteOperationResult
import com.owncloud.android.lib.common.utils.Log_OC
import com.owncloud.android.lib.resources.users.DeletePrivateKeyRemoteOperation
import java.io.IOException
import java.net.HttpURLConnection

class DeleteTaskRemoteOperation(private val appId: Long) : RemoteOperation<Void>() {
    override fun run(client: HnscloudClient): RemoteOperationResult<Void> {
        var postMethod: DeleteMethod? = null
        var result: RemoteOperationResult<Void>
        try {
            postMethod =
                DeleteMethod(
                    client.baseUri.toString() + DIRECT_ENDPOINT + appId,
                    true
                )
            val status = client.execute(postMethod)
            result = RemoteOperationResult<Void>(status == HttpURLConnection.HTTP_OK, postMethod)
        } catch (e: IOException) {
            result = RemoteOperationResult<Void>(e)
            Log_OC.e(TAG, "Deletion of task failed: " + result.logMessage, result.exception)
        } finally {
            postMethod?.releaseConnection()
        }

        return result
    }

    companion object {
        private val TAG = DeletePrivateKeyRemoteOperation::class.java.simpleName
        private const val DIRECT_ENDPOINT =
            "/ocs/v2.php/textprocessing/task/"
    }
}
