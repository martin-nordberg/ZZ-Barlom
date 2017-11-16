//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.vertices.AbstractEdgeType


class AbstractEdgeTypeActions {

    companion object {

        /**
         * Changes the abstractness of the given [edgeType].
         */
        fun changeAbstractness(edgeType: AbstractEdgeType, abstractness: EAbstractness): ModelAction {

            return { model: Model ->
                val path = edgeType.path
                edgeType.abstractness = abstractness
                "Make edge type ${path} ${abstractness.toString().toLowerCase()}."
            }

        }

        /**
         * Changes the cyclicity of the given [edgeType].
         */
        fun changeCyclicity(edgeType: AbstractEdgeType, cyclicity: ECyclicity): ModelAction {

            return { model: Model ->
                val path = edgeType.path
                edgeType.cyclicity = cyclicity
                "Make edge type ${path} ${cyclicity.toString().toLowerCase()}."
            }

        }

    }

}

