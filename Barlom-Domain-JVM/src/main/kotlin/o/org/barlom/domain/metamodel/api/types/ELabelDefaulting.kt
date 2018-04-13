//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.types

/**
 * Enumeration of possible values for whether an attribute is the default label for a vertex type.
 */
enum class ELabelDefaulting {

    /** An attribute is not the default label. */
    NOT_DEFAULT_LABEL,

    /** An element is concrete. */
    DEFAULT_LABEL;

    /**
     * Converts this enum value to a boolean equivalent.
     * @return true if this is the default label.
     */
    fun isDefaultLabel(): Boolean {
        return this == DEFAULT_LABEL
    }

    companion object {

        /**
         * Determines the label defaulting corresponding to a boolean value for is-default-label.
         * @param isDefaultLabel whether the item is the default label.
         * @return the corresponding enum value.
         */
        fun fromBoolean(isDefaultLabel: Boolean): ELabelDefaulting {
            if (isDefaultLabel) {
                return DEFAULT_LABEL
            }
            return NOT_DEFAULT_LABEL
        }

    }

}
