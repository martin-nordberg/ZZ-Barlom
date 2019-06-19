//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

fun <
    FromConcept : IConcept<FromConcept>,
    Connection : IDirectedConnection<Connection, FromConcept, ToConcept>,
    ToConcept : IConcept<ToConcept>
> IGraph.findConceptConnectedFrom(
    conceptId: Id<FromConcept>,
    connectionTypeName: String,
    connectionPredicate: (Connection) -> Boolean = { true }
): ToConcept? {

    for (connection in connectionsFrom<FromConcept,Connection>(conceptId, connectionTypeName)) {
        if (connectionPredicate(connection)) {
            return concept(connection.toConceptId) as ToConcept
        }
    }

    return null

}

//---------------------------------------------------------------------------------------------------------------------

fun <
    FromConcept : IConcept<FromConcept>,
    Connection : IDirectedConnection<Connection, FromConcept, ToConcept>,
    ToConcept : IConcept<ToConcept>
> IGraph.findConceptConnectedTo(
    conceptId: Id<ToConcept>,
    connectionTypeName: String,
    connectionPredicate: (Connection) -> Boolean = { true }
): FromConcept? {

    for (connection in connectionsTo<ToConcept,Connection>(conceptId, connectionTypeName)) {
        if (connectionPredicate(connection)) {
            return concept(connection.fromConceptId) as FromConcept
        }
    }

    return null

}

//---------------------------------------------------------------------------------------------------------------------

fun <
    FromConcept : IConcept<FromConcept>,
    Connection : IDirectedConnection<Connection, FromConcept, ToConcept>,
    ToConcept : IConcept<ToConcept>
> IGraph.findConceptsConnectedFrom(
    conceptId: Id<FromConcept>,
    connectionTypeName: String,
    connectionPredicate: (Connection) -> Boolean = { true }
): List<ToConcept> {

    val result: MutableList<ToConcept> = mutableListOf()

    for (connection in connectionsFrom<FromConcept,Connection>(conceptId,connectionTypeName)) {
        if (connectionPredicate(connection)) {
            result.add(concept(connection.toConceptId) as ToConcept)
        }
    }

    return result

}

//---------------------------------------------------------------------------------------------------------------------

fun <
    FromConcept : IConcept<FromConcept>,
    Connection : IDirectedConnection<Connection, FromConcept, ToConcept>,
    ToConcept : IConcept<ToConcept>
> IGraph.findConceptsConnectedTo(
    conceptId: Id<ToConcept>,
    connectionTypeName: String,
    connectionPredicate: (Connection) -> Boolean = { true }
): List<FromConcept> {

    val result: MutableList<FromConcept> = mutableListOf()

    for (connection in connectionsTo<ToConcept,Connection>(conceptId,connectionTypeName)) {
        if (connectionPredicate(connection)) {
            result.add(concept(connection.fromConceptId) as FromConcept)
        }
    }

    return result

}

//---------------------------------------------------------------------------------------------------------------------

fun <
    Concept : IConcept<Concept>,
    Connection : IDirectedConnection<Connection, Concept, Concept>
> IGraph.findTransitiveConceptsConnectedFrom(
    conceptId: Id<Concept>,
    connectionTypeName: String,
    connectionPredicate: (Connection) -> Boolean = { true }
): Set<Concept> {

    val result: MutableSet<Concept> = mutableSetOf()

    var idsToCheck: MutableSet<Id<Concept>> = mutableSetOf(conceptId)

    while ( idsToCheck.isNotEmpty() ) {

        val moreIdsToCheck: MutableSet<Id<Concept>> = mutableSetOf()

        for (idToCheck in idsToCheck) {
            for (connection in connectionsFrom<Concept,Connection>(idToCheck, connectionTypeName)) {
                if (connectionPredicate(connection)) {

                    val c = concept(connection.toConceptId) as Concept

                    if (!result.contains(c)) {
                        result.add(c)
                        moreIdsToCheck.add(c.id)
                    }

                }
            }
        }

        idsToCheck = moreIdsToCheck

    }

    return result

}

//---------------------------------------------------------------------------------------------------------------------

fun <
    Concept : IConcept<Concept>,
    Connection : IDirectedConnection<Connection, Concept, Concept>
> IGraph.findTransitiveConceptsConnectedTo(
    conceptId: Id<Concept>,
    connectionTypeName: String,
    connectionPredicate: (Connection) -> Boolean = { true }
): Set<Concept> {

    val result: MutableSet<Concept> = mutableSetOf()

    var idsToCheck: MutableSet<Id<Concept>> = mutableSetOf(conceptId)

    while ( idsToCheck.isNotEmpty() ) {

        val moreIdsToCheck: MutableSet<Id<Concept>> = mutableSetOf()

        for (idToCheck in idsToCheck) {
            for (connection in connectionsTo<Concept,Connection>(idToCheck, connectionTypeName)) {
                if (connectionPredicate(connection)) {

                    val c = concept(connection.fromConceptId) as Concept

                    if (!result.contains(c)) {
                        result.add(c)
                        moreIdsToCheck.add(c.id)
                    }

                }
            }
        }

        idsToCheck = moreIdsToCheck

    }

    return result

}

//---------------------------------------------------------------------------------------------------------------------

fun <
    FromConcept : IConcept<FromConcept>,
    Connection : IDirectedConnection<Connection, FromConcept, ToConcept>,
    ToConcept : IConcept<ToConcept>
> IGraph.hasConceptConnectedFrom(
    conceptId: Id<FromConcept>,
    connectionTypeName: String,
    connectionPredicate: (Connection) -> Boolean = { true }
): Boolean {

    for (connection in connectionsFrom<FromConcept,Connection>(conceptId, connectionTypeName)) {
        if (connectionPredicate(connection)) {
            return true
        }
    }

    return false

}

//---------------------------------------------------------------------------------------------------------------------

fun <
    FromConcept : IConcept<FromConcept>,
    Connection : IDirectedConnection<Connection, FromConcept, ToConcept>,
    ToConcept : IConcept<ToConcept>
> IGraph.hasConceptConnectedTo(
    conceptId: Id<ToConcept>,
    connectionTypeName: String,
    connectionPredicate: (Connection) -> Boolean = { true }
): Boolean {

    for (connection in connectionsTo<ToConcept,Connection>(conceptId, connectionTypeName)) {
        if (connectionPredicate(connection)) {
            return true
        }
    }

    return false

}

//---------------------------------------------------------------------------------------------------------------------

fun <
    Concept : IConcept<Concept>,
    Connection : IDirectedConnection<Connection, Concept, Concept>
> IGraph.hasTransitiveConceptConnectedFrom(
    conceptId: Id<Concept>,
    connectionTypeName: String,
    connectionPredicate: (Connection) -> Boolean = { true }
): Boolean {

    val checked: MutableSet<Concept> = mutableSetOf()

    var idsToCheck: MutableSet<Id<Concept>> = mutableSetOf(conceptId)

    while (idsToCheck.isNotEmpty()) {

        val moreIdsToCheck: MutableSet<Id<Concept>> = mutableSetOf()

        for (idToCheck in idsToCheck) {

            for (connection in connectionsFrom<Concept,Connection>(idToCheck, connectionTypeName)) {

                if (connectionPredicate(connection)) {
                    return true
                }

                val c = concept(connection.toConceptId) as Concept

                if (!checked.contains(c)) {
                    checked.add(c)
                    moreIdsToCheck.add(c.id)
                }

            }

        }

        idsToCheck = moreIdsToCheck

    }

    return false

}

//---------------------------------------------------------------------------------------------------------------------

