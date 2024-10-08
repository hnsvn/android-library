/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2023-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2023 Alper Ozturk <alper_ozturk@proton.me>
 * SPDX-License-Identifier: MIT
 */

package com.hnscloud.extensions

import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

fun createElement(
    xml: String,
    value: String
): Element {
    val builder =
        DocumentBuilderFactory.newInstance().run {
            newDocumentBuilder()
        }
    val document = builder.newDocument()
    val element =
        document.createElement(xml).apply {
            textContent = value
        }
    document.appendChild(element)
    return element
}
