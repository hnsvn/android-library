/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2020-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2020 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.common

import okhttp3.Response

internal data class ResponseOrError private constructor(val result: Response?, val error: Exception?) {
    constructor(result: Response) : this(result, null)
    constructor(error: Exception) : this(null, error)
}
