//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.types

/**
 * Enumeration of possible values for cyclic/acyclic.
 */
enum class ECyclicity {

    /** Cyclicity of an edge type is unconstrained by an edge type. */
    UNCONSTRAINED,

    /** Edges of a given edge type are constrained to be acyclic. */
    ACYCLIC,

    /** Edges of a given edge type are expected to be cyclic. */
    POTENTIALLY_CYCLIC;

    /**
     * Converts this enum value to a boolean equivalent.
     * @return true if this is acyclic.
     */
    fun isAcyclic(): Boolean? {

        return when (this) {
            UNCONSTRAINED      -> null
            ACYCLIC            -> true
            POTENTIALLY_CYCLIC -> false
        }

    }


    companion object {

        val DEFAULT = POTENTIALLY_CYCLIC

        /**
         * Determines the abstractness corresponding to a boolean value for is-acyclic.
         * @param isAbstract whether the item is acyclic.
         * @return the corresponding enum value.
         */
        fun fromBoolean(isAcyclic: Boolean?): ECyclicity {

            if (isAcyclic == null) {
                return UNCONSTRAINED
            }

            if (isAcyclic) {
                return ACYCLIC
            }

            return POTENTIALLY_CYCLIC

        }

    }

}
