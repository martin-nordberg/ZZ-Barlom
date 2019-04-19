//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.actions

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.domain.metamodel.api.vertices.ConstrainedString


class ConstrainedStringActions {

    companion object {

        /**
         * Changes the default value of the given [constrainedString].
         */
        fun changeDefaultValue(constrainedString: ConstrainedString, defaultValue: String): ModelAction {

            return { _: Model ->

                constrainedString.defaultValue = defaultValue

                val path = constrainedString.path

                "Changed constrained string $path default value to \"$defaultValue\"."
            }

        }

        /**
         * Changes the maximum length of the given [constrainedString].
         */
        fun changeMaxLength(constrainedString: ConstrainedString, maxLength: Int?): ModelAction {

            return { _: Model ->

                constrainedString.maxLength = maxLength

                val path = constrainedString.path

                "Changed constrained string $path maximum length to $maxLength."
            }

        }

        /**
         * Changes the minimum length of the given [constrainedString].
         */
        fun changeMinLength(constrainedString: ConstrainedString, minLength: Int?): ModelAction {

            return { _: Model ->

                constrainedString.minLength = minLength

                val path = constrainedString.path

                "Changed constrained string $path minimum length to $minLength."
            }

        }

        /**
         * Changes the regex pattern of the given [constrainedString].
         */
        fun changeRegexPattern(constrainedString: ConstrainedString, regexPatternStr: String): ModelAction {

            return { _: Model ->

                constrainedString.regexPattern = Regex(regexPatternStr)

                val path = constrainedString.path

                "Changed constrained string $path regex pattern to \"$regexPatternStr\"."
            }

        }

    }

}

