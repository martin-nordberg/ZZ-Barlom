//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.ConstrainedBoolean


class ConstrainedBooleanActions {

    companion object {

        /**
         * Changes the default value of the given [constrainedBoolean].
         */
        fun changeDefaultValue(constrainedBoolean: ConstrainedBoolean, defaultValue: Boolean?): ModelAction {

            return { _: Model ->

                constrainedBoolean.defaultValue = defaultValue

                val path = constrainedBoolean.path

                "Change constrained boolean $path default value to $defaultValue."
            }

        }

    }

}

