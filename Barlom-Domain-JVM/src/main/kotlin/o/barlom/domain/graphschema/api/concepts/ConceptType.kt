//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.domain.graphschema.api.types.EAbstractness
import o.barlom.infrastructure.graphs.Id

//---------------------------------------------------------------------------------------------------------------------

/**
 * The type of a concept (vertex or node).
 */
data class ConceptType(
    override val id: Id<ConceptType>,
    val isRoot: Boolean = false,
    override val name: String = if (isRoot) "RootConceptType" else "NewConceptType",
    override val description: String = if (isRoot) "Root concept type." else "",
    val abstractness: EAbstractness = EAbstractness.fromBoolean(isRoot)
) : AbstractPackagedConcept<ConceptType>() {

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "abstractness" -> abstractness
            "isRoot"       -> isRoot
            else           -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "abstractness" -> true
            "isRoot"       -> true
            else           -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("abstractness").plus("isRoot")

}

//---------------------------------------------------------------------------------------------------------------------

