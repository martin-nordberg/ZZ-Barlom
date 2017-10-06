//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.listitems

import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.domain.metamodel.api.vertices.Package
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.actions.focusElement
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder


/** Generates the icon and clickable name for an abstract element. */
fun viewListItem(
    builder: KatyDomFlowContentBuilder,
    element: AbstractPackagedElement,
    revDispatchUi: (uiAction: UiAction) -> Unit
) {

    // TODO: not sure really need different fun for each type; use a classes(...) mapping on the span & a bit of logic for roots instead\

    when (element) {

        is Package ->
            if (element.isRoot) {
                viewRootPackageListItem(builder, element, revDispatchUi)
            }
            else {
                viewPackageListItem(builder, element, revDispatchUi)
            }

        else       ->
            require(false) { "Unimplemented abstract packaged element variation." }

    }

}


/** Generates the icon and clickable name for a package [pkg]. */
fun viewPackageListItem(
    builder: KatyDomFlowContentBuilder,
    pkg: Package,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    span(".c-link", pkg.id) {

        onclick { e ->
            revDispatchUi(focusElement(pkg))
            e.stopPropagation()
        }

        span(".mdi.mdi-folder.package-icon", "icon") {}

        text(" " + pkg.name)

    }

}


/** Generates the icon and clickable name for root package [pkg]. */
fun viewRootPackageListItem(
    builder: KatyDomFlowContentBuilder,
    pkg: Package,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    span(".c-link", pkg.id) {

        onclick { e ->
            revDispatchUi(focusElement(pkg))
            e.stopPropagation()
        }

        span(".mdi.mdi-book-open.root-package-icon", "icon") {}

        text(" Metamodel")

    }

}
