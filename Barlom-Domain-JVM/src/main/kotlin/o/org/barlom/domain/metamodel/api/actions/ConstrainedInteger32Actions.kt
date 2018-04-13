//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.actions

import o.org.barlom.domain.metamodel.api.model.Model
import o.org.barlom.domain.metamodel.api.vertices.ConstrainedInteger32


class ConstrainedInteger32Actions {

    companion object {

        /**
         * Changes the default value of the given [constrainedInteger32].
         */
        fun changeDefaultValue(constrainedInteger32: ConstrainedInteger32, defaultValue: Int?): ModelAction {

            return { _: Model ->

                constrainedInteger32.defaultValue = defaultValue

                val path = constrainedInteger32.path

                "Changed constrained integer32 $path default value to \"$defaultValue\"."
            }

        }

        /**
         * Changes the maximum value of the given [constrainedInteger32].
         */
        fun changeMaxValue(constrainedInteger32: ConstrainedInteger32, maxValue: Int?): ModelAction {

            return { _: Model ->

                constrainedInteger32.maxValue = maxValue

                val path = constrainedInteger32.path

                "Changed constrained integer32 $path maximum value to $maxValue."
            }

        }

        /**
         * Changes the minimum value of the given [constrainedInteger32].
         */
        fun changeMinValue(constrainedInteger32: ConstrainedInteger32, minValue: Int?): ModelAction {

            return { _: Model ->

                constrainedInteger32.minValue = minValue

                val path = constrainedInteger32.path

                "Changed constrained integer32 $path minimum value to $minValue."
            }

        }

    }

}

