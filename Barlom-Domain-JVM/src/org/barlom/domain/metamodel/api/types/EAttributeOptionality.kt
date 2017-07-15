//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.types

/**
 * Enumeration of possible values for whether an attribute is required or optional.
 */
enum class EAttributeOptionality {

    /** An attribute is optional. */
    OPTIONAL,

    /** An attribute is required. */
    REQUIRED;

    /**
     * Converts this enum value to a boolean equivalent.
     * @return true if this is required.
     */
    fun isRequired(): Boolean {
        return this == REQUIRED
    }

    companion object {

        /**
         * Determines the optionality corresponding to a boolean value for is-required.
         * @param isRequired whether the item is required.
         * @return the corresponding enum value.
         */
        fun fromBoolean(isRequired: Boolean): EAttributeOptionality {
            if (isRequired) {
                return REQUIRED
            }
            return OPTIONAL
        }

    }


}
