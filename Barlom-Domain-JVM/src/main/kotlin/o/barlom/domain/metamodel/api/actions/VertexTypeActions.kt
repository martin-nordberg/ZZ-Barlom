//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.actions

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.domain.metamodel.api.types.EAbstractness
import o.barlom.domain.metamodel.api.vertices.VertexType
import x.barlom.infrastructure.uuids.Uuid


class VertexTypeActions {

    companion object {

        /**
         * Adds a new child attribute type with given [id] to the given [parentVertexType].
         */
        fun addAttributeType(parentVertexType: VertexType, id: Uuid): ModelAction {

            return { model: Model ->

                model.makeVertexAttributeType(id) {
                    model.makeVertexAttributeTypeContainment(parentVertexType, this)
                }

                "Added an attribute type to ${parentVertexType.path}"

            }

        }

        /**
         * Changes the abstractness of the given [vertexType].
         */
        fun changeAbstractness(vertexType: VertexType, abstractness: EAbstractness): ModelAction {

            return { _: Model ->

                vertexType.abstractness = abstractness

                val path = vertexType.path
                val a = if (abstractness.isAbstract()) "abstract" else "concrete"

                "Made vertex type $path $a."

            }

        }

        /**
         * Changes the super type of a vertex type.
         */
        fun changeSuperType(vertexType: VertexType, superTypePath: String): ModelAction {

            return { model: Model ->

                val superType: VertexType? = model.rootPackage.findVertexTypeByPath(superTypePath)

                if (superType != null) {
                    while (!vertexType.superTypeVertexTypeInheritances.isEmpty()) {
                        vertexType.superTypeVertexTypeInheritances.get(0).remove()
                    }
                    model.makeVertexTypeInheritance(superType, vertexType)
                }

                val path = vertexType.path

                "Set vertex type $path super type to $superTypePath."

            }

        }

    }

}

