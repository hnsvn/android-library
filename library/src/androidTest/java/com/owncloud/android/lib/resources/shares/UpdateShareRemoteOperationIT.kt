/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2020-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2020-2023 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.shares

import com.owncloud.android.AbstractIT
import com.owncloud.android.lib.resources.files.CreateFolderRemoteOperation
import com.owncloud.android.lib.resources.files.RemoveFileRemoteOperation
import com.owncloud.android.lib.resources.status.GetCapabilitiesRemoteOperation
import com.owncloud.android.lib.resources.status.HnscloudVersion
import com.owncloud.android.lib.resources.status.OCCapability
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UpdateShareRemoteOperationIT : AbstractIT() {
    @Test
    fun shortNote() {
        testUpdateNote("123")
    }

    @Test
    fun middleNote() {
        testUpdateNote("123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123")
    }

    @Test
    fun longNote() {
        testUpdateNote(
            "123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123" +
                "123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123" +
                "123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123" +
                "123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123"
        )
    }

    @Test
    fun testEscapedNote() {
        testUpdateNote("test & test")
    }

    private fun testUpdateNote(note: String) {
        assertTrue(CreateFolderRemoteOperation("/note/", true).execute(client).isSuccess)

        // share folder to user "admin"
        val createOperationResult =
            CreateShareRemoteOperation(
                "/note/",
                ShareType.USER,
                "admin",
                false,
                "",
                OCShare.MAXIMUM_PERMISSIONS_FOR_FOLDER,
                true,
                ""
            ).execute(client)

        assertTrue(createOperationResult.isSuccess)

        val share = createOperationResult.resultData[0]

        val sut = UpdateShareRemoteOperation(share.remoteId)
        sut.setNote(note)

        assertTrue(sut.execute(client).isSuccess)

        // verify
        val getShareOperationResult = GetShareRemoteOperation(share.remoteId).execute(client)
        assertTrue(getShareOperationResult.isSuccess)

        val updatedShare = getShareOperationResult.resultData[0]

        assertEquals(note, updatedShare.note)

        assertTrue(RemoveFileRemoteOperation("/note/").execute(client).isSuccess)
    }

    @Test
    fun updateLabel() {
        val label = "test & test"
        assertTrue(CreateFolderRemoteOperation("/label/", true).execute(client).isSuccess)

        // share folder via public link
        val createOperationResult =
            CreateShareRemoteOperation(
                "/label/",
                ShareType.PUBLIC_LINK,
                "",
                true,
                "",
                OCShare.READ_PERMISSION_FLAG
            ).execute(client)

        assertTrue(createOperationResult.isSuccess)

        val share = createOperationResult.resultData[0]

        val sut = UpdateShareRemoteOperation(share.remoteId)
        sut.setLabel(label)

        assertTrue(sut.execute(client).isSuccess)

        // verify
        val getShareOperationResult = GetShareRemoteOperation(share.remoteId).execute(client)
        assertTrue(getShareOperationResult.isSuccess)

        val updatedShare = getShareOperationResult.resultData[0]

        assertEquals(label, updatedShare.label)

        assertTrue(RemoveFileRemoteOperation("/label/").execute(client).isSuccess)
    }

    @Test
    @Suppress("MaxLineLength")
    fun invalidPassword() {
        val folder = "/invalidPassword/"
        assertTrue(CreateFolderRemoteOperation(folder, true).execute(client).isSuccess)

        // share folder via public link
        val createOperationResult =
            CreateShareRemoteOperation(
                folder,
                ShareType.PUBLIC_LINK,
                "",
                true,
                "",
                OCShare.READ_PERMISSION_FLAG
            ).execute(client)

        assertTrue(createOperationResult.isSuccess)

        val share = createOperationResult.resultData[0]

        val sut = UpdateShareRemoteOperation(share.remoteId)
        sut.setPassword("1")

        val result = sut.execute(client)
        assertFalse(result.isSuccess)

        val capabilityResult = GetCapabilitiesRemoteOperation().execute(hnscloudClient)
        assertTrue(capabilityResult.isSuccess)
        val capability = capabilityResult.singleData as OCCapability

        when {
            capability.version.isNewerOrEqual(HnscloudVersion.hnscloud_22) -> {
                assertEquals(
                    "Password needs to be at least 10 characters long. " +
                        "Password is present in compromised password list. " +
                        "Please choose a different password.",
                    result.message
                )
            }
            capability.version.isNewerOrEqual(HnscloudVersion.hnscloud_21) -> {
                assertEquals("Password needs to be at least 8 characters long.", result.message)
            }
            else -> {
                assertEquals("Password needs to be at least 8 characters long", result.message)
            }
        }

        assertTrue(RemoveFileRemoteOperation(folder).execute(client).isSuccess)
    }

    @Test
    fun validPassword() {
        val folder = "/validPassword/"
        assertTrue(CreateFolderRemoteOperation(folder, true).execute(client).isSuccess)

        // share folder via public link
        val createOperationResult =
            CreateShareRemoteOperation(
                folder,
                ShareType.PUBLIC_LINK,
                "",
                true,
                "",
                OCShare.READ_PERMISSION_FLAG
            ).execute(client)

        assertTrue(createOperationResult.isSuccess)

        val share = createOperationResult.resultData[0]

        val sut = UpdateShareRemoteOperation(share.remoteId)
        sut.setPassword("arnservcvcbtp234")

        assertTrue(sut.execute(client).isSuccess)
        assertTrue(RemoveFileRemoteOperation(folder).execute(client).isSuccess)
    }
}
