/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.common

import android.net.Uri

object UserIdEncoder {
    /**
     * Characters to skip during userID encoding
     */
    private const val ALLOWED_USERID_CHARACTERS = "@+"

    @JvmStatic
    fun encode(userId: String): String {
        return Uri.encode(userId, ALLOWED_USERID_CHARACTERS)
            // single quote is not automatically encoded by Uri but is encoded in NC server
            .replace("'", "%27")
    }
}
