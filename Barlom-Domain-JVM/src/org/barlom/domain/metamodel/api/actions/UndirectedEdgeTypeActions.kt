//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.UndirectedEdgeType


class UndirectedEdgeTypeActions {

    companion object {

        /**
         * Changes the maximum degree of the given [edgeType].
         */
        fun changeMaxDegree(edgeType: UndirectedEdgeType, maxDegree: Int?): ModelAction {

            return { _: Model ->

                edgeType.maxDegree = maxDegree

                val path = edgeType.path

                "Change edge type $path maximum degree to $maxDegree."
            }

        }

        /**
         * Changes the minimum degree of the given [edgeType].
         */
        fun changeMinDegree(edgeType: UndirectedEdgeType, minDegree: Int?): ModelAction {

            return { _: Model ->

                edgeType.minDegree = minDegree

                val path = edgeType.path

                "Change edge type $path minimum degree to $minDegree."
            }

        }

        /**
         * Changes the super type of a directed edge type.
         */
        fun changeSuperType(edgeType: UndirectedEdgeType, superTypePath: String): ModelAction {

            return { model: Model ->

                val superType: UndirectedEdgeType? = model.rootPackage.findUndirectedEdgeTypeByPath(superTypePath)

                if (superType != null) {
                    while (!edgeType.superTypeInheritances.isEmpty()) {
                        edgeType.superTypeInheritances.get(0).remove()
                    }
                    model.makeUndirectedEdgeTypeInheritance(superType, edgeType)
                }

                val path = edgeType.path

                "Set vertex type $path super type to $superTypePath."
            }

        }

    }

}

