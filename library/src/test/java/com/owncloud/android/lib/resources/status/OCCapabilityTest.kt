/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro.brey@hns.vn>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.status

import org.junit.Test

class OCCapabilityTest {
    /**
     * This won't compile if the fields below are not nullable. This is sort of redundant,
     * but it's meant to prevent a crash in client apps when trying to assign null from Java.
     */
    @Test
    fun testFieldNullability() {
        OCCapability().apply {
            accountName = null
            versionString = null
            versionEdition = null
            serverName = null
            serverSlogan = null
            serverColor = null
            serverTextColor = null
            serverElementColor = null
            serverElementColorBright = null
            serverElementColorDark = null
            serverLogo = null
            serverBackground = null
            richDocumentsMimeTypeList = null
            richDocumentsOptionalMimeTypeList = null
            richDocumentsProductName = null
            directEditingEtag = null
        }
    }
}
