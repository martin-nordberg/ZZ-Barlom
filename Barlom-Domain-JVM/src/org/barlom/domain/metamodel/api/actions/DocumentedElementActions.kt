//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices.AbstractNamedElement

class DocumentedElementActions {

    companion object {

        /**
         * Changes the description of the given [element] to [newDescription].
         */
        fun describe(element: AbstractDocumentedElement, newDescription: String): ModelAction {

            return { _: Model ->
                element.description = newDescription
                "Change description of element to ${element.firstLineOfDescription}."
            }

        }

    }

}
