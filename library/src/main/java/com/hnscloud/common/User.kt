/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.common

import android.accounts.Account

interface User {
    val accountName: String

    @Deprecated("Temporary workaround")
    fun toPlatformAccount(): Account
}
