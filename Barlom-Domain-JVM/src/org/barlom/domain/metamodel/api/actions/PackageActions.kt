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
         * Adds a new constrained 32-bit boolean data type to the given [parentPackage].
         */
        fun addConstrainedBoolean(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeConstrainedBoolean {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Add a constrained boolean data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained date/time data type to the given [parentPackage].
         */
        fun addConstrainedDateTime(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeConstrainedDateTime {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Add a constrained date/time data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained 64-bit floating point data type to the given [parentPackage].
         */
        fun addConstrainedFloat64(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeConstrainedFloat64 {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Add a constrained 64-bit floating point data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained 32-bit integer data type to the given [parentPackage].
         */
        fun addConstrainedInteger32(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeConstrainedInteger32 {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Add a constrained 32-bit integer data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained string data type to the given [parentPackage].
         */
        fun addConstrainedString(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeConstrainedString {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Add a constrained string data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained UUID data type to the given [parentPackage].
         */
        fun addConstrainedUuid(parentPackage: Package): ModelAction {

            return { model: Model ->

                model.makeConstrainedUuid {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Add a constrained UUID data type to ${parentPackage.path}"

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

