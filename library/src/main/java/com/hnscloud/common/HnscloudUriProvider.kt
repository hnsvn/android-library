/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.common

import android.net.Uri

interface HnscloudUriProvider {
    /**
     * Root URI of the Hnscloud server
     */
    var baseUri: Uri?
    val filesDavUri: Uri
    val uploadUri: Uri
    val davUri: Uri

    fun getFilesDavUri(path: String): String

    fun getCommentsUri(fileId: Long): String
}
