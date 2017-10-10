//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.listitems

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.actions.PackageActions
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.domain.metamodel.api.vertices.Package
import org.barlom.presentation.client.actions.UiAction
import org.katydom.api.katyDomListItemComponent
import org.katydom.builders.KatyDomListItemContentBuilder


/** Generates a tree item for a given package [pkg]. */
@Suppress("RedundantUnitReturnType")
fun viewPackageTreeItem(
    builder: KatyDomListItemContentBuilder,
    pkg: Package,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: AbstractPackagedElement?,
    revDispatchUi: (uiAction: UiAction) -> Unit
): Unit = katyDomListItemComponent(builder) {

    val hasChildren = pkg.childPackageContainments.isNotEmpty()

    li(".c-tree__item") {

        classes(
            "c-tree__item--expanded" to (hasChildren && true/*TODO*/),
            "c-tree__item--expandable" to (hasChildren && false),
            "c-tree__item--empty" to !hasChildren,
            "tree-item--focused" to (pkg == focusedElement),
            "tree-item--not-focused" to (pkg != focusedElement)
        )

        data("uuid", pkg.id.toString())

        onclick { e ->
            console.log("Tree node clicked: ", e.offsetX)
        }

        viewPackageListItem(this, pkg, revDispatchUi)

        if (hasChildren) {

            ul(".c-tree") {

                for (subpkg in pkg.children) {
                    viewPackageTreeItem(this, subpkg, revDispatchModel, focusedElement, revDispatchUi)
                }

                li(".tree-item--not-focused") {

                    onclick {
                        revDispatchModel(PackageActions.addPackage(pkg))
                        // TODO: focus the newly created package
                    }

                    text("New Package")

                }

            }

        }

    }

}

/** Generates a tree item for a given package [pkg]. */
fun viewRootPackageTreeItem(
    builder: KatyDomListItemContentBuilder,
    pkg: Package,
    focusedElement: AbstractPackagedElement?,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomListItemComponent(builder) {

    require(pkg.isRoot) { "Root package expected." }

    li {

        classes(
            "tree-item--focused" to (pkg == focusedElement),
            "tree-item--not-focused" to (pkg != focusedElement)
        )

        data("uuid", pkg.id.toString())

        onclick { e ->
            console.log("Tree node clicked: ", e.offsetX)
        }

        viewRootPackageListItem(this, pkg, revDispatchUi)

    }

}



