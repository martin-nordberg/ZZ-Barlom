//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement


fun rename(element: AbstractPackagedElement, newName: String): ModelAction {

    fun result(model: Model): String {
        element.name = newName
        return "Rename ${element.path} to ${newName}."
    }

    return ::result
}
