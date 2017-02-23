//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomAttribute

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * KatyDOM content builder for attributes and event handlers available to all nodes. Serves as a base class for more
 * specialized content builders that also add child nodes of the right types for given context.
 */
@KatyDomContentBuilderDsl
open class KatyDomAttributeContentBuilder {

    /** The content being built by this builder. */
    internal val content: KatyDomContentUnderConstruction = KatyDomContentUnderConstruction()

    /**
     * Adds one attribute to the content.
     * @param name the name of the attribute.
     * @param value the string value of the attribute.
     */
    fun attribute(
        name: String,
        value: String
    ) {
        content.addAttribute(KatyDomAttribute(name, value))
    }

    /**
     * Adds multiple attributes to the content being built.
     * @param pairs a list of the names (first) and values (second) for the attributes to add.
     */
    fun attributes(vararg pairs: Pair<String, String>) {
        pairs.forEach { pair ->
            attribute(pair.first, pair.second)
        }
    }

    /**
     * Adds one data attribute to the content being built.
     * @param name the name of the data attribute. May have the "data-" prefix omitted.
     * @param value the string value of the data attribute.
     */
    fun data(
        name: String,
        value: String
    ) {
        if (name.startsWith("data-")) {
            content.addDataAttribute(KatyDomAttribute(name, value))
        }
        else {
            content.addDataAttribute(KatyDomAttribute("data-" + name, value))
        }
    }

    /**
     * Adds multiple data attributes to the content being built.
     * @param pairs a list of the names (first) and values (second) for the attributes to add. Names may have the
     * "data-" prefix omitted.
     */
    fun dataset(vararg pairs: Pair<String, String>) {
        pairs.forEach { pair ->
            data(pair.first, pair.second)
        }
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

