/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2023-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2023 Álvaro Brey <alvaro.brey@hns.vn>
 * SPDX-FileCopyrightText: 2018 Bartosz Przybylski <bart.p.pl@gmail.com>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.ocs;

import com.google.gson.annotations.SerializedName;

/**
 * Wrapper for server response
 *
 * @author Bartosz Przybylski
 */
public class ServerResponse<T extends Object> {
    @SerializedName("ocs")
    public OCSResponse<T> ocs;

    public OCSResponse<T> getOcs() {
        return this.ocs;
    }
}
