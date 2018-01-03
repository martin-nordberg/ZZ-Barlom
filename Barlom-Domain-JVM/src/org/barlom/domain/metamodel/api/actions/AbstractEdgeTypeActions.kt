//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.domain.metamodel.api.vertices.AbstractEdgeType


class AbstractEdgeTypeActions {

    companion object {

        /**
         * Adds a new child attribute type to the given [parentEdgeType].
         */
        fun addAttributeType(parentEdgeType: AbstractEdgeType): ModelAction {

            return { model: Model ->

                model.makeEdgeAttributeType {
                    model.makeEdgeAttributeTypeContainment(parentEdgeType, this)
                }

                "Add an attribute type to ${parentEdgeType.path}"

            }

        }

        /**
         * Changes the abstractness of the given [edgeType].
         */
        fun changeAbstractness(edgeType: AbstractEdgeType, abstractness: EAbstractness): ModelAction {

            return { _: Model ->

                edgeType.abstractness = abstractness

                val path = edgeType.path
                val a = if (abstractness.isAbstract()) "abstract" else "concrete"

                "Make edge type $path $a."
            }

        }

        /**
         * Changes the cyclicity of the given [edgeType].
         */
        fun changeCyclicity(edgeType: AbstractEdgeType, cyclicity: ECyclicity): ModelAction {

            return { _: Model ->

                edgeType.cyclicity = cyclicity

                val path = edgeType.path
                val c = when (cyclicity) {
                    ECyclicity.ACYCLIC            -> "acyclic"
                    ECyclicity.POTENTIALLY_CYCLIC -> "potentially cyclic"
                    ECyclicity.UNCONSTRAINED      -> "unconstrained in cyclicity"
                }

                "Make edge type $path $c."

            }

        }

        /**
         * Changes the multi-edgedness of the given [edgeType].
         */
        fun changeMultiEdgedness(edgeType: AbstractEdgeType, multiEdgedness: EMultiEdgedness): ModelAction {

            return { _: Model ->

                edgeType.multiEdgedness = multiEdgedness

                val path = edgeType.path
                val m = when (multiEdgedness) {
                    EMultiEdgedness.MULTI_EDGES_ALLOWED     -> "potentially multi-edged"
                    EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED -> "single-edged"
                    EMultiEdgedness.UNCONSTRAINED           -> "unconstrained in multi-edgedness"
                }

                "Make edge type $path $m."

            }

        }

        /**
         * Changes the self-looping of the given [edgeType].
         */
        fun changeSelfLooping(edgeType: AbstractEdgeType, selfLooping: ESelfLooping): ModelAction {

            return { _: Model ->

                edgeType.selfLooping = selfLooping

                val path = edgeType.path
                val s = when (selfLooping) {
                    ESelfLooping.SELF_LOOPS_ALLOWED     -> "potentially self-looping"
                    ESelfLooping.SELF_LOOPS_NOT_ALLOWED -> "non-self-looping"
                    ESelfLooping.UNCONSTRAINED          -> "unconstrained in self-looping"
                }

                "Make edge type $path $s."

            }

        }

    }

}

