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
    graph.findConceptsConnectedFrom<Package, PackageContainment, Package>(parentPackageId)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the package with ID [parentPackageId] has a child package with ID [childPackage].
 */
fun Model.hasChild(parentPackageId: Id<Package>, childPackageId: Id<Package>): Boolean =
    graph.hasConceptConnectedFrom<Package, PackageContainment, Package>(parentPackageId) { connection ->
        connection.childElementId == childPackageId
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether the package with ID [childPackageId] has parent with ID [parentPackage].
 */
fun Model.hasParent(childPackageId: Id<Package>, parentPackageId: Id<Package>): Boolean =
    hasChild(parentPackageId, childPackageId)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether a given [parentPackage] has a given child or grandchild package [childPackage].
 */
fun Model.hasTransitiveChild(parentPackage: Package, childPackage: Package): Boolean =
    graph.hasTransitiveConceptConnectedFrom<Package, PackageContainment>(parentPackage.id) { connection ->
        connection.childElementId == childPackage.id
    }

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests whether a given [parentPackage] has a given child or grandchild package [childPackage].
 */
fun Model.hasTransitiveParent(childPackage: Package, parentPackage: Package): Boolean =
    hasTransitiveChild(parentPackage, childPackage)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Computes the parent package of a [childPackage].
 */
fun Model.parentPackage(childPackage: Package): Package? =
    graph.findConceptConnectedTo<Package, PackageContainment, Package>(childPackage.id)

//---------------------------------------------------------------------------------------------------------------------

/**
 * Computes the dot-delimited path to a packaged concept.
 */
fun <Concept : AbstractPackagedConcept<Concept>> Model.path(concept: Concept): String {

    fun buildPath(pkg: Package, subPath: String): String {

        if (pkg.isRoot) {
            return subPath
        }

        with(graph) {

            for (connection in connections(pkg.id)) {
                if (connection is PackageContainment && connection.childElementId == pkg.id) {
                    return buildPath(concept(connection.parentPackageId) as Package, pkg.name + "." + subPath)
                }
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
 * Finds the child and grandchild packages of a package.
 */
fun Model.transitiveChildPackages(pkg: Package): Set<Package> =
    graph.findTransitiveConceptsConnectedFrom<Package, PackageContainment>(pkg.id)

//---------------------------------------------------------------------------------------------------------------------

