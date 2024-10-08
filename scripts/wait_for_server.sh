#!/usr/bin/env bash

# SPDX-FileCopyrightText: 2017-2024 Hnscloud GmbH and Hnscloud contributors
# SPDX-FileCopyrightText: 2017 Tobias Kaminsky <tobias@kaminsky.me>
# SPDX-License-Identifier: GPL-3.0-or-later

counter=0
status=""

until [[ $status = "false" ]]; do
    status=$(curl 2>/dev/null "http://$1/status.php" | jq .maintenance)
    
    echo "($counter) $status"

    if [[ "$status" =~ "false" || "$status" = "" ]]; then
        let "counter += 1"
         if [[ $counter -gt 50 ]]; then
            echo "Failed to wait for server"
            exit 1
        fi
    fi

    sleep 10
done
