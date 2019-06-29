//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.connections.Dependency
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

interface IModuleDependencyQueries {

    /** The graph at the core of this model. */
    val graph: IGraph

    ////

    /**
     * Finds the immediate consumer packages of a package with ID [supplierModuleId].
     */
    fun Module.consumers(): List<Module> =
        graph.findConceptsConnectedTo(this, Dependency.MODULE_DEPENDENCY_TYPE)

    /**
     * Tests whether the package with ID [consumerModuleId] has a direct supplier package with ID [supplierModuleId].
     */
    fun Module.hasSupplier(supplierModule: Module): Boolean =
        graph.hasConceptConnectedFrom(this, Dependency.MODULE_DEPENDENCY_TYPE) { connection ->
            connection.supplierId == supplierModule
        }

    /**
     * Tests whether the package with ID [supplierModuleId] has a direct consumer package with ID [consumerModuleId].
     */
    fun Module.hasConsumer(consumerModule: Module): Boolean =
        graph.hasConceptConnectedTo(this, Dependency.MODULE_DEPENDENCY_TYPE) { connection ->
            connection.consumerId == consumerModule
        }

    /**
     * Tests whether a package with ID [consumerModuleId] has a given direct or indirect supplier package
     * with ID [supplierModuleId].
     */
    fun Module.hasTransitiveSupplier(supplierModule: Module): Boolean =
        graph.hasTransitiveConceptConnectedFrom(this, Dependency.MODULE_DEPENDENCY_TYPE) { connection ->
            connection.supplierId == supplierModule
        }

    /**
     * Tests whether a package with ID [supplierModuleId] has a given direct or indirect consumer package
     * with ID [consumerModuleId].
     */
    fun Module.hasTransitiveConsumer(consumerModule: Module): Boolean =
        graph.hasTransitiveConceptConnectedTo(this, Dependency.MODULE_DEPENDENCY_TYPE) { connection ->
            connection.consumerId == consumerModule
        }

    /**
     * Finds the immediate supplier packages of a package with ID [consumerModuleId].
     */
    fun Module.suppliers(): List<Module> =
        graph.findConceptsConnectedFrom(this, Dependency.MODULE_DEPENDENCY_TYPE)

    /**
     * Finds the direct and indirect consumer packages of the package with ID [moduleId].
     */
    fun Module.transitiveConsumers(): Set<Module> =
        graph.findTransitiveConceptsConnectedTo(this, Dependency.MODULE_DEPENDENCY_TYPE)

    /**
     * Finds the direct and indirect supplier packages of the package with ID [moduleId].
     */
    fun Module.transitiveSuppliers(): Set<Module> =
        graph.findTransitiveConceptsConnectedFrom(this, Dependency.MODULE_DEPENDENCY_TYPE)

}

//---------------------------------------------------------------------------------------------------------------------

