//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.actions

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.domain.metamodel.api.types.EAttributeOptionality
import o.barlom.domain.metamodel.api.types.ELabelDefaulting
import o.barlom.domain.metamodel.api.vertices.ConstrainedDataType
import o.barlom.domain.metamodel.api.vertices.VertexAttributeType


class VertexAttributeTypeActions {

    companion object {

        /**
         * Changes the data type of a vertex attribute type.
         */
        fun changeDataType(vertexAttributeType: VertexAttributeType, dataTypePath: String): ModelAction {

            return { model: Model ->

                val dataType: ConstrainedDataType? = model.rootPackage.findDataTypeByPath(dataTypePath)

                if (dataType != null) {
                    while (!vertexAttributeType.dataTypeUsages.isEmpty()) {
                        vertexAttributeType.dataTypeUsages.get(0).remove()
                    }
                    model.makeAttributeDataTypeUsage(vertexAttributeType, dataType)
                }

                val path = vertexAttributeType.path

                "Set vertex attribute type $path data type to $dataTypePath."
            }

        }

        /**
         * Changes the label defaulting of a vertex attribute type.
         */
        fun changeLabelDefaulting(vertexAttributeType: VertexAttributeType,
                                  labelDefaulting: ELabelDefaulting): ModelAction {

            return { _: Model ->

                vertexAttributeType.labelDefaulting = labelDefaulting

                val path = vertexAttributeType.path
                val a = if (labelDefaulting.isDefaultLabel()) "the" else "not the"

                "Made vertex attribute type $path $a default label."
            }

        }

        /**
         * Changes the optionality of a vertex attribute type.
         */
        fun changeOptionality(vertexAttributeType: VertexAttributeType,
                              optionality: EAttributeOptionality): ModelAction {

            return { _: Model ->

                vertexAttributeType.optionality = optionality

                val path = vertexAttributeType.path
                val a = if (optionality.isRequired()) "required" else "optional"

                "Made vertex attribute type $path $a."
            }

        }

    }

}

