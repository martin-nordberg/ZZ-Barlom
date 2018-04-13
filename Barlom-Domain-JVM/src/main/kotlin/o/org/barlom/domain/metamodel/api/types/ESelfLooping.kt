//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.types

/**
 * Enumeration of possible constraints for self loops in an edge type.
 */
enum class ESelfLooping {

    /** Self loops are unconstrained by an edge type. */
    UNCONSTRAINED,

    /** Self loops are allowed by an edge type. */
    SELF_LOOPS_ALLOWED,

    /** Self loops are disallowed by an edge type. */
    SELF_LOOPS_NOT_ALLOWED;

    /**
     * Converts this enum value to a boolean equivalent.
     * @return true if this item allows self looping.
     */
    fun isSelfLoopAllowed(): Boolean? {

        return when (this) {
            UNCONSTRAINED          -> null
            SELF_LOOPS_ALLOWED     -> true
            SELF_LOOPS_NOT_ALLOWED -> false
        }

    }

    companion object {

        val DEFAULT = SELF_LOOPS_NOT_ALLOWED

        /**
         * Determines the self looping corresponding to a boolean value for is-self-loop-allowed.
         * @param isSelfLoopAllowed whether the item is self looping.
         * @return the corresponding enum value.
         */
        fun fromBoolean(isSelfLoopAllowed: Boolean?): ESelfLooping {

            if (isSelfLoopAllowed == null) {
                return UNCONSTRAINED
            }

            if (isSelfLoopAllowed) {
                return SELF_LOOPS_ALLOWED
            }

            return SELF_LOOPS_NOT_ALLOWED

        }

    }

}
