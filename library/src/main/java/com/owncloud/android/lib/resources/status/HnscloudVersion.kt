/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2020-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.status

class HnscloudVersion : OwnCloudVersion {
    companion object {
        @JvmField
        val hnscloud_21 = HnscloudVersion(0x15000000) // 21.0

        @JvmField
        val hnscloud_22 = HnscloudVersion(0x16000000) // 22.0

        @JvmField
        val hnscloud_23 = HnscloudVersion(0x17000000) // 23.0

        @JvmField
        val hnscloud_24 = HnscloudVersion(0x18000000) // 24.0

        @JvmField
        val hnscloud_25 = HnscloudVersion(0x19000000) // 25.0

        @JvmField
        val hnscloud_26 = HnscloudVersion(0x1A000000) // 26.0

        @JvmField
        val hnscloud_27 = HnscloudVersion(0x1B000000) // 27.0

        @JvmField
        val hnscloud_28 = HnscloudVersion(0x1C000000) // 28.0
    }

    constructor(string: String) : super(string)
    constructor(version: Int) : super(version)
}
