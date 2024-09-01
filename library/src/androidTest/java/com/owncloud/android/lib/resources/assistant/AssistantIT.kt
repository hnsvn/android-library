/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2024 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */

package com.owncloud.android.lib.resources.assistant

import com.owncloud.android.AbstractIT
import com.owncloud.android.lib.resources.status.HnscloudVersion
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class AssistantIT : AbstractIT() {
    @Before
    fun before() {
        testOnlyOnServer(HnscloudVersion.hnscloud_28)
    }

    @Test
    fun testGetTaskTypes() {
        val result = GetTaskTypesRemoteOperation().execute(hnscloudClient)
        assertTrue(result.isSuccess)

        val taskTypes = result.resultData.types
        assertTrue(taskTypes.isNotEmpty())
    }

    @Test
    fun testGetTaskList() {
        var result = GetTaskListRemoteOperation("assistant").execute(hnscloudClient)
        assertTrue(result.isSuccess)
        assertTrue(result.resultData.tasks.isEmpty())

        // create one task
        val input = "Give me some random output for test purpose"
        val type = "OCP\\TextProcessing\\FreePromptTaskType"
        assertTrue(CreateTaskRemoteOperation(input, type).execute(hnscloudClient).isSuccess)

        result = GetTaskListRemoteOperation("assistant").execute(hnscloudClient)
        assertTrue(result.isSuccess)

        val taskList = result.resultData.tasks
        assertTrue(taskList.isNotEmpty())
    }

    @Test
    fun testDeleteTask() {
        // create one task
        val input = "Give me some random output for test purpose"
        val type = "OCP\\TextProcessing\\FreePromptTaskType"
        assertTrue(CreateTaskRemoteOperation(input, type).execute(hnscloudClient).isSuccess)

        var result = GetTaskListRemoteOperation("assistant").execute(hnscloudClient)
        assertTrue(result.isSuccess)

        val tasks = result.resultData.tasks
        val countBefore = tasks.size

        // delete
        assertTrue(DeleteTaskRemoteOperation(tasks.first().id).execute(hnscloudClient).isSuccess)

        result = GetTaskListRemoteOperation("assistant").execute(hnscloudClient)
        assertTrue(result.isSuccess)

        assertEquals(countBefore - 1, result.resultData.tasks.size)
    }
}
