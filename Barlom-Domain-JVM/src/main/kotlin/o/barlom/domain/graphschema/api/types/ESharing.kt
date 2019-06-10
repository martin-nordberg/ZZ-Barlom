//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.types

/**
 * Enumeration of possible values for whether something shared outside its container.
 */
enum class ESharing {

    /** An element is available for use outside its container. */
    SHARED,

    /** An element is not available for use outside its container. */
    NOT_SHARED;

    /**
     * Converts this enum value to a boolean equivalent.
     * @return true if this is shared.
     */
    fun isShared(): Boolean {
        return this == SHARED
    }

    /**
     * Converts this enum value to an inverse boolean equivalent.
     * @return true if this is not shared.
     */
    fun isNotShared(): Boolean {
        return this == NOT_SHARED
    }

    companion object {

        /**
         * Determines the sharing corresponding to a boolean value for is-shared.
         * @param isShared whether the item is shared.
         * @return the corresponding enum value.
         */
        fun fromBoolean(isShared: Boolean): ESharing {
            if (isShared) {
                return SHARED
            }
            return NOT_SHARED
        }

    }

}
