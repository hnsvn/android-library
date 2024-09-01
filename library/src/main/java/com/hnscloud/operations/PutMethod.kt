/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2020-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.operations

import com.hnscloud.common.OkHttpMethodBase
import okhttp3.Request
import okhttp3.RequestBody

/**
 * HTTP PUT method that uses OkHttp with new HnscloudClient
 */
class PutMethod(
    uri: String,
    useOcsApiRequestHeader: Boolean,
    val body: RequestBody? = null
) : OkHttpMethodBase(uri, useOcsApiRequestHeader) {
    override fun applyType(temp: Request.Builder) {
        body?.let {
            temp.put(it)
        }
    }
}
