//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.types;

/**
 * Enumeration of possible constraints for multi edges in an edge type.
 */
enum class EMultiEdgedness {

    /** An edge type does not constrain the multi-edgedness of its edges. */
    UNCONSTRAINED,

    /** An edge type allows multiple edges between two given vertexes. */
    MULTI_EDGES_ALLOWED,

    /** An edge type disallows multiple edges between two given vertexes. */
    MULTI_EDGES_NOT_ALLOWED;

    /**
     * Converts this enum value to a boolean equivalent.
     * @return true if this allows multi edges.
     */
    fun isMultiEdgeAllowed(): Boolean? {

        return when (this) {
            UNCONSTRAINED           -> null
            MULTI_EDGES_ALLOWED     -> true
            MULTI_EDGES_NOT_ALLOWED -> false
        }

    }

    companion object {

        val DEFAULT = MULTI_EDGES_NOT_ALLOWED

        /**
         * Determines the abstractness corresponding to a boolean value for is-multi-edge-allowed.
         * @param isMultiEdgeAllowed whether the item allows multi edges.
         * @return the corresponding enum value.
         */
        fun fromBoolean(isMultiEdgeAllowed: Boolean?): EMultiEdgedness {

            if (isMultiEdgeAllowed == null) {
                return UNCONSTRAINED
            }

            if (isMultiEdgeAllowed) {
                return MULTI_EDGES_ALLOWED
            }

            return MULTI_EDGES_NOT_ALLOWED

        }

    }

}
