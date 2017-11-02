//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.state

import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.presentation.client.ApplicationState

/**
 * Creates and initializes an application state for testing.
 */
fun initializeAppStateForTesting(revHistory: RevisionHistory): ApplicationState {

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

        val pkg1b = m.makePackage {
            name = "pkg1b"
            m.makePackageContainment(pkg1, this)
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

