/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.common

import android.net.Uri
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HnscloudUriDelegateIT {
    lateinit var sut: HnscloudUriDelegate

    @Before
    fun setUp() {
        sut = HnscloudUriDelegate(Uri.parse(BASEURL), USERID)
    }

    @Test
    fun testFilesDavUriLeadingSlashInPath() {
        val expected = "$EXPECTED_FILES_DAV/path/to/file.txt"
        val actual = sut.getFilesDavUri("/path/to/file.txt")
        assertEquals("Wrong URL", expected, actual)
    }

    @Test
    fun testFilesDavUriLoLeadingSlashInPath() {
        val expected = "$EXPECTED_FILES_DAV/path/to/file.txt"
        val actual = sut.getFilesDavUri("path/to/file.txt")
        assertEquals("Wrong URL", expected, actual)
    }

    @Test
    fun testFilesDavUriEmptyPath() {
        val expected = "$EXPECTED_FILES_DAV/"
        val actual = sut.getFilesDavUri("")
        assertEquals("Wrong URL", expected, actual)
    }

    @Test
    fun testFilesDavUriRootPath() {
        val expected = "$EXPECTED_FILES_DAV/"
        val actual = sut.getFilesDavUri("/")
        assertEquals("Wrong URL", expected, actual)
    }

    companion object {
        private const val USERID = "user"
        private const val BASEURL = "http://test.localhost"
        private const val EXPECTED_FILES_DAV = "$BASEURL/remote.php/dav/files/$USERID"
    }
}
