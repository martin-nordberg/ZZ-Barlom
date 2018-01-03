//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.api.vertices.EdgeAttributeType
import org.barlom.domain.metamodel.api.vertices.VertexAttributeType


class EdgeAttributeTypeActions {

    companion object {

        /**
         * Changes the optionality of an edge attribute type.
         */
        fun changeOptionality(edgeAttributeType: EdgeAttributeType, optionality: EAttributeOptionality): ModelAction {

            return { _: Model ->

                edgeAttributeType.optionality = optionality

                val path = edgeAttributeType.path
                val a = if (optionality.isRequired()) "required" else "optional"

                "Make edge attribute type $path $a."
            }

        }

    }

}

