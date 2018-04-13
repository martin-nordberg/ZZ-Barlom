//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.actions

import o.org.barlom.domain.metamodel.api.model.Model
import o.org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement

class DocumentedElementActions {

    companion object {

        /**
         * Changes the description of the given [element] to [newDescription].
         */
        fun describe(element: AbstractDocumentedElement, newDescription: String): ModelAction {

            return { _: Model ->
                element.description = newDescription
                "Changed description of element to ${element.firstLineOfDescription}."
            }

        }

    }

}
