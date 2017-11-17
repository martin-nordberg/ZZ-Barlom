//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels.browse

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.actions.PackageActions
import org.barlom.domain.metamodel.api.vertices.DirectedEdgeType
import org.barlom.domain.metamodel.api.vertices.Package
import org.barlom.domain.metamodel.api.vertices.UndirectedEdgeType
import org.barlom.domain.metamodel.api.vertices.VertexType
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.actions.leftpanels.browse.BrowsePanelAction
import org.barlom.presentation.client.actions.leftpanels.browse.BrowsePanelActions
import org.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState
import org.barlom.presentation.client.views.listitems.*
import org.katydom.api.katyDomComponent
import org.katydom.api.katyDomListItemComponent
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomListItemContentBuilder


/** Generates a tree item for a given [directedEdgeType]. */
fun viewDirectedEdgeTypeTreeItem(
    builder: KatyDomListItemContentBuilder,
    directedEdgeType: DirectedEdgeType,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    val hasChildren = directedEdgeType.attributeTypes.isNotEmpty()

    val isFocused = directedEdgeType == browsePanelState.focusedElement

    li {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", directedEdgeType.id.toString())

        viewListItem(this, directedEdgeType, revDispatchUi)

        if (hasChildren) {
            // TODO
        }

    }

}

/** Generates a tree item for a given package [pkg]. */
@Suppress("RedundantUnitReturnType")
fun viewPackageTreeItem(
    builder: KatyDomListItemContentBuilder,
    pkg: Package,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
): Unit = katyDomListItemComponent(builder) {

    val hasChildren = pkg.childPackageContainments.isNotEmpty() ||
                      pkg.vertexTypeContainments.isNotEmpty() ||
                      pkg.directedEdgeTypes.isNotEmpty()||
                      pkg.undirectedEdgeTypes.isNotEmpty()

    val isExpanded = browsePanelState.isExpandedElement(pkg)

    val isFocused = pkg == browsePanelState.focusedElement

    li(".c-tree__item") {

        classes(
            "c-tree__item--expanded" to (hasChildren && isExpanded),
            "c-tree__item--expandable" to (hasChildren && !isExpanded),
            "c-tree__item--empty" to !hasChildren,
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", pkg.id.toString())

        onclick { e ->

            if (e.offsetX < 20) {
                revDispatchBrowse(BrowsePanelActions.toggleExpanded(pkg))
                e.stopPropagation()
            }

        }

        viewListItem(this, pkg, revDispatchUi)

        if (hasChildren && isExpanded) {
            viewPackageChildTreeItems(this, pkg, browsePanelState, revDispatchModel, revDispatchUi, revDispatchBrowse)
        }

    }

}

/** Fills in the child items of a package. */
private fun viewPackageChildTreeItems(
    builder: KatyDomFlowContentBuilder,
    pkg: Package,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomComponent(builder) {

    ul(".c-tree") {

        onclick { e -> e.stopPropagation() }

        for (subpkg in pkg.children) {
            viewPackageTreeItem(this, subpkg, browsePanelState, revDispatchModel, revDispatchUi,
                                revDispatchBrowse)
        }

        // TODO: Move to right panel:
        li(".tree-item--not-focused") {

            onclick {
                revDispatchModel(PackageActions.addPackage(pkg))
                // TODO: focus the newly created package
            }

            text("New Package")

        }

        for (vt in pkg.vertexTypes) {
            viewVertexTypeTreeItem(this, vt, browsePanelState, revDispatchModel, revDispatchUi, revDispatchBrowse)
        }

        for (et in pkg.undirectedEdgeTypes) {
            viewUndirectedEdgeTypeTreeItem(this, et, browsePanelState, revDispatchModel, revDispatchUi, revDispatchBrowse)
        }

        for (et in pkg.directedEdgeTypes) {
            viewDirectedEdgeTypeTreeItem(this, et, browsePanelState, revDispatchModel, revDispatchUi, revDispatchBrowse)
        }

    }

}

/** Generates a tree item for a given package [pkg]. */
fun viewRootPackageTreeItem(
    builder: KatyDomListItemContentBuilder,
    pkg: Package,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    require(pkg.isRoot) { "Root package expected." }

    val hasChildren = pkg.childPackageContainments.isNotEmpty() ||
                      pkg.vertexTypeContainments.isNotEmpty() ||
                      pkg.directedEdgeTypes.isNotEmpty() ||
                      pkg.undirectedEdgeTypes.isNotEmpty()

    val isFocused = pkg == browsePanelState.focusedElement

    li {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", pkg.id.toString())

        viewListItem(this, pkg, revDispatchUi)

        if (hasChildren) {
            viewPackageChildTreeItems(this, pkg, browsePanelState, revDispatchModel, revDispatchUi, revDispatchBrowse)
        }

    }

}

/** Generates a tree item for a given [undirectedEdgeType]. */
fun viewUndirectedEdgeTypeTreeItem(
    builder: KatyDomListItemContentBuilder,
    undirectedEdgeType: UndirectedEdgeType,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    val hasChildren = undirectedEdgeType.attributeTypes.isNotEmpty()

    val isFocused = undirectedEdgeType == browsePanelState.focusedElement

    li {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", undirectedEdgeType.id.toString())

        viewListItem(this, undirectedEdgeType, revDispatchUi)

        if (hasChildren) {
            // TODO
        }

    }

}


/** Generates a tree item for a given [vertexType]. */
fun viewVertexTypeTreeItem(
    builder: KatyDomListItemContentBuilder,
    vertexType: VertexType,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    val hasChildren = vertexType.attributeTypes.isNotEmpty()

    val isFocused = vertexType == browsePanelState.focusedElement

    li {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", vertexType.id.toString())

        viewListItem(this, vertexType, revDispatchUi)

        if (hasChildren) {
            // TODO
        }

    }

}


