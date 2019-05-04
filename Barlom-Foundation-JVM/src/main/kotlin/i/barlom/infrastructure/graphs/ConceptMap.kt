//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.graphs

import o.barlom.infrastructure.graphs.IConcept
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Defines a map where concepts are mapped by their own UUID.
 */
class ConceptMap {

    /** The ordinary inner map doing all the work. */
    private val conceptsByUuid = HashMap<Uuid, IConcept<*>>()

    ////

    /** The number of concepts mapped. */
    val size: Int
        get() = conceptsByUuid.size

    ////

    /**
     * @return whether the map has an entry for the given UUID [conceptUuid].
     */
    fun containsUuid(conceptUuid: Uuid): Boolean =
        conceptsByUuid.containsKey(conceptUuid)

    /**
     * @return the concept with given UUID [conceptUuid] or null if there is none.
     */
    operator fun get(conceptUuid: Uuid): IConcept<*>? =
        conceptsByUuid[conceptUuid]

    /**
     * @return true if there are no concepts in this map.
     */
    fun isEmpty() =
        conceptsByUuid.isEmpty()

    /**
     * @return true if there are no concepts in this map.
     */
    fun isNotEmpty() =
        !isEmpty()

    /**
     * Puts the given [concept] into this map, mapped by its own UUID. Replaces any concept with the same UUID.
     */
    fun put(concept: IConcept<*>) {
        conceptsByUuid[concept.id.uuid] = concept
    }

    /**
     * Copies concepts from another map [addedConcepts] into this one.
     */
    fun putAll(addedConcepts: ConceptMap) =
        conceptsByUuid.putAll(addedConcepts.conceptsByUuid)

    /**
     * Removes the concept with UUID [conceptUuid] from this map.
     * @return the removed concept or null if there is no concept with the given UUID.
     */
    fun remove(conceptUuid: Uuid) =
        conceptsByUuid.remove(conceptUuid)

    /**
     * Removes the concept with UUID [conceptUuid] from this map.
     * @return the removed concept or null if there is no concept with the given UUID.
     */
    fun removeAll(conceptUuids: Iterable<Uuid>) =
        conceptUuids.forEach { conceptUuid ->
            conceptsByUuid.remove(conceptUuid)
        }

}

//---------------------------------------------------------------------------------------------------------------------

