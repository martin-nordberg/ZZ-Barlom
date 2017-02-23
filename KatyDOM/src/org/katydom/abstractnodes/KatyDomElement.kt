//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract class representing a KDom virtual element. Corresponds to DOM Element.
 */
abstract class KatyDomElement(
    selector: String?,
    content: KatyDomContent
) : KatyDomNode(content.childNodes) {

    val classList: Set<String>

    val dataset = content.dataset

    val id: String?

    val otherAttributes: List<KatyDomAttribute> = content.attributes.filter { it.name !== "class" && it.name != "id" }

    init {

        // Look for a "class" attribute and split its space-separated value.
        val className = content.attributes.find { it.name == "class" }?.value
        val classAttributes: Set<String> =
            if (className == null || className.isEmpty()) {
                setOf()
            }
            else {
                className.split(" ").toHashSet()
            }

        // Parse the id and classes out of the selector as relevant.
        val selectorPieces = selector?.split(".") ?: listOf()
        if (selectorPieces.isEmpty()) {
            id = content.attributes.find { it.name == "id" }?.value
            classList = classAttributes
        }
        else {

            var firstClassIdx = 0
            if (selectorPieces[0].startsWith("#")) {
                id = selectorPieces[0].substring(1)
                firstClassIdx = 1
                // TODO: warning if there is also an id attribute in content
            }
            else {
                id = content.attributes.find { it.name == "id" }?.value
                if (selectorPieces[0].isEmpty()) {
                    firstClassIdx = 1
                }
            }

            classList = classAttributes.plus(selectorPieces.subList(firstClassIdx, selectorPieces.size))

        }

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
