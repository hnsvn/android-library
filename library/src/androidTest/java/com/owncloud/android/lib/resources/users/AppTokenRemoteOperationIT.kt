/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2021-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2021 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.users

import androidx.test.platform.app.InstrumentationRegistry
import com.owncloud.android.AbstractIT
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import okhttp3.Credentials.basic
import org.junit.Test

class AppTokenRemoteOperationIT : AbstractIT() {
    @Test
    fun createAppPassword() {
        val sut = ConvertAppTokenRemoteOperation()
        val result = sut.execute(hnscloudClient)

        assertTrue(result.isSuccess)

        val arguments = InstrumentationRegistry.getArguments()

        val password: String? = arguments.getString("TEST_SERVER_PASSWORD")
        val newPassword = result.resultData

        assertTrue(newPassword.isNotBlank())
        assertTrue(password != newPassword)
    }

    @Test
    fun checkAppPassword() {
        // first create new app token
        val sut = ConvertAppTokenRemoteOperation()
        val result = sut.execute(hnscloudClient)

        assertTrue(result.isSuccess)

        val arguments = InstrumentationRegistry.getArguments()
        val username: String = arguments.getString("TEST_SERVER_USERNAME", "")
        val password: String = arguments.getString("TEST_SERVER_PASSWORD", "")
        val newPassword = result.resultData

        assertTrue(newPassword.isNotBlank())
        assertTrue(password != newPassword)

        // second use this app token to check
        hnscloudClient.credentials = basic(username, newPassword)
        val result2 = sut.execute(hnscloudClient)

        assertTrue(result2.isSuccess)
        assertTrue(result2.resultData.isEmpty())
    }

    @Test
    fun deleteAppPassword() {
        val arguments = InstrumentationRegistry.getArguments()
        val username: String = arguments.getString("TEST_SERVER_USERNAME", "")
        val password: String = arguments.getString("TEST_SERVER_PASSWORD", "")
        hnscloudClient.credentials = basic(username, password)

        // first: create a new app password
        val createPassword = ConvertAppTokenRemoteOperation()
        val result = createPassword.execute(hnscloudClient)

        assertTrue(result.isSuccess)

        val newPassword = result.resultData

        assertTrue(newPassword.isNotBlank())
        assertTrue(password != newPassword)

        hnscloudClient.credentials = basic(username, newPassword)

        // check that new password works
        assertTrue(createPassword.execute(hnscloudClient).isSuccess)

        // second: delete this new app password
        val sut = DeleteAppPasswordRemoteOperation()
        val result2 = sut.execute(hnscloudClient)

        assertTrue(result2.isSuccess)

        // check if old password can still be used
        assertFalse(createPassword.execute(hnscloudClient).isSuccess)
    }

    override fun after() {
        // do nothing
    }
}
