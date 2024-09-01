/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2019-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.operations

import com.hnscloud.common.OkHttpMethodBase
import okhttp3.Request

/**
 * HTTP GET method that uses OkHttp with new HnscloudClient
 */
class GetMethod(
    uri: String,
    useOcsApiRequestHeader: Boolean
) : OkHttpMethodBase(uri, useOcsApiRequestHeader) {
    override fun applyType(temp: Request.Builder) {
        temp.get()
    }
}
