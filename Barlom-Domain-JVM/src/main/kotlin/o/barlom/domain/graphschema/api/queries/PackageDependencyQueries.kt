//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.connections.PackageDependency
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the immediate consumer packages of a package with ID [supplierPackageId].
 */
fun Model.consumerPackages(supplierPackageId: Id<Package>): List<Package> =
    graph.findConceptsConnectedTo(supplierPackageId, PackageDependency.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the package with ID [consumerPackageId] has a direct supplier package with ID [supplierPackageId].
 */
fun Model.hasSupplier(consumerPackageId: Id<Package>, supplierPackageId: Id<Package>): Boolean =
    graph.hasConceptConnectedFrom(consumerPackageId, PackageDependency.TYPE) { connection ->
        connection.supplierPackageId == supplierPackageId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the package with ID [supplierPackageId] has a direct consumer package with ID [consumerPackageId].
 */
fun Model.hasConsumer(supplierPackageId: Id<Package>, consumerPackageId: Id<Package>): Boolean =
    hasSupplier(consumerPackageId, supplierPackageId)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether a package with ID [consumerPackageId] has a given direct or indirect supplier package
 * with ID [supplierPackageId].
 */
fun Model.hasTransitiveSupplier(consumerPackageId: Id<Package>, supplierPackageId: Id<Package>): Boolean =
    graph.hasTransitiveConceptConnectedFrom(consumerPackageId, PackageDependency.TYPE) { connection ->
        connection.supplierPackageId == supplierPackageId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether a package with ID [supplierPackageId] has a given direct or indirect consumer package
 * with ID [consumerPackageId].
 */
fun Model.hasTransitiveConsumer(supplierPackageId: Id<Package>, consumerPackageId: Id<Package>): Boolean =
    hasTransitiveSupplier(consumerPackageId, supplierPackageId)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the immediate supplier packages of a package with ID [consumerPackageId].
 */
fun Model.supplierPackages(consumerPackageId: Id<Package>): List<Package> =
    graph.findConceptsConnectedFrom(consumerPackageId, PackageDependency.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the direct and indirect consumer packages of the package with ID [packageId].
 */
fun Model.transitiveConsumerPackages(packageId: Package): Set<Package> =
    graph.findTransitiveConceptsConnectedTo(packageId, PackageDependency.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the direct and indirect supplier packages of the package with ID [packageId].
 */
fun Model.transitiveSupplierPackages(packageId: Package): Set<Package> =
    graph.findTransitiveConceptsConnectedFrom(packageId, PackageDependency.TYPE)

//---------------------------------------------------------------------------------------------------------------------

