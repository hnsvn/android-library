#!/bin/bash

# SPDX-FileCopyrightText: 2017-2024 Hnscloud GmbH and Hnscloud contributors
# SPDX-FileCopyrightText: 2017 Tobias Kaminsky <tobias@kaminsky.me>
# SPDX-License-Identifier: GPL-3.0-or-later

# $1: username, $2: password/token, $3: pull request number

if [ -z $3 ] ; then
    echo "master";
else
    curl 2>/dev/null -u $1:$2 https://api.github.com/repos/hnscloud/android-library/pulls/$3 | jq .base.ref
fi
