/*
 * Hnscloud Android Library
 *
 * SPDX-FileCopyrightText: 2023-2024 Hnscloud GmbH and Hnscloud contributors
 * SPDX-FileCopyrightText: 2023 Alper Ozturk <alper_ozturk@proton.me>
 * SPDX-License-Identifier: MIT
 */

package com.hnscloud.extensions

import org.w3c.dom.Element

@Suppress("ReturnCount", "NestedBlockDepth")
inline fun <reified T> ArrayList<*>.processXmlData(tagName: String): T? {
    this.forEach {
        val element = it as? Element
        if (element != null && element.tagName == tagName) {
            val textContent = element.firstChild.textContent

            return when (T::class) {
                Float::class -> {
                    val floatValue = textContent.toFloatOrNull()
                    if (floatValue != null) floatValue as T else null
                }
                Double::class -> {
                    val doubleValue = textContent.toDoubleOrNull()
                    if (doubleValue != null) doubleValue as T else null
                }
                else -> return null
            }
        }
    }

    return null
}
