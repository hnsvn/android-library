/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2021-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2021-2024 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.android.lib.resources.profile

/**
 * Action of HoverCard data model
 */
data class Action(
    var appId: String,
    var title: String,
    var icon: String,
    var hyperlink: String
)
