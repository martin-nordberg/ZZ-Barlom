//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.presentation.client.state.leftpanels.browse

import o.org.barlom.domain.metamodel.api.vertices.*
import o.org.barlom.infrastructure.revisions.V
import o.org.barlom.infrastructure.revisions.VHashSet
import x.org.barlom.infrastructure.uuids.Uuid

/**
 * The Browse panel UI state of the application.
 */
class BrowsePanelState(

    private val _focusedElement: V<AbstractNamedElement?>

) {

    private var _expandedBrowseTreeElements = VHashSet<Uuid>(2000)


    var focusedElement
        get() = _focusedElement.get()
        set(value) = _focusedElement.set(value)


    fun addExpandedElement(element: AbstractDocumentedElement) {
        _expandedBrowseTreeElements.add(element.id)
    }

    fun isExpandedElement(element: AbstractDocumentedElement): Boolean {

        if (_expandedBrowseTreeElements.contains(element.id)) {
            return true
        }

        val focusedElement = _focusedElement.get()

        if ( focusedElement == null ) {
            return false
        }

        return element is Package && focusedElement.hasParentPackage(element) ||
            element is VertexType && focusedElement is VertexAttributeType && focusedElement.hasParent(element)
    }

    fun removeExpandedElement(element: AbstractDocumentedElement) {
        _expandedBrowseTreeElements.remove(element.id)
    }

}
