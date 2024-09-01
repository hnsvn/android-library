/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2019-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.shares

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShareeUser(val userId: String?, var displayName: String?, val shareType: ShareType?) :
    Parcelable
