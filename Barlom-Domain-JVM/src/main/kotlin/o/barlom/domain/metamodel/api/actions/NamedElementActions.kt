//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.actions

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.domain.metamodel.api.vertices.AbstractNamedElement

class NamedElementActions {

    companion object {

        /**
         * Renames the given [element] to [newName].
         */
        fun rename(element: AbstractNamedElement, newName: String): ModelAction {

            return { _: Model ->
                val oldPath = element.path
                element.name = newName
                "Renamed $oldPath to $newName."
            }

        }

    }

}
