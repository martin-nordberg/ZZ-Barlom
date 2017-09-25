//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.listitems

import org.barlom.domain.metamodel.api.vertices.Package
import org.barlom.presentation.client.actions.IUiAction
import org.barlom.presentation.client.actions.ui.FocusElement
import org.katydom.api.katyDomComponent


/** Generates the icon and clickable name for a package [pkg]. */
fun viewPackageListItem(
    pkg: Package,
    revDispatchUi: (makeUiAction: () -> IUiAction) -> Unit
) = katyDomComponent {

    span(".c-link") {

        val msg = "Package " + pkg.name + " clicked."
        onclick { e ->
            revDispatchUi{ FocusElement(pkg) }
            console.log(msg)
            e.stopPropagation()
        }

        span(".mdi.mdi-folder.package-icon") {}

        text(" " + pkg.name)

    }

}
