/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2023-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2023-2024 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.android.lib.resources.groupfolders

import com.google.gson.reflect.TypeToken
import com.hnscloud.common.HnscloudClient
import com.hnscloud.operations.GetMethod
import com.owncloud.android.lib.common.operations.RemoteOperationResult
import com.owncloud.android.lib.common.utils.Log_OC
import com.owncloud.android.lib.ocs.ServerResponse
import com.owncloud.android.lib.resources.OCSRemoteOperation
import org.apache.commons.httpclient.HttpStatus

/**
 * Get groupfolders of an user
 */
class GetGroupfoldersRemoteOperation :
    OCSRemoteOperation<Map<String, Groupfolder>>() {
    @Suppress("TooGenericExceptionCaught")
    override fun run(client: HnscloudClient): RemoteOperationResult<Map<String, Groupfolder>> {
        var result: RemoteOperationResult<Map<String, Groupfolder>>
        var getMethod: GetMethod? = null
        try {
            getMethod =
                GetMethod(
                    client.baseUri.toString() + GROUPFOLDERS_ENDPOINT + JSON_FORMAT + APPLICABLE,
                    true
                )
            val status = client.execute(getMethod)
            if (status == HttpStatus.SC_OK) {
                val map =
                    getServerResponse(
                        getMethod,
                        object : TypeToken<ServerResponse<Map<String, Groupfolder>>>() {}
                    )?.ocs?.data

                if (map != null) {
                    result = RemoteOperationResult(true, getMethod)
                    result.setResultData(map)
                } else {
                    result = RemoteOperationResult(false, getMethod)
                }
            } else {
                result = RemoteOperationResult(false, getMethod)
            }
        } catch (e: Exception) {
            result = RemoteOperationResult(e)
            Log_OC.e(
                TAG,
                "Get groupfolders failed: " + result.logMessage,
                result.exception
            )
        } finally {
            getMethod?.releaseConnection()
        }
        return result
    }

    companion object {
        private val TAG = GetGroupfoldersRemoteOperation::class.java.simpleName
        private const val GROUPFOLDERS_ENDPOINT = "/index.php/apps/groupfolders/folders"
        private const val APPLICABLE = "&applicable=1"
    }
}
