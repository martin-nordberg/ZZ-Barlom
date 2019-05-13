//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Wrapper class for a unique ID. Includes the type of entity identified for added static type checking.
 * E.g. code will not compile if a connection ID is passed where a concept ID is expected.
 */
data class Id<T>(

    /** The encapsulated UUID. */
    val uuid: Uuid

)

//---------------------------------------------------------------------------------------------------------------------

