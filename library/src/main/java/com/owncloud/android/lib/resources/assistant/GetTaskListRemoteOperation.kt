/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2024 Alper Ozturk <alper.ozturk@hns.vn>
 * SPDX-License-Identifier: MIT
 */

package com.owncloud.android.lib.resources.assistant

import com.google.gson.reflect.TypeToken
import com.hnscloud.common.HnscloudClient
import com.hnscloud.operations.GetMethod
import com.owncloud.android.lib.common.operations.RemoteOperationResult
import com.owncloud.android.lib.common.utils.Log_OC
import com.owncloud.android.lib.ocs.ServerResponse
import com.owncloud.android.lib.resources.OCSRemoteOperation
import com.owncloud.android.lib.resources.assistant.model.TaskList
import org.apache.commons.httpclient.HttpStatus

class GetTaskListRemoteOperation(private val appId: String) : OCSRemoteOperation<TaskList>() {
    @Suppress("TooGenericExceptionCaught")
    override fun run(client: HnscloudClient): RemoteOperationResult<TaskList> {
        var result: RemoteOperationResult<TaskList>
        var getMethod: GetMethod? = null
        try {
            getMethod =
                GetMethod(client.baseUri.toString() + DIRECT_ENDPOINT + appId + JSON_FORMAT, true)
            val status = client.execute(getMethod)
            if (status == HttpStatus.SC_OK) {
                val taskTypes: TaskList? =
                    getServerResponse(
                        getMethod,
                        object : TypeToken<ServerResponse<TaskList>>() {}
                    )?.ocs?.data
                result = RemoteOperationResult(true, getMethod)
                result.setResultData(taskTypes)
            } else {
                result = RemoteOperationResult(false, getMethod)
            }
        } catch (e: Exception) {
            result = RemoteOperationResult(e)
            Log_OC.e(
                TAG,
                "Get task list for user " + " failed: " + result.logMessage,
                result.exception
            )
        } finally {
            getMethod?.releaseConnection()
        }
        return result
    }

    companion object {
        private val TAG = GetTaskTypesRemoteOperation::class.java.simpleName
        private const val DIRECT_ENDPOINT = "/ocs/v2.php/textprocessing/tasks/app/"
    }
}
