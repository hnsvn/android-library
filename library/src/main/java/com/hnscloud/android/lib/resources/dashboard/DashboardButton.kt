/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022-2024 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.android.lib.resources.dashboard

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DashboardButton(
    val type: DashBoardButtonType,
    val text: String,
    val link: String
) : Parcelable
