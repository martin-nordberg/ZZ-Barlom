//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.api.types.ELabelDefaulting
import org.barlom.domain.metamodel.api.vertices.VertexAttributeType
import org.barlom.domain.metamodel.api.vertices.VertexType


class VertexAttributeTypeActions {

    companion object {

        /**
         * Changes the label defaulting of a vertex attribute type.
         */
        fun changeLabelDefaulting(vertexAttributeType: VertexAttributeType, labelDefaulting: ELabelDefaulting): ModelAction {

            return { _: Model ->

                vertexAttributeType.labelDefaulting = labelDefaulting

                val path = vertexAttributeType.path
                val a = if (labelDefaulting.isDefaultLabel()) "the" else "not the"

                "Make vertex attribute type $path $a default label."
            }

        }

        /**
         * Changes the optionality of a vertex attribute type.
         */
        fun changeOptionality(vertexAttributeType: VertexAttributeType, optionality: EAttributeOptionality): ModelAction {

            return { _: Model ->

                vertexAttributeType.optionality = optionality

                val path = vertexAttributeType.path
                val a = if (optionality.isRequired()) "required" else "optional"

                "Make vertex attribute type $path $a."
            }

        }

    }

}

