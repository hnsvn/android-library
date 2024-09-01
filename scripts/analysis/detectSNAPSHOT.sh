#!/bin/bash

# SPDX-FileCopyrightText: 2017-2024 Hnscloud GmbH and Hnscloud contributors
# SPDX-FileCopyrightText: 2017 Tobias Kaminsky <tobias@kaminsky.me>
# SPDX-License-Identifier: GPL-3.0-or-later

count=$(./gradlew dependencies | grep SNAPSHOT -c)

if [ $count -eq 0 ] ; then
    exit 0
else
    exit 1
fi

