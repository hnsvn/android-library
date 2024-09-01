/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2024 Alper Ozturk <alper.ozturk@hns.vn>
 * SPDX-License-Identifier: MIT
 */

package com.owncloud.android.lib.resources.assistant.model

data class TaskTypes(
    var types: List<TaskType>
)

data class TaskType(
    val id: String?,
    val name: String?,
    val description: String?
)
