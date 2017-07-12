//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

/**
 * Enumeration of possible values for whether a dependency is direct or can also be indirect.
 */
enum class EDependencyDepth {

    /** The dependency is direct. */
    DIRECT,

    /** The dependency is direct or indirect. */
    TRANSITIVE;

    /**
     * Converts this enum value to a boolean equivalent.
     * @return true if this is transitive.
     */
    fun isTransitive(): Boolean {
        return this == TRANSITIVE
    }

    companion object {

        /**
         * Determines the dependency depth corresponding to a boolean value for is-transitive.
         * @param isAbstract whether the item is transitive.
         * @return the corresponding enum value.
         */
        fun fromBoolean(isTransitive: Boolean): EDependencyDepth {
            if (isTransitive) {
                return TRANSITIVE
            }
            return DIRECT
        }

    }

}
