//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

internal data class GraphData(

    val concepts: ConceptMap = ConceptMap(),

    val connections: ConnectionMap = ConnectionMap(),

    val connectionsFrom: ConceptConnectionMap = ConceptConnectionMap(),

    val connectionsTo: ConceptConnectionMap = ConceptConnectionMap()

)

//---------------------------------------------------------------------------------------------------------------------

