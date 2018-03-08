//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.UndirectedEdgeType
import org.barlom.domain.metamodel.api.vertices.VertexType


class UndirectedEdgeTypeActions {

    companion object {

        /**
         * Changes the connected vertex of an undirected edge type.
         */
        fun changeConnectedVertexType(edgeType: UndirectedEdgeType, vertexTypePath: String): ModelAction {

            return { model: Model ->

                val vertexType: VertexType? = model.rootPackage.findVertexTypeByPath(vertexTypePath)

                if (vertexType != null) {
                    while (!edgeType.connectivities.isEmpty()) {
                        edgeType.connectivities[0].remove()
                    }
                    model.makeUndirectedEdgeTypeConnectivity(edgeType, vertexType)
                }

                val path = edgeType.path

                "Set undirected edge type $path connected vertex type to $vertexTypePath."
            }

        }

        /**
         * Changes the maximum degree of the given [edgeType].
         */
        fun changeMaxDegree(edgeType: UndirectedEdgeType, maxDegree: Int?): ModelAction {

            return { _: Model ->

                edgeType.maxDegree = maxDegree

                val path = edgeType.path

                "Changed edge type $path maximum degree to $maxDegree."
            }

        }

        /**
         * Changes the minimum degree of the given [edgeType].
         */
        fun changeMinDegree(edgeType: UndirectedEdgeType, minDegree: Int?): ModelAction {

            return { _: Model ->

                edgeType.minDegree = minDegree

                val path = edgeType.path

                "Changed edge type $path minimum degree to $minDegree."
            }

        }

        /**
         * Changes the super type of an undirected edge type.
         */
        fun changeSuperType(edgeType: UndirectedEdgeType, superTypePath: String): ModelAction {

            return { model: Model ->

                val superType: UndirectedEdgeType? = model.rootPackage.findUndirectedEdgeTypeByPath(superTypePath)

                if (superType != null) {
                    while (!edgeType.superTypeInheritances.isEmpty()) {
                        edgeType.superTypeInheritances[0].remove()
                    }
                    model.makeUndirectedEdgeTypeInheritance(superType, edgeType)
                }

                val path = edgeType.path

                "Set undirected edge type $path super type to $superTypePath."
            }

        }

    }

}

