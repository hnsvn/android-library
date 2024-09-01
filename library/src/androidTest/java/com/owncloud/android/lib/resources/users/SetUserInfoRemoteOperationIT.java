/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2020-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2022 Unpublished <unpublished@users.noreply.github.com>
 * SPDX-FileCopyrightText: 2020-2021 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2018 Bartosz Przybylski <bart.p.pl@gmail.com>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.owncloud.android.AbstractIT;
import com.owncloud.android.lib.common.UserInfo;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.resources.status.GetCapabilitiesRemoteOperation;
import com.owncloud.android.lib.resources.status.HnscloudVersion;
import com.owncloud.android.lib.resources.status.OCCapability;

import org.junit.Test;

public class SetUserInfoRemoteOperationIT extends AbstractIT {
    @Test
    public void testSetEmail() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().getEmail();

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.EMAIL, "new@mail.com")
                .execute(hnscloudClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("new@mail.com", userInfo.getResultData().getEmail());

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.EMAIL, oldValue)
                .execute(hnscloudClient).isSuccess());
    }

    @Test
    public void testSetDisplayName() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());

        String oldUserId = hnscloudClient.getUserId();
        assertEquals(hnscloudClient.getUserId(), userInfo.getResultData().getDisplayName());

        // set display name
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.DISPLAYNAME, "newName")
                .execute(hnscloudClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("newName", userInfo.getResultData().getDisplayName());

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.DISPLAYNAME, oldUserId)
                .execute(hnscloudClient).isSuccess());
    }

    @Test
    public void testSetPhone() {
        RemoteOperationResult result = new GetCapabilitiesRemoteOperation().execute(hnscloudClient);
        assertTrue(result.isSuccess());
        OCCapability ocCapability = (OCCapability) result.getSingleData();

        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().getPhone();

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.PHONE, "+49555-12345")
                .execute(hnscloudClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());

        if (ocCapability.getVersion().isNewerOrEqual(HnscloudVersion.hnscloud_21)) {
            assertEquals("+4955512345", userInfo.getResultData().getPhone());
        } else {
            assertEquals("+49555-12345", userInfo.getResultData().getPhone());
        }

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.PHONE, oldValue)
                .execute(hnscloudClient).isSuccess());
    }

    @Test
    public void testSetAddress() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().getAddress();

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.ADDRESS, "NoName Street 123")
                .execute(hnscloudClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("NoName Street 123", userInfo.getResultData().getAddress());

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.ADDRESS, oldValue)
                .execute(hnscloudClient).isSuccess());
    }

    @Test
    public void testSetWebsite() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().getWebsite();

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.WEBSITE, "https://hns.vn")
                .execute(hnscloudClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("https://hns.vn", userInfo.getResultData().getWebsite());

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.WEBSITE, oldValue)
                .execute(hnscloudClient).isSuccess());
    }

    @Test
    public void testSetTwitter() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().getTwitter();

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.TWITTER, "@Hnsclouders")
                .execute(hnscloudClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(hnscloudClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("@Hnsclouders", userInfo.getResultData().getTwitter());

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.TWITTER, oldValue)
                .execute(hnscloudClient).isSuccess());
    }
}
