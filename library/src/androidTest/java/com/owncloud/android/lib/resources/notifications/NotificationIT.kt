/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2023-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2023 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.notifications

import com.owncloud.android.AbstractIT
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class NotificationIT : AbstractIT() {
    @Test
    fun getNotification() {
        // get all
        val all = GetNotificationsRemoteOperation().execute(client)
        assertTrue(all.isSuccess)

        // get one
        val firstNotification = all.resultData[0]
        val first =
            GetNotificationRemoteOperation(firstNotification.notificationId).execute(
                client
            )
        assertTrue(first.isSuccess)
        assertEquals(firstNotification.message, first.resultData.message)
    }
}
