//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.Package

class AddPackage(private val parentPackage: Package)
    : IModelAction {

    override fun apply(model: Model) {
        model.makePackage() {
            model.makePackageContainment(parentPackage, this)
        }
    }

    override val description: String
        get() = "Add package"

}


