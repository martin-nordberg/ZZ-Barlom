//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.Package


class PackageActions {

    companion object {

        /**
         * Adds a new constrained data type to the given [parentPackage].
         */
        fun addConstrainedString(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeConstrainedString {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Add a constrained data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new directed edge type to the given [parentPackage].
         */
        fun addDirectedEdgeType(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeDirectedEdgeType {
                    model.makeDirectedEdgeTypeContainment(parentPackage, this)
                }

                "Add a directed edge type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new child package to the given [parentPackage].
         */
        fun addPackage(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makePackage {
                    model.makePackageContainment(parentPackage, this)
                }

                "Add a package to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new undirected edge type to the given [parentPackage].
         */
        fun addUndirectedEdgeType(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeUndirectedEdgeType {
                    model.makeUndirectedEdgeTypeContainment(parentPackage, this)
                }

                "Add an undirected edge type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new vertex type to the given [parentPackage].
         */
        fun addVertexType(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeVertexType {
                    model.makeVertexTypeContainment(parentPackage, this)
                }

                "Add a vertex type to ${parentPackage.path}"

            }

        }

    }

}

