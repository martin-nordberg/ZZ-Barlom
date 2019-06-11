//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A package containing concept types and connection types.
 */
data class Package(
    override val uuid: Uuid,
    val isRoot: Boolean = false,
    override val name: String = if (isRoot) "Schema" else "newpackage",
    override val description: String = if (isRoot) "Root package." else ""
) : AbstractPackagedConcept<Package>() {

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

}

//---------------------------------------------------------------------------------------------------------------------

