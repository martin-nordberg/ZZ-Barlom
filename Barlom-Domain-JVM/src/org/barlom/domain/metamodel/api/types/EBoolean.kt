//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.types

/**
 * Enumeration of possible values for whether something is true or false. Useful for converting to functions that work
 * on enums (e.g. Yes/No radio buttons).
 */
enum class EBoolean {

    /** A value is true. */
    FALSE,

    /** A value is false. */
    TRUE;

    /**
     * Tests whether this enum value is FALSE.
     * @return true if this is FALSE.
     */
    fun isFalse(): Boolean {
        return this == FALSE
    }

    /**
     * Tests whether this enum value is TRUE.
     * @return true if this is TRUE.
     */
    fun isTrue(): Boolean {
        return this == TRUE
    }

    /**
     * Converts this enum value to a boolean equivalent.
     * @return true, false, or null
     */
    fun toBoolean() : Boolean {
        return when ( this ) {
            FALSE -> false
            TRUE -> true
        }
    }

    companion object {

        /**
         * Determines the abstractness corresponding to a boolean value for is-abstract.
         * @param isAbstract whether the item is abstract.
         * @return the corresponding enum value.
         */
        fun fromBoolean(value: Boolean): EBoolean {
            return if (value) TRUE else FALSE
        }

    }

}
