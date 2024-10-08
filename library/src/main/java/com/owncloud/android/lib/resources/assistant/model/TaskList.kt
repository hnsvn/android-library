/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2024 Alper Ozturk <alper.ozturk@hns.vn>
 * SPDX-License-Identifier: MIT
 */

package com.owncloud.android.lib.resources.assistant.model

data class TaskList(
    var tasks: List<Task>
)

data class Task(
    val id: Long,
    val type: String?,
    val status: Long?,
    val userId: String?,
    val appId: String?,
    val input: String?,
    val output: String?,
    val identifier: String?,
    val completionExpectedAt: String? = null
)
