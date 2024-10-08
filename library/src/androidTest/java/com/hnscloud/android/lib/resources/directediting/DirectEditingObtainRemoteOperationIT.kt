/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.android.lib.resources.directediting

import com.owncloud.android.AbstractIT
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Test

class DirectEditingObtainRemoteOperationIT : AbstractIT() {
    @Test
    fun testGetAll() {
        val result = DirectEditingObtainRemoteOperation().execute(client)
        assertTrue(result.isSuccess)

        val (editors, creators) = result.resultData
        assertTrue(editors.containsKey("text"))

        val textEditor = editors["text"]
        assertNotNull(textEditor)
        assertEquals("Hnscloud Text", textEditor!!.name)
        assertTrue(textEditor.mimetypes.contains("text/markdown"))
        assertTrue(textEditor.mimetypes.contains("text/plain"))
        assertEquals(0, textEditor.optionalMimetypes.size.toLong())

        val creator = creators["textdocument"]
        assertNotNull(creator)
        assertFalse(creator!!.templates)
    }
}
