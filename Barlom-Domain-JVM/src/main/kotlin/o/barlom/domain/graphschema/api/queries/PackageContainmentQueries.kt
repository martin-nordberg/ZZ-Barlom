//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.queries

import o.barlom.domain.graphschema.api.concepts.AbstractPackagedConcept
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.connections.AbstractPackagedElementContainment
import o.barlom.domain.graphschema.api.connections.PackageContainment
import o.barlom.domain.graphschema.api.model.Model
import o.barlom.infrastructure.graphs.*

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the immediate child packages of a package with ID [parentPackageId].
 */
fun Model.childPackages(parentPackageId: Id<Package>): List<Package> =
    graph.findConceptsConnectedFrom(parentPackageId, PackageContainment.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the package with ID [parentPackageId] has a child package with ID [childPackageId].
 */
fun Model.hasChild(parentPackageId: Id<Package>, childPackageId: Id<Package>): Boolean =
    graph.hasConceptConnectedFrom(parentPackageId, PackageContainment.TYPE) { connection ->
        connection.childElementId == childPackageId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the package with ID [childPackageId] has parent with ID [parentPackageId].
 */
fun Model.hasParent(childPackageId: Id<Package>, parentPackageId: Id<Package>): Boolean =
    graph.hasConceptConnectedTo(childPackageId, PackageContainment.TYPE) { connection ->
        connection.parentPackageId == parentPackageId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether a package with ID [parentPackageId] has a given child or grandchild package with ID [childPackageId].
 */
fun Model.hasTransitiveChild(parentPackageId: Id<Package>, childPackageId: Id<Package>): Boolean =
    graph.hasTransitiveConceptConnectedFrom(parentPackageId, PackageContainment.TYPE) { connection ->
        connection.childElementId == childPackageId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether a package with ID [childPackageId] has a given parent or grandparent package with ID [parentPackageId].
 */
fun Model.hasTransitiveParent(childPackageId: Id<Package>, parentPackageId: Id<Package>): Boolean =
    graph.hasTransitiveConceptConnectedTo(childPackageId, PackageContainment.TYPE) { connection ->
        connection.parentPackageId == parentPackageId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the parent package of the package with ID [childPackageId].
 */
fun Model.parentPackage(childPackageId: Id<Package>): Package? =
    graph.findConceptConnectedTo(childPackageId, PackageContainment.TYPE)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Computes the dot-delimited path to a packaged [concept].
 */
fun <Concept : AbstractPackagedConcept<Concept>> Model.path(concept: Concept): String {

    fun buildPath(pkg: Package, subPath: String): String {

        if (pkg.isRoot) {
            return subPath
        }

        with(graph) {

            for (connection in connectionsTo(pkg.id,PackageContainment.TYPE)) {
                return buildPath(concept(connection.parentPackageId) as Package, pkg.name + "." + subPath)
            }

            return "???.$subPath"

        }

    }

    with(graph) {

        for (connection in connections(concept.id)) {
            if (connection is AbstractPackagedElementContainment<*, *> && connection.childElementId == concept.id) {
                return buildPath(concept(connection.parentPackageId) as Package, concept.name)
            }
        }

        return concept.name

    }

}

//---------------------------------------------------------------------------------------------------------------------

/**
 * Finds the child and grandchild packages of the package with ID [packageId].
 */
fun Model.transitiveChildPackages(packageId: Package): Set<Package> =
    graph.findTransitiveConceptsConnectedFrom(packageId, PackageContainment.TYPE)

//---------------------------------------------------------------------------------------------------------------------

