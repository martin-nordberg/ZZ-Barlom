//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.infrastructure.graphs.Id

//---------------------------------------------------------------------------------------------------------------------

/**
 * A package containing concept types and connection types.
 */
data class Package(
    override val id: Id<Package>,
    val isRoot: Boolean,
    override var name: String = if (isRoot) "Schema" else "newpackage",
    override var description: String = if (isRoot) "Root package." else ""
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

