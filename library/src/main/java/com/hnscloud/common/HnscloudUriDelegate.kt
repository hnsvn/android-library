/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.common

import android.net.Uri
import com.owncloud.android.lib.common.accounts.AccountUtils
import com.owncloud.android.lib.common.network.WebdavUtils

/**
 * Transitory class to share uri logic between [com.owncloud.android.lib.common.OwnCloudClient]
 * and [com.hnscloud.common.HnscloudClient].
 *
 * When finally getting rid of [com.owncloud.android.lib.common.OwnCloudClient],
 * this should be separate from the client.
 */
class HnscloudUriDelegate(baseUri: Uri, var userId: String?) : HnscloudUriProvider {
    constructor(baseUri: Uri) : this(baseUri, null)

    val userIdEncoded: String?
        get() = userId?.let { UserIdEncoder.encode(it) }

    /**
     * Root URI of the Hnscloud server
     */
    override var baseUri: Uri? = baseUri
        set(uri) {
            requireNotNull(uri) { "URI cannot be NULL" }
            field = uri
        }

    override val filesDavUri: Uri
        get() = Uri.parse("$davUri/files/$userIdEncoded")
    override val uploadUri: Uri
        get() = Uri.parse(baseUri.toString() + AccountUtils.DAV_UPLOAD)
    override val davUri: Uri
        get() = Uri.parse(baseUri.toString() + AccountUtils.WEBDAV_PATH_9_0)

    override fun getFilesDavUri(path: String): String {
        // encodePath already adds leading slash if needed
        return "$filesDavUri${WebdavUtils.encodePath(path)}"
    }

    override fun getCommentsUri(fileId: Long): String {
        return "$davUri/comments/files/$fileId"
    }
}
