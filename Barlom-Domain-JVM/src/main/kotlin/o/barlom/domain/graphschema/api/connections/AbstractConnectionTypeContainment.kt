//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.AbstractConnectionType

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a connection representing containment of a connection type within a package.
 */
abstract class AbstractConnectionTypeContainment<
    Connection : AbstractConnectionTypeContainment<Connection, ChildConnectionType>,
    ChildConnectionType : AbstractConnectionType<ChildConnectionType>
> : AbstractPackagedElementContainment<Connection, ChildConnectionType>() {

    val childConnectionTypeId
        get() = toConceptId

}

//---------------------------------------------------------------------------------------------------------------------

