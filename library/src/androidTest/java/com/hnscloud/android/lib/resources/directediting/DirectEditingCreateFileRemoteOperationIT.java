/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2019-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.hnscloud.android.lib.resources.directediting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.owncloud.android.AbstractIT;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;

import org.junit.Test;

public class DirectEditingCreateFileRemoteOperationIT extends AbstractIT {
    @Test
    public void createEmptyFile() {
        RemoteOperationResult<String> result = new DirectEditingCreateFileRemoteOperation("/test.md",
                "text",
                "textdocument")
                .execute(client);
        assertTrue(result.isSuccess());

        String url = result.getResultData();

        assertFalse(url.isEmpty());
    }

    @Test
    public void createFileFromTemplate() {
        RemoteOperationResult<String> result = new DirectEditingCreateFileRemoteOperation("/test.md",
                                                                                          "text",
                                                                                          "textdocument",
                                                                                          "1")
                .execute(client);
        assertTrue(result.isSuccess());

        String url = result.getResultData();

        assertFalse(url.isEmpty());
    }

    @Test
    public void createFileWithSpecialCharacterFromTemplate() {
        RemoteOperationResult<String> result = new DirectEditingCreateFileRemoteOperation("/あ.md",
                                                                                          "text",
                                                                                          "textdocument",
                                                                                          "1")
                .execute(client);
        assertTrue(result.isSuccess());

        String url = result.getResultData();

        assertFalse(url.isEmpty());
    }
}
