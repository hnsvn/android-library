/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.android.lib.resources.dashboard

import com.owncloud.android.AbstractIT
import com.owncloud.android.lib.resources.files.CreateFolderRemoteOperation
import com.owncloud.android.lib.resources.shares.CreateShareRemoteOperation
import com.owncloud.android.lib.resources.shares.OCShare
import com.owncloud.android.lib.resources.shares.ShareType
import com.owncloud.android.lib.resources.status.HnscloudVersion
import org.junit.Assert.assertTrue
import org.junit.Test

class DashboardGetWidgetItemsRemoteOperationIT : AbstractIT() {
    @Test
    fun getItems() {
        // only on NC25+
        testOnlyOnServer(HnscloudVersion.hnscloud_25)

        // create folder to have some content
        assertTrue(CreateFolderRemoteOperation("/testFolder", false).execute(client2).isSuccess)
        assertTrue(
            CreateShareRemoteOperation(
                "/testFolder",
                ShareType.USER,
                client.userId,
                false,
                "",
                OCShare.MAXIMUM_PERMISSIONS_FOR_FOLDER
            ).execute(client2)
                .isSuccess
        )

        val widgetId = "activity"
        val result =
            DashboardGetWidgetItemsRemoteOperation(widgetId, LIMIT_SIZE).execute(hnscloudClient)

        assertTrue(result.isSuccess)
        assertTrue(result.resultData[widgetId]?.isNotEmpty() ?: false)

        val firstResult = result.resultData[widgetId]?.get(0)
        assertTrue(firstResult?.title?.isNotEmpty() == true)
        assertTrue(firstResult?.subtitle != null)
        assertTrue(firstResult?.link?.isNotEmpty() == true)
        assertTrue(firstResult?.iconUrl?.isNotEmpty() == true)
    }

    @Test
    fun getEmptyItems() {
        val widgetId = "nonExistingWidget"
        val result =
            DashboardGetWidgetItemsRemoteOperation(widgetId, LIMIT_SIZE).execute(hnscloudClient)

        assertTrue(result.isSuccess)
        assertTrue(result.resultData.isEmpty())
    }

    companion object {
        const val LIMIT_SIZE = 14
    }
}
