//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.actions

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.domain.metamodel.api.types.EAttributeOptionality
import o.barlom.domain.metamodel.api.vertices.ConstrainedDataType
import o.barlom.domain.metamodel.api.vertices.EdgeAttributeType


class EdgeAttributeTypeActions {

    companion object {

        /**
         * Changes the data type of an edge attribute type.
         */
        fun changeDataType(edgeAttributeType: EdgeAttributeType, dataTypePath: String): ModelAction {

            return { model: Model ->

                val dataType: ConstrainedDataType? = model.rootPackage.findDataTypeByPath(dataTypePath)

                if (dataType != null) {
                    while (!edgeAttributeType.dataTypeUsages.isEmpty()) {
                        edgeAttributeType.dataTypeUsages.get(0).remove()
                    }
                    model.makeAttributeDataTypeUsage(edgeAttributeType, dataType)
                }

                val path = edgeAttributeType.path

                "Set edge attribute type $path data type to $dataTypePath."
            }

        }

        /**
         * Changes the optionality of an edge attribute type.
         */
        fun changeOptionality(edgeAttributeType: EdgeAttributeType, optionality: EAttributeOptionality): ModelAction {

            return { _: Model ->

                edgeAttributeType.optionality = optionality

                val path = edgeAttributeType.path
                val a = if (optionality.isRequired()) "required" else "optional"

                "Made edge attribute type $path $a."
            }

        }

    }

}

