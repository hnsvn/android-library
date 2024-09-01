/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2023 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.android.lib.core

interface Clock {
    /**
     * Current epoch time in millis
     */
    val currentTimeMillis: Long
}
