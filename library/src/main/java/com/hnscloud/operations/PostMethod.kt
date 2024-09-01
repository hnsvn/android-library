/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2019-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2023 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.operations

import com.hnscloud.common.OkHttpMethodBase
import okhttp3.Request
import okhttp3.RequestBody

/**
 * HTTP POST method that uses OkHttp with new HnscloudClient
 * UTF8 by default
 */
class PostMethod(
    uri: String,
    useOcsApiRequestHeader: Boolean,
    val body: RequestBody
) : OkHttpMethodBase(uri, useOcsApiRequestHeader) {
    override fun applyType(temp: Request.Builder) {
        temp.post(body)
    }
}
