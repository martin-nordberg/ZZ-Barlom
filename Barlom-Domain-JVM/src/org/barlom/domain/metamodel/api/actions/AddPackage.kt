//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.Package

fun addPackage(parentPackage: Package): ModelAction {

    fun result(model: Model): String {
        model.makePackage() {
            model.makePackageContainment(parentPackage, this)
        }
        return "Add package to ${parentPackage.path}"
    }

    return ::result

}
