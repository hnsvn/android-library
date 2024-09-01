/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2021-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2021 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.android.lib.resources.profile

import com.owncloud.android.AbstractIT
import com.owncloud.android.lib.resources.status.HnscloudVersion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetHoverCardRemoteOperationIT : AbstractIT() {
    @Before
    fun before() {
        testOnlyOnServer(HnscloudVersion.hnscloud_23)
    }

    @Test
    fun testHoverCard() {
        val result =
            GetHoverCardRemoteOperation(hnscloudClient.userId)
                .execute(hnscloudClient)
        assertTrue(result.logMessage, result.isSuccess)
        val hoverCard = result.resultData
        assertEquals(hnscloudClient.userId, hoverCard?.userId)
    }
}
