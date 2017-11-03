//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.state

import org.barlom.presentation.client.ApplicationState

/**
 * Creates and initializes the application state.
 */
fun initializeAppState(): ApplicationState {

    val result = ApplicationState()

    val m = result.model

    m.apply {

        val root = rootPackage

        val pkg1 = makePackage {
            name = "pkg1"
            makePackageContainment(root, this)
        }

        val pkg1a = makePackage {
            name = "pkg1a"
            makePackageContainment(pkg1, this)
        }

        val pkg1ai = makePackage {
            name = "pkg1ai"
            makePackageContainment(pkg1a, this)
        }

        val pkg1aii = makePackage {
            name = "pkg1aii"
            makePackageContainment(pkg1a, this)
        }

        val VertexTypeA = makeVertexType {
            name = "VertexTypeA"
            makeVertexTypeContainment(pkg1a, this)
        }

        val VertexTypeB = makeVertexType {
            name = "VertexTypeB"
            makeVertexTypeContainment(pkg1a, this)
        }

        val UndirectedEdgeTypeX = makeUndirectedEdgeType {
            name = "UndirectedEdgeTypeX"
            makeUndirectedEdgeTypeContainment(pkg1a, this)
        }

        val DirectedEdgeTypeY = makeDirectedEdgeType {
            name = "DirectedEdgeTypeY"
            makeDirectedEdgeTypeContainment(pkg1a, this)
        }

        val pkg1b = makePackage {
            name = "pkg1b"
            makePackageContainment(pkg1, this)
        }

        val pkg1bi = makePackage {
            name = "pkg1bi"
            makePackageContainment(pkg1b, this)
        }

        val pkg1bii = makePackage {
            name = "pkg1bii"
            makePackageContainment(pkg1b, this)
        }

        val pkg2 = makePackage {
            name = "pkg2"
            makePackageContainment(root, this)
        }

        val pkg3 = makePackage {
            name = "pkg3"
            makePackageContainment(root, this)
        }

        makePackageDependency(pkg2, pkg1)
        makePackageDependency(pkg3, pkg1)

    }

    return result

}

