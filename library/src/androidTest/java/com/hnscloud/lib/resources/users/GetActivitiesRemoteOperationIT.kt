/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2019-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.lib.resources.users

import com.owncloud.android.AbstractIT
import com.owncloud.android.lib.resources.activities.GetActivitiesRemoteOperation
import com.owncloud.android.lib.resources.activities.model.Activity
import com.owncloud.android.lib.resources.files.CreateFolderRemoteOperation
import org.junit.Assert.assertTrue
import org.junit.Test

class GetActivitiesRemoteOperationIT : AbstractIT() {
    @Test
    fun getActivities() {
        // set-up, create a folder so there is an activity
        assertTrue(CreateFolderRemoteOperation("/test/123/1", true).execute(client).isSuccess)

        val result = hnscloudClient.execute(GetActivitiesRemoteOperation())
        assertTrue(result.isSuccess)

        val activities = result.data[0] as ArrayList<Activity>
        val lastGiven = result.data[1] as Integer

        assertTrue(activities.isNotEmpty())
        assertTrue(lastGiven > 0)
    }
}
