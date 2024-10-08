/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2015-2016 ownCloud Inc.
 * SPDX-FileCopyrightText: 2015-2016 David A. Velasco <dvelasco@solidgear.es>
 * SPDX-License-Identifier: MIT
 */
package com.owncloud.android.lib.common.network;

import org.apache.commons.httpclient.HttpStatus;

import java.util.Arrays;


/**
 * Aggregate saving the list of URLs followed in a sequence of redirections during the exceution of a
 * {@link com.owncloud.android.lib.common.operations.RemoteOperation}, and the status codes corresponding to all
 * of them.
 *
 * The last status code saved corresponds to the first response not being a redirection, unless the sequence exceeds
 * the maximum length of redirections allowed by the {@link com.owncloud.android.lib.common.OwnCloudClient} instance
 * that ran the operation.
 *
 * If no redirection was followed, the last (and first) status code contained corresponds to the original URL in the
 * request.
 */
public class RedirectionPath {

    private int[] mStatuses = null;

    private int mLastStatus = -1;

    private String[] mLocations = null;

    private int mLastLocation = -1;

    /**
     * Public constructor.
     *
     * @param status            Status code resulting of executing a request on the original URL.
     * @param maxRedirections   Maximum number of redirections that will be contained.
     * @throws IllegalArgumentException     If 'maxRedirections' is < 0
     */
    public RedirectionPath(int status, int maxRedirections) {
        if (maxRedirections < 0) {
            throw new IllegalArgumentException("maxRedirections MUST BE zero or greater");
        }
        mStatuses = new int[maxRedirections + 1];
        Arrays.fill(mStatuses, -1);
        mStatuses[++mLastStatus] = status;
    }

    /**
     * Adds a new location URL to the list of followed redirections.
     *
     * @param location      URL extracted from a 'Location' header in a redirection.
     */
    public void addLocation(String location) {
        if (mLocations == null) {
            mLocations = new String[mStatuses.length - 1];
        }
        if (mLastLocation < mLocations.length - 1) {
            mLocations[++mLastLocation] = location;
        }
    }


    /**
     * Adds a new status code to the list of status corresponding to followed redirections.
     *
     * @param status     Status code from the response of another followed redirection.
     */
    public void addStatus(int status) {
        if (mLastStatus < mStatuses.length - 1) {
            mStatuses[++mLastStatus] = status;
        }
    }

    /**
     * @return      Last status code saved.
     */
    public int getLastStatus() {
        return mStatuses[mLastStatus];
    }

    /**
     * @return      Last location followed corresponding to a permanent redirection (status code 301).
     */
    public String getLastPermanentLocation() {
        for (int i = mLastStatus; i >= 0; i--) {
            if (mStatuses[i] == HttpStatus.SC_MOVED_PERMANENTLY && i <= mLastLocation) {
                return mLocations[i];
            }
        }
        return null;
    }

    /**
     * @return      Count of locations.
     */
    public int getRedirectionsCount() {
        return mLastLocation + 1;
    }
}
