/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2019-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2019-2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2019 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import android.net.Uri;
import android.os.Parcel;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OwnCloudAccountTest {

    @Test
    public void parcelableIsImplemented() {
        Uri uri = Uri.parse("https://hnscloud.localhost.localdomain");
        OwnCloudCredentials credentials = new OwnCloudBasicCredentials(
                "user",
                "pass",
                true
        );
        OwnCloudAccount original = new OwnCloudAccount(uri, credentials);
        Parcel parcel = Parcel.obtain();
        parcel.setDataPosition(0);
        parcel.writeParcelable(original, 0);
        parcel.setDataPosition(0);
        OwnCloudAccount retrieved = parcel.readParcelable(OwnCloudAccount.class.getClassLoader());

        assertNotSame(original, retrieved);
        assertEquals(original, retrieved);
    }
}
