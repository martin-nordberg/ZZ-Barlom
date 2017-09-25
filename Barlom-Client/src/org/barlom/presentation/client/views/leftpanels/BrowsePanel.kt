//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels

import org.barlom.domain.metamodel.api.actions.AddPackage
import org.barlom.domain.metamodel.api.actions.IModelAction
import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.AbstractDocumentedElement
import org.barlom.presentation.client.actions.IUiAction
import org.barlom.presentation.client.views.listitems.viewPackageTreeItem
import org.katydom.api.katyDomComponent

/**
 * Generates the browse panel view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun viewBrowsePanel(
    m: Model,
    revDispatchModel: (makeModelAction: () -> IModelAction) -> Unit,
    focusedElement: AbstractDocumentedElement?,
    revDispatchUi: (makeUiAction: () -> IUiAction) -> Unit
) = katyDomComponent {

    div(".o-panel.o-panel--nav-top.u-pillar-box--small.barlom-browse-panel") {

        ul("#package-list.c-tree") {

            for (pkg in m.rootPackage.children) {

                viewPackageTreeItem(pkg,focusedElement,revDispatchUi)()

            }

            li {

                onclick {
                    revDispatchModel { AddPackage(m.rootPackage) }
                    console.log("Clicked")
                }

                text("New Package")

            }

        }

    }

}
