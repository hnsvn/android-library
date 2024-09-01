/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.operations

import com.hnscloud.common.OkHttpMethodBase
import okhttp3.Request

/**
 * HTTP UNLOCK method that uses OkHttp with new HnscloudClient
 */
class UnlockMethod(
    uri: String,
    useOcsApiRequestHeader: Boolean
) : OkHttpMethodBase(uri, useOcsApiRequestHeader) {
    override fun applyType(temp: Request.Builder) {
        temp.method("UNLOCK", null)
    }
}
