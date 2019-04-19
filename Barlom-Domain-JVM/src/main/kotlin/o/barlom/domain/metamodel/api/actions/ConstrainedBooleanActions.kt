//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.actions

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.domain.metamodel.api.vertices.ConstrainedBoolean


class ConstrainedBooleanActions {

    companion object {

        /**
         * Changes the default value of the given [constrainedBoolean].
         */
        fun changeDefaultValue(constrainedBoolean: ConstrainedBoolean, defaultValue: Boolean?): ModelAction {

            return { _: Model ->

                constrainedBoolean.defaultValue = defaultValue

                val path = constrainedBoolean.path

                "Changed constrained boolean $path default value to $defaultValue."
            }

        }

    }

}

