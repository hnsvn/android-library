/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.android.lib.resources.dashboard

import com.owncloud.android.AbstractIT
import com.owncloud.android.lib.resources.status.HnscloudVersion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DashboardListWidgetsRemoteOperationIT : AbstractIT() {
    @Test
    fun list() {
        // only on NC25+
        testOnlyOnServer(HnscloudVersion.hnscloud_25)

        val result = DashboardListWidgetsRemoteOperation().execute(hnscloudClient)
        assertTrue(result.isSuccess)

        assertTrue(result.resultData.isNotEmpty())

        assertTrue(result.resultData["recommendations"]?.buttons?.getOrNull(0) == null)

        assertEquals(1, result.resultData["activity"]?.buttons?.size)
        assertTrue(result.resultData["activity"]?.buttons?.getOrNull(0)?.type == DashBoardButtonType.MORE)
        assertTrue(result.resultData["activity"]?.roundIcons == false)

        assertTrue(result.resultData["user_status"]?.roundIcons == true)
    }
}
