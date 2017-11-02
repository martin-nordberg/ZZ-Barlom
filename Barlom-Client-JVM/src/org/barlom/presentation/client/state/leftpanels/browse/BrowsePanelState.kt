//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.state.leftpanels.browse

import org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VHashSet
import org.barlom.infrastructure.uuids.Uuid

/**
 * The Browse panel UI state of the application.
 */
class BrowsePanelState(

    private val _focusedElement: V<AbstractPackagedElement?>

) {

    private var _expandedBrowseTreeElements = VHashSet<Uuid>(2000)


    var focusedElement
        get() = _focusedElement.get()
        set(value) = _focusedElement.set(value)


    fun addExpandedElement(element: AbstractDocumentedElement) {
        _expandedBrowseTreeElements.add(element.id)
    }

    fun isExpandedElement(element: AbstractDocumentedElement): Boolean {
        return _expandedBrowseTreeElements.contains(element.id)
    }

    fun removeExpandedElement(element: AbstractDocumentedElement) {
        _expandedBrowseTreeElements.remove(element.id)
    }

}
