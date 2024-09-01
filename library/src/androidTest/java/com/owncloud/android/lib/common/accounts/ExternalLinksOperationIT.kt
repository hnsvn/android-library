/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.common.accounts

import com.owncloud.android.AbstractIT
import com.owncloud.android.lib.common.ExternalLink
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class ExternalLinksOperationIT : AbstractIT() {
    @Test
    fun retrieveExternalLinks() {
        val result = ExternalLinksOperation().execute(client)
        assertTrue(result.isSuccess)

        val data = result.data as ArrayList<ExternalLink>
        assertEquals(2, data.size)

        assertEquals("Hnscloud", data[0].name)
        assertEquals("https://www.hns.vn", data[0].url)

        assertEquals("Forum", data[1].name)
        assertEquals("https://help.hns.vn", data[1].url)
    }
}
