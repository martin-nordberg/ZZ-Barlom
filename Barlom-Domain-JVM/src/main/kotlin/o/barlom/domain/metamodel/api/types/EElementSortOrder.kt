//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.types

/**
 * Enumeration of possible sort orders for element queries.
 */
enum class EElementSortOrder {

    /** Sort by name. */
    NAME,

    /** Sort a parent before its children. */
    PARENT_BEFORE_CHILDREN,

    /** Sort by full path. */
    PATH,

    /** Sort a super type before its sub-types. */
    SUPER_TYPE_BEFORE_SUB_TYPES

}
