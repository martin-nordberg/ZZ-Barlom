//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.Package

/**
 * Adds a new child package to the given [parentPackage].
 */
fun addPackage(parentPackage: Package): ModelAction {

    return { model: Model ->

        model.makePackage {
            model.makePackageContainment(parentPackage, this)
        }

        "Add package to ${parentPackage.path}"

    }

}
