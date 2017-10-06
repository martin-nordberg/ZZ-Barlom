//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.actions.addPackage
import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.views.listitems.viewPackageTreeItem
import org.barlom.presentation.client.views.listitems.viewRootPackageTreeItem
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Generates the browse panel view from the latest application state. Wires event handlers to be dispatched as actions.
 */
fun viewBrowsePanel(
    builder: KatyDomFlowContentBuilder,
    m: Model,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: AbstractPackagedElement?,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    div(".o-panel.o-panel--nav-top.u-pillar-box--small.left-panel") {

        ul("#package-list.c-tree") {

            viewRootPackageTreeItem(this, m.rootPackage, focusedElement, revDispatchUi)

            for (pkg in m.rootPackage.children) {
                viewPackageTreeItem(this, pkg, revDispatchModel, focusedElement, revDispatchUi)
            }

            li {

                onclick {
                    revDispatchModel(addPackage(m.rootPackage))
                }

                text("New Package")

            }

        }

    }

}
