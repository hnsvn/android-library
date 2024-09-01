#!/usr/bin/env bash

# SPDX-FileCopyrightText: 2017-2024 Hnscloud GmbH and Hnscloud contributors
# SPDX-FileCopyrightText: 2017 Tobias Kaminsky <tobias@kaminsky.me>
# SPDX-License-Identifier: GPL-3.0-or-later

BRANCH=$1
TYPE=$2
PR=$3
GITHUB_USER=$4
GITHUB_PASSWORD=$5
BRANCH_TYPE=$BRANCH-$TYPE
REPO="android-library"

# delete all old comments, matching this type
oldComments=$(curl 2>/dev/null -u $GITHUB_USER:$GITHUB_PASSWORD -X GET https://api.github.com/repos/hnscloud/$REPO/issues/$PR/comments | jq --arg TYPE $BRANCH_TYPE '.[] | (.id |tostring) + "|" + (.user.login | test("hnscloud-android-bot") | tostring) + "|" + (.body | test([$TYPE]) | tostring)'| grep "true|true" | tr -d "\"" | cut -f1 -d"|")

echo $oldComments | while read comment ; do
    curl 2>/dev/null -u $GITHUB_USER:$GITHUB_PASSWORD -X DELETE https://api.github.com/repos/hnscloud/$REPO/issues/comments/$comment
done

exit 0
