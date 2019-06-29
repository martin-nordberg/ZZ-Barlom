//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.AbstractPackagedConcept
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.connections.Containment
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

interface IModuleContainmentQueries {

    /** The graph at the core of this model. */
    val graph: IGraph

    ////

    /**
     * Finds the immediate child packages of a package with ID [parentModuleId].
     */
    fun Module.childModules(): List<Module> =
        graph.findConceptsConnectedFrom(this, Containment.MODULE_CONTAINMENT_TYPE)

    /**
     * Tests whether the package with ID [parentModuleId] has a child package with ID [childModuleId].
     */
    fun Module.hasChild(childModule: Module): Boolean =
        graph.hasConceptConnectedFrom(this, Containment.MODULE_CONTAINMENT_TYPE) { connection ->
            connection.childId == childModule
        }

    /**
     * Tests whether the package with ID [childModuleId] has parent with ID [parentModuleId].
     */
    fun Module.hasParent(parentModule: Module): Boolean =
        graph.hasConceptConnectedTo(this, Containment.MODULE_CONTAINMENT_TYPE) { connection ->
            connection.parentId == parentModule
        }

    /**
     * Tests whether a package with ID [parentModuleId] has a given child or grandchild package with ID [childModuleId].
     */
    fun Module.hasTransitiveChild(childModule: Module): Boolean =
        graph.hasTransitiveConceptConnectedFrom(this, Containment.MODULE_CONTAINMENT_TYPE) { connection ->
            connection.childId == childModule
        }

    /**
     * Tests whether a package with ID [childModuleId] has a given parent or grandparent package with ID [parentModuleId].
     */
    fun Module.hasTransitiveParent(parentModule: Module): Boolean =
        graph.hasTransitiveConceptConnectedTo(this, Containment.MODULE_CONTAINMENT_TYPE) { connection ->
            connection.parentId == parentModule
        }

    /**
     * Finds the parent package of the package with ID [childModuleId].
     */
    fun Module.parentModule(): Module? =
        graph.findConceptConnectedTo(this, Containment.MODULE_CONTAINMENT_TYPE)

    /**
     * Computes the dot-delimited path to a packaged [concept].
     */
    fun <Concept : AbstractPackagedConcept<Concept>> Concept.path(): String {

        fun buildPath(pkg: Module, subPath: String): String {

            if (pkg.isRoot) {
                return subPath
            }

            with(graph) {

                for (connection in connectionsTo(pkg.id, Containment.MODULE_CONTAINMENT_TYPE)) {
                    return buildPath(concept(connection.parentId) as Module, pkg.name + "." + subPath)
                }

                return "???.$subPath"

            }

        }

        for (connection in graph.connections(this.id)) {
            if (connection is Containment<*, *> && connection.childId == this.id) {
                val parentId: Id<Module> = connection.parentId as Id<Module>
                return buildPath(graph.concept(parentId) as Module, this.name)
            }
        }

        return this.name
    }

    /**
     * Finds the child and grandchild packages of the package with ID [moduleId].
     */
    fun Module.transitiveChildModules(): Set<Module> =
        graph.findTransitiveConceptsConnectedFrom(this, Containment.MODULE_CONTAINMENT_TYPE)


}

//---------------------------------------------------------------------------------------------------------------------

