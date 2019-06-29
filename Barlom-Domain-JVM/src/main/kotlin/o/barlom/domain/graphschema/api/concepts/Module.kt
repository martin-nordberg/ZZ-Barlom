//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.infrastructure.graphs.ConceptTypeId
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A package containing concept types and connection types.
 */
data class Module(
    override val uuid: Uuid,
    val isRoot: Boolean = false,
    override val name: String = if (isRoot) "Schema" else "newmodule",
    override val description: String = if (isRoot) "Root module." else ""
) : AbstractPackagedConcept<Module>() {

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "isRoot" -> isRoot
            else     -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "isRoot" -> true
            else     -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("isRoot")

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<Module>(
            "o.barlom.domain.graphschema.api.concepts.Module"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

