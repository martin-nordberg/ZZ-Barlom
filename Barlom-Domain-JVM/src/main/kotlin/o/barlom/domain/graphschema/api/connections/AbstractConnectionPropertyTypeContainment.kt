//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.AbstractConnectionType
import o.barlom.domain.graphschema.api.concepts.ConnectionPropertyType

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a connection representing containment of a connection type within a package.
 */
abstract class AbstractConnectionPropertyTypeContainment<
    Connection : AbstractConnectionPropertyTypeContainment<Connection, ParentConnectionType>,
    ParentConnectionType : AbstractConnectionType<ParentConnectionType>
> : AbstractContainment<Connection, ParentConnectionType, ConnectionPropertyType>() {

    val childConnectionPropertyTypeId
        get() = toConceptId

    val parentConnectionTypeId
        get() = fromConceptId

}

//---------------------------------------------------------------------------------------------------------------------

