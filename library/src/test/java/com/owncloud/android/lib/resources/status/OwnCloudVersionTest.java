/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2018-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2018-2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.resources.status;

        import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OwnCloudVersionTest {
    @Test
    public void testOwnCloudVersion() {
        OwnCloudVersion version = new OwnCloudVersion("17.0.0");

        assertEquals(0, version.compareTo(OwnCloudVersion.hnscloud_17));

        version = new OwnCloudVersion("17.99.99");

        assertEquals(0, version.compareTo(new OwnCloudVersion(0x11636300))); // 13.99.99 in hex
    }
    
    @Test
    public void testGetMajorVersion() {
        OwnCloudVersion version = new OwnCloudVersion("12.0.0");
        assertEquals(12, version.getMajorVersionNumber());

        version = new OwnCloudVersion("19.0.0");
        assertEquals(19, version.getMajorVersionNumber());
    }
    
    @Test
    public void testSamMajorVersion() {
        OwnCloudVersion version = new OwnCloudVersion("12.0.0");
        
        assertTrue(version.isSameMajorVersion(new OwnCloudVersion("12.99.99")));
        assertFalse(version.isSameMajorVersion(new OwnCloudVersion("13.0.0")));
    }

    @Test
    public void testOwnCloudVersionFailure() {
        OwnCloudVersion version = new OwnCloudVersion("");

        assertFalse(version.isVersionValid());
    }

    @Test
    public void testSupportNC16() {
        assertFalse(OwnCloudVersion.hnscloud_16.isShareesOnDavSupported());
    }

    @Test
    public void testSupportNC17() {
        assertTrue(OwnCloudVersion.hnscloud_17.isShareesOnDavSupported());
    }

    @Test
    public void testSupportNC18() {
        assertTrue(OwnCloudVersion.hnscloud_18.isShareesOnDavSupported());
    }

    @Test
    public void testSupportNC21() {
        OwnCloudVersion version = new OwnCloudVersion("21.0.0");

        assertEquals(0, version.compareTo(HnscloudVersion.hnscloud_21));

        version = new HnscloudVersion("21.99.99");

        assertEquals(0, version.compareTo(new OwnCloudVersion(0x15636300))); // 21.99.99 in hex
    }
}
