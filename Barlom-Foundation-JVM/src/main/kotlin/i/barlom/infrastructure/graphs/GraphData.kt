//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.IConcept
import o.barlom.infrastructure.graphs.IConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

data class GraphData(

    val concepts: MutableMap<Uuid, IConcept<*>> = mutableMapOf(),

    val connectionsFrom: MutableMap<Uuid, MutableSet<IConnection<*>>> = mutableMapOf(),

    val connectionsTo: MutableMap<Uuid, MutableSet<IConnection<*>>> = mutableMapOf()

)

//---------------------------------------------------------------------------------------------------------------------

