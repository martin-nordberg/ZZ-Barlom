//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.leftpanels.browse

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.actions.leftpanels.browse.BrowsePanelAction
import org.barlom.presentation.client.actions.leftpanels.browse.BrowsePanelActions
import org.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState
import org.barlom.presentation.client.views.listitems.viewListItem
import org.katydom.api.katyDomComponent
import org.katydom.api.katyDomListItemComponent
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomListItemContentBuilder


/** Generates a tree item for a given [directedEdgeType]. */
fun viewConstrainedDataTypeTreeItem(
    builder: KatyDomListItemContentBuilder<Unit>,
    constrainedDataType: ConstrainedDataType,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    val isFocused = constrainedDataType == browsePanelState.focusedElement

    li ( ".c-tree__item.c-tree__item--empty", key=constrainedDataType.id) {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", constrainedDataType.id.toString())

        viewListItem(this, constrainedDataType, revDispatchUi)

    }

}

/** Generates a tree item for a given [directedEdgeType]. */
fun viewDirectedEdgeTypeTreeItem(
    builder: KatyDomListItemContentBuilder<Unit>,
    directedEdgeType: DirectedEdgeType,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    val hasChildren = directedEdgeType.attributeTypes.isNotEmpty()

    val isExpanded = browsePanelState.isExpandedElement(directedEdgeType)

    val isFocused = directedEdgeType == browsePanelState.focusedElement

    li( ".c-tree__item", key=directedEdgeType.id) {

        classes(
            "c-tree__item--expanded" to (hasChildren && isExpanded),
            "c-tree__item--expandable" to (hasChildren && !isExpanded),
            "c-tree__item--empty" to !hasChildren,
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
    builder: KatyDomListItemContentBuilder<Unit>,
    pkg: Package,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
): Unit = katyDomListItemComponent(builder) {

    val hasChildren = pkg.containsElements

    val isExpanded = browsePanelState.isExpandedElement(pkg)

    val isFocused = pkg == browsePanelState.focusedElement

    li(".c-tree__item.c-tree__item", key=pkg.id) {

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

            emptyList()

        }

        viewListItem(this, pkg, revDispatchUi)

        if (hasChildren && isExpanded) {
            viewPackageChildTreeItems(this, pkg, browsePanelState, revDispatchModel, revDispatchUi, revDispatchBrowse)
        }

    }

}

/** Fills in the child items of a package. */
private fun viewPackageChildTreeItems(
    builder: KatyDomFlowContentBuilder<Unit>,
    pkg: Package,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomComponent(builder) {

    ul(".c-tree") {

        onclick { e ->
            e.stopPropagation()
            emptyList()
        }

        for (subpkg in pkg.childPackages) {
            viewPackageTreeItem(this, subpkg, browsePanelState, revDispatchModel, revDispatchUi,
                                revDispatchBrowse)
        }

        for (vt in pkg.vertexTypes) {
            viewVertexTypeTreeItem(this, vt, browsePanelState, revDispatchModel, revDispatchUi, revDispatchBrowse)
        }

        for (et in pkg.undirectedEdgeTypes) {
            viewUndirectedEdgeTypeTreeItem(this, et, browsePanelState, revDispatchModel, revDispatchUi,
                                           revDispatchBrowse)
        }

        for (et in pkg.directedEdgeTypes) {
            viewDirectedEdgeTypeTreeItem(this, et, browsePanelState, revDispatchModel, revDispatchUi, revDispatchBrowse)
        }

        for (cdt in pkg.constrainedDataTypes) {
            viewConstrainedDataTypeTreeItem(this, cdt, browsePanelState, revDispatchModel, revDispatchUi,
                                            revDispatchBrowse)
        }

    }

}

/** Generates a tree item for a given package [pkg]. */
fun viewRootPackageTreeItem(
    builder: KatyDomListItemContentBuilder<Unit>,
    pkg: Package,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    require(pkg.isRoot) { "Root package expected." }

    val hasChildren = pkg.containsElements

    val isFocused = pkg == browsePanelState.focusedElement

    li (key=pkg.id) {

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
    builder: KatyDomListItemContentBuilder<Unit> ,
    undirectedEdgeType: UndirectedEdgeType,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    val hasChildren = undirectedEdgeType.attributeTypes.isNotEmpty()

    val isExpanded = browsePanelState.isExpandedElement(undirectedEdgeType)

    val isFocused = undirectedEdgeType == browsePanelState.focusedElement

    li( ".c-tree__item", key=undirectedEdgeType.id) {

        classes(
            "c-tree__item--expanded" to (hasChildren && isExpanded),
            "c-tree__item--expandable" to (hasChildren && !isExpanded),
            "c-tree__item--empty" to !hasChildren,
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


/** Generates a tree item for a given [vertexAttributeType]. */
fun viewVertexAttributeTypeTreeItem(
    builder: KatyDomListItemContentBuilder<Unit>,
    vertexAttributeType: VertexAttributeType,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    val isFocused = vertexAttributeType == browsePanelState.focusedElement

    li (".c-tree__item.c-tree__item--empty", key=vertexAttributeType.id) {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", vertexAttributeType.id.toString())

        viewListItem(this, vertexAttributeType, revDispatchUi)

    }

}


/** Generates a tree item for a given [vertexType]. */
fun viewVertexTypeTreeItem(
    builder: KatyDomListItemContentBuilder<Unit> ,
    vertexType: VertexType,
    browsePanelState: BrowsePanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchBrowse: (browseAction: BrowsePanelAction) -> Unit
) = katyDomListItemComponent(builder) {

    val hasChildren = vertexType.attributeTypes.isNotEmpty()

    val isExpanded = browsePanelState.isExpandedElement(vertexType)

    val isFocused = vertexType == browsePanelState.focusedElement

    li( ".c-tree__item", key=vertexType.id) {

        classes(
            "c-tree__item--expanded" to (hasChildren && isExpanded),
            "c-tree__item--expandable" to (hasChildren && !isExpanded),
            "c-tree__item--empty" to !hasChildren,
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", vertexType.id.toString())

        onclick { e ->

            if (e.offsetX < 20) {
                revDispatchBrowse(BrowsePanelActions.toggleExpanded(vertexType))
                e.stopPropagation()
            }

            emptyList()

        }

        viewListItem(this, vertexType, revDispatchUi)

        if (hasChildren && isExpanded) {

            ul(".c-tree") {

                onclick {
                    e -> e.stopPropagation()
                    emptyList()
                }

                for (at in vertexType.attributeTypes) {
                    viewVertexAttributeTypeTreeItem(
                        this, at, browsePanelState, revDispatchModel, revDispatchUi, revDispatchBrowse
                    )
                }

            }

        }

    }

}



