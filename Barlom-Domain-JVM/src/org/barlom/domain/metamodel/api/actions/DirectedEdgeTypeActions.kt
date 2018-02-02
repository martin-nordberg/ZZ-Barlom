//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.DirectedEdgeType


class DirectedEdgeTypeActions {

    companion object {

        /**
         * Changes the forward directed name of the given [edgeType].
         */
        fun changeForwardName(edgeType: DirectedEdgeType, forwardName: String): ModelAction {

            return { _: Model ->

                edgeType.forwardName = forwardName

                val path = edgeType.path

                "Change edge type $path forward directed name to $forwardName."
            }

        }

        /**
         * Changes the head role name of the given [edgeType].
         */
        fun changeHeadRoleName(edgeType: DirectedEdgeType, headRoleName: String): ModelAction {

            return { _: Model ->

                edgeType.headRoleName = headRoleName

                val path = edgeType.path

                "Change edge type $path head role name to $headRoleName."
            }

        }

        /**
         * Changes the maximum head in-degree of the given [edgeType].
         */
        fun changeMaxHeadInDegree(edgeType: DirectedEdgeType, maxHeadInDegree: Int?): ModelAction {

            return { _: Model ->

                edgeType.maxHeadInDegree = maxHeadInDegree

                val path = edgeType.path

                "Change edge type $path maximum head in-degree to $maxHeadInDegree."
            }

        }

        /**
         * Changes the maximum tail out-degree of the given [edgeType].
         */
        fun changeMaxTailOutDegree(edgeType: DirectedEdgeType, maxTailOutDegree: Int?): ModelAction {

            return { _: Model ->

                edgeType.maxTailOutDegree = maxTailOutDegree

                val path = edgeType.path

                "Change edge type $path maximum tail out-degree to $maxTailOutDegree."
            }

        }

        /**
         * Changes the minimum head in-degree of the given [edgeType].
         */
        fun changeMinHeadInDegree(edgeType: DirectedEdgeType, minHeadInDegree: Int?): ModelAction {

            return { _: Model ->

                edgeType.minHeadInDegree = minHeadInDegree

                val path = edgeType.path

                "Change edge type $path minimum head in-degree to $minHeadInDegree."
            }

        }

        /**
         * Changes the minimum tail out-degree of the given [edgeType].
         */
        fun changeMinTailOutDegree(edgeType: DirectedEdgeType, minTailOutDegree: Int?): ModelAction {

            return { _: Model ->

                edgeType.minTailOutDegree = minTailOutDegree

                val path = edgeType.path

                "Change edge type $path minimum tail out-degree to $minTailOutDegree."
            }

        }

        /**
         * Changes the reverse directed name of the given [edgeType].
         */
        fun changeReverseName(edgeType: DirectedEdgeType, reverseName: String): ModelAction {

            return { _: Model ->

                edgeType.reverseName = reverseName

                val path = edgeType.path

                "Change edge type $path reverse directed name to $reverseName."
            }

        }

        /**
         * Changes the super type of a directed edge type.
         */
        fun changeSuperType(edgeType: DirectedEdgeType, superTypePath: String): ModelAction {

            return { model: Model ->

                val superType: DirectedEdgeType? = model.rootPackage.findDirectedEdgeTypeByPath(superTypePath)

                if (superType != null) {
                    while (!edgeType.superTypeInheritances.isEmpty()) {
                        edgeType.superTypeInheritances.get(0).remove()
                    }
                    model.makeDirectedEdgeTypeInheritance(superType, edgeType)
                }

                val path = edgeType.path

                "Set vertex type $path super type to $superTypePath."
            }

        }

        /**
         * Changes the tail role name of the given [edgeType].
         */
        fun changeTailRoleName(edgeType: DirectedEdgeType, tailRoleName: String): ModelAction {

            return { _: Model ->

                edgeType.tailRoleName = tailRoleName

                val path = edgeType.path

                "Change edge type $path tail role name to $tailRoleName."
            }

        }

    }

}

