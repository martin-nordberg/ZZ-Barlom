//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.types

/**
 * Enumeration of possible values for whether something is abstract.
 */
enum class EAbstractness {

    /** An element is abstract. */
    ABSTRACT,

    /** An element is concrete. */
    CONCRETE;

    /**
     * Converts this enum value to a boolean equivalent.
     * @return true if this is abstract.
     */
    fun isAbstract(): Boolean {
        return this == ABSTRACT
    }

    /**
     * Converts this enum value to an inverse boolean equivalent.
     * @return true if this is abstract.
     */
    fun isConcrete(): Boolean {
        return this == CONCRETE
    }

    companion object {

        /**
         * Determines the abstractness corresponding to a boolean value for is-abstract.
         * @param isAbstract whether the item is abstract.
         * @return the corresponding enum value.
         */
        fun fromBoolean(isAbstract: Boolean): EAbstractness {
            if (isAbstract) {
                return ABSTRACT
            }
            return CONCRETE
        }

    }

}
