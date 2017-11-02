//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.state

import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.presentation.client.ApplicationState

/**
 * Creates and initializes the application state.
 */
fun initializeAppState(revHistory: RevisionHistory): ApplicationState {

    val result = ApplicationState(revHistory)

    val m = result.model

    m.revHistory.update {

        val root = m.rootPackage

        val pkg1 = m.makePackage {
            name = "pkg1"
            m.makePackageContainment(root, this)
        }

        val pkg1a = m.makePackage {
            name = "pkg1a"
            m.makePackageContainment(pkg1, this)
        }

        val pkg1ai = m.makePackage {
            name = "pkg1ai"
            m.makePackageContainment(pkg1a, this)
        }

        val pkg1aii = m.makePackage {
            name = "pkg1aii"
            m.makePackageContainment(pkg1a, this)
        }

        val pkg1b = m.makePackage {
            name = "pkg1b"
            m.makePackageContainment(pkg1, this)
        }

        val pkg1bi = m.makePackage {
            name = "pkg1bi"
            m.makePackageContainment(pkg1b, this)
        }

        val pkg1bii = m.makePackage {
            name = "pkg1bii"
            m.makePackageContainment(pkg1b, this)
        }

        val pkg2 = m.makePackage {
            name = "pkg2"
            m.makePackageContainment(root, this)
        }

        val pkg3 = m.makePackage {
            name = "pkg3"
            m.makePackageContainment(root, this)
        }

        m.makePackageDependency(pkg2, pkg1)
        m.makePackageDependency(pkg3, pkg1)

        "Initialized model"

    }

    return result

}

