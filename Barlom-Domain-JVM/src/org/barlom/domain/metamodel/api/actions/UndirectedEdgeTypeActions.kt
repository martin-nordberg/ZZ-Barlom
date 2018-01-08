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

    }

}

