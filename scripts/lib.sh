#!/usr/bin/env bash
#
# Hnscloud Android Library
# SPDX-FileCopyrightText: 2022-2024 Hnscloud GmbH and Hnscloud contributors
# SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
# SPDX-License-Identifier: MIT

## This file is intended to be sourced by other scripts


function err() {
    echo >&2 "$@"
}


function curl_gh() {
    if [[ -n "$GITHUB_TOKEN" ]]
    then
        curl \
            --silent \
            --header "Authorization: token $GITHUB_TOKEN" \
            "$@"
    else
        err "WARNING: No GITHUB_TOKEN found. Skipping API call"
    fi

}
