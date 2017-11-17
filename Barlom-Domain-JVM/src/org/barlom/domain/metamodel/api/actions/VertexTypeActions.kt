//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.vertices.VertexType


class VertexTypeActions {

    companion object {

        /**
         * Changes the abstractness of the given [vertexType].
         */
        fun changeAbstractness(vertexType: VertexType, abstractness: EAbstractness): ModelAction {

            return { _: Model ->

                vertexType.abstractness = abstractness

                val path = vertexType.path
                val a = if (abstractness.isAbstract()) "abstract" else "concrete"

                "Make vertex type $path $a."
            }

        }

    }

}

