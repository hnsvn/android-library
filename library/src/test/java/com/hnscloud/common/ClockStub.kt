/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro.brey@hns.vn>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.common

import com.hnscloud.android.lib.core.Clock

class ClockStub(private val currentTimeValue: Long) : Clock {
    override val currentTimeMillis: Long
        get() = currentTimeValue
}
