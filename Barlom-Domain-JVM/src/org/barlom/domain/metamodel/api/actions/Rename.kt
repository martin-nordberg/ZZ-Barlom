package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.domain.metamodel.api.vertices.Package

class Rename(private val element: AbstractPackagedElement, private val newName: String)
    : IModelAction {

    override fun apply(model: Model) {
        element.name = newName
    }

    override val description: String
        get() = "Rename " + element.path + " to " + newName + "."

}