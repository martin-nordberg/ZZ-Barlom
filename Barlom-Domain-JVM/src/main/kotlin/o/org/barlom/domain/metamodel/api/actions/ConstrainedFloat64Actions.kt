//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.actions

import o.org.barlom.domain.metamodel.api.model.Model
import o.org.barlom.domain.metamodel.api.vertices.ConstrainedFloat64


class ConstrainedFloat64Actions {

    companion object {

        /**
         * Changes the default value of the given [constrainedFloat64].
         */
        fun changeDefaultValue(constrainedFloat64: ConstrainedFloat64, defaultValue: Double?): ModelAction {

            return { _: Model ->

                constrainedFloat64.defaultValue = defaultValue

                val path = constrainedFloat64.path

                "Changed constrained float64 $path default value to \"$defaultValue\"."
            }

        }

        /**
         * Changes the maximum value of the given [constrainedFloat64].
         */
        fun changeMaxValue(constrainedFloat64: ConstrainedFloat64, maxValue: Double?): ModelAction {

            return { _: Model ->

                constrainedFloat64.maxValue = maxValue

                val path = constrainedFloat64.path

                "Changed constrained float64 $path maximum value to $maxValue."
            }

        }

        /**
         * Changes the minimum value of the given [constrainedFloat64].
         */
        fun changeMinValue(constrainedFloat64: ConstrainedFloat64, minValue: Double?): ModelAction {

            return { _: Model ->

                constrainedFloat64.minValue = minValue

                val path = constrainedFloat64.path

                "Changed constrained float64 $path minimum value to $minValue."
            }

        }

    }

}

