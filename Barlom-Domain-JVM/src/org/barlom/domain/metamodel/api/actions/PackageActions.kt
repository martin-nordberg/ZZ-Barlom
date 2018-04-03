//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.Package
import org.barlom.infrastructure.uuids.Uuid


class PackageActions {

    companion object {

        /**
         * Adds a new constrained 32-bit boolean data type with given [id] to the given [parentPackage].
         */
        fun addConstrainedBoolean(parentPackage: Package, id: Uuid): ModelAction {

            return { model: Model ->

                model.makeConstrainedBoolean(id) {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Added a constrained boolean data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained date/time data type with given [id] to the given [parentPackage].
         */
        fun addConstrainedDateTime(parentPackage: Package, id: Uuid): ModelAction {

            return { model: Model ->

                model.makeConstrainedDateTime(id) {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Added a constrained date/time data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained 64-bit floating point data type with given [id] to the given [parentPackage].
         */
        fun addConstrainedFloat64(parentPackage: Package, id: Uuid): ModelAction {

            return { model: Model ->

                model.makeConstrainedFloat64(id) {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Added a constrained 64-bit floating point data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained 32-bit integer data type with given [id] to the given [parentPackage].
         */
        fun addConstrainedInteger32(parentPackage: Package, id: Uuid): ModelAction {

            return { model: Model ->

                model.makeConstrainedInteger32(id) {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Added a constrained 32-bit integer data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained string data type with given [id] to the given [parentPackage].
         */
        fun addConstrainedString(parentPackage: Package, id: Uuid): ModelAction {

            return { model: Model ->

                model.makeConstrainedString(id) {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Added a constrained string data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new constrained UUID data type with given [id] to the given [parentPackage].
         */
        fun addConstrainedUuid(parentPackage: Package, id: Uuid): ModelAction {

            return { model: Model ->

                model.makeConstrainedUuid(id) {
                    model.makeConstrainedDataTypeContainment(parentPackage, this)
                }

                "Added a constrained UUID data type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new directed edge type with given [id] to the given [parentPackage].
         */
        fun addDirectedEdgeType(parentPackage: Package, id: Uuid): ModelAction {

            return { model: Model ->

                model.makeDirectedEdgeType(id) {
                    model.makeDirectedEdgeTypeContainment(parentPackage, this)
                }

                "Added a directed edge type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new child package with given [id] to the given [parentPackage].
         */
        fun addPackage(parentPackage: Package, id: Uuid): ModelAction {

            return { model: Model ->

                model.makePackage(id) {
                    model.makePackageContainment(parentPackage, this)
                }

                "Added a package to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new undirected edge type with given [id] to the given [parentPackage].
         */
        fun addUndirectedEdgeType(parentPackage: Package, id:Uuid): ModelAction {

            return { model: Model ->

                model.makeUndirectedEdgeType(id) {
                    model.makeUndirectedEdgeTypeContainment(parentPackage, this)
                }

                "Added an undirected edge type to ${parentPackage.path}"

            }

        }

        /**
         * Adds a new vertex type with given [id] to the given [parentPackage].
         */
        fun addVertexType(parentPackage: Package, id: Uuid): ModelAction {

            return { model: Model ->

                model.makeVertexType(id) {
                    model.makeVertexTypeContainment(parentPackage, this)
                }

                "Added a vertex type to ${parentPackage.path}"

            }

        }

        fun addPackageSupplier(pkg: Package, supplierPath: String): ModelAction {

            return { model: Model ->

                val supplier: Package? = model.rootPackage.findPackageByPath(supplierPath)

                if (supplier != null) {
                    model.makePackageDependency(pkg, supplier)

                    val path = pkg.path

                    "Added dependency from $path to $supplierPath."
                }
                else {
                    "Supplier package $supplierPath not found."
                }

            }

        }

    }

}

