//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.views.leftpanels.browse

import o.org.barlom.domain.metamodel.api.vertices.*
import o.org.barlom.presentation.client.actions.leftpanels.browse.BrowsePanelActions
import js.org.barlom.presentation.client.messages.Message
import js.org.barlom.presentation.client.messages.leftpanels.browse.BrowsePanelActionMessage
import o.org.katydom.builders.lists.KatyDomUnorderedListContentBuilder
import o.org.barlom.presentation.client.state.leftpanels.browse.BrowsePanelState
import js.org.barlom.presentation.client.views.listitems.viewListItem
import o.org.katydom.api.katyDomComponent
import o.org.katydom.api.katyDomListItemsComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder
import o.org.katydom.eventhandling.onclick


/** Generates a tree item for a given [constrainedDataType]. */
fun viewConstrainedDataTypeTreeItem(
    builder: KatyDomUnorderedListContentBuilder<Message>,
    constrainedDataType: ConstrainedDataType,
    browsePanelState: BrowsePanelState
) = katyDomListItemsComponent(builder) {

    val isFocused = constrainedDataType == browsePanelState.focusedElement

    li(".c-tree__item.c-tree__item--empty", key = constrainedDataType.id) {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", constrainedDataType.id.toString())

        viewListItem(this, constrainedDataType)

    }

}

/** Generates a tree item for a given [directedEdgeType]. */
fun viewDirectedEdgeTypeTreeItem(
    builder: KatyDomUnorderedListContentBuilder<Message>,
    directedEdgeType: DirectedEdgeType,
    browsePanelState: BrowsePanelState
) = katyDomListItemsComponent(builder) {

    val hasChildren = directedEdgeType.attributeTypes.isNotEmpty()

    val isExpanded = browsePanelState.isExpandedElement(directedEdgeType)

    val isFocused = directedEdgeType == browsePanelState.focusedElement

    li(".c-tree__item", key = directedEdgeType.id) {

        classes(
            "c-tree__item--expanded" to (hasChildren && isExpanded),
            "c-tree__item--expandable" to (hasChildren && !isExpanded),
            "c-tree__item--empty" to !hasChildren,
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", directedEdgeType.id.toString())

        onclick { e ->

            if (e.offsetX < 20) {
                e.stopPropagation()
                listOf(BrowsePanelActionMessage(BrowsePanelActions.toggleExpanded(directedEdgeType)))
            }
            else {
                emptyList()
            }

        }

        viewListItem(this, directedEdgeType)

        if (hasChildren && isExpanded) {

            ul(".c-tree") {

                onclick { e ->
                    e.stopPropagation()
                    emptyList()
                }

                for (at in directedEdgeType.attributeTypes) {
                    viewEdgeAttributeTypeTreeItem(this, at, browsePanelState)
                }

            }

        }

    }

}


/** Generates a tree item for a given [edgeAttributeType]. */
fun viewEdgeAttributeTypeTreeItem(
    builder: KatyDomUnorderedListContentBuilder<Message>,
    edgeAttributeType: EdgeAttributeType,
    browsePanelState: BrowsePanelState
) = katyDomListItemsComponent(builder) {

    val isFocused = edgeAttributeType == browsePanelState.focusedElement

    li(".c-tree__item.c-tree__item--empty", key = edgeAttributeType.id) {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", edgeAttributeType.id.toString())

        viewListItem(this, edgeAttributeType)

    }

}


/** Generates a tree item for a given package [pkg]. */
@Suppress("RedundantUnitReturnType")
fun viewPackageTreeItem(
    builder: KatyDomUnorderedListContentBuilder<Message>,
    pkg: Package,
    browsePanelState: BrowsePanelState
): Unit = katyDomListItemsComponent(builder) {

    val hasChildren = pkg.containsElements

    val isExpanded = browsePanelState.isExpandedElement(pkg)

    val isFocused = pkg == browsePanelState.focusedElement

    li(".c-tree__item.c-tree__item", key = pkg.id) {

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
                e.stopPropagation()
                listOf(BrowsePanelActionMessage(BrowsePanelActions.toggleExpanded(pkg)))
            }
            else {
                emptyList()
            }

        }

        viewListItem(this, pkg)

        if (hasChildren && isExpanded) {
            viewPackageChildTreeItems(this, pkg, browsePanelState)
        }

    }

}

/** Fills in the child items of a package. */
private fun viewPackageChildTreeItems(
    builder: KatyDomFlowContentBuilder<Message>,
    pkg: Package,
    browsePanelState: BrowsePanelState
) = katyDomComponent(builder) {

    ul(".c-tree") {

        onclick { e ->
            e.stopPropagation()
            emptyList()
        }

        for (subpkg in pkg.childPackages) {
            viewPackageTreeItem(this, subpkg, browsePanelState)
        }

        for (vt in pkg.vertexTypes) {
            viewVertexTypeTreeItem(this, vt, browsePanelState)
        }

        for (et in pkg.undirectedEdgeTypes) {
            viewUndirectedEdgeTypeTreeItem(this, et, browsePanelState)
        }

        for (et in pkg.directedEdgeTypes) {
            viewDirectedEdgeTypeTreeItem(this, et, browsePanelState)
        }

        for (cdt in pkg.constrainedDataTypes) {
            viewConstrainedDataTypeTreeItem(this, cdt, browsePanelState)
        }

    }

}

/** Generates a tree item for a given package [pkg]. */
fun viewRootPackageTreeItem(
    builder: KatyDomUnorderedListContentBuilder<Message>,
    pkg: Package,
    browsePanelState: BrowsePanelState
) = katyDomListItemsComponent(builder) {

    require(pkg.isRoot) { "Root package expected." }

    val hasChildren = pkg.containsElements

    val isFocused = pkg == browsePanelState.focusedElement

    li(key = pkg.id) {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", pkg.id.toString())

        viewListItem(this, pkg)

        if (hasChildren) {
            viewPackageChildTreeItems(this, pkg, browsePanelState)
        }

    }

}

/** Generates a tree item for a given [undirectedEdgeType]. */
fun viewUndirectedEdgeTypeTreeItem(
    builder: KatyDomUnorderedListContentBuilder<Message>,
    undirectedEdgeType: UndirectedEdgeType,
    browsePanelState: BrowsePanelState
) = katyDomListItemsComponent(builder) {

    val hasChildren = undirectedEdgeType.attributeTypes.isNotEmpty()

    val isExpanded = browsePanelState.isExpandedElement(undirectedEdgeType)

    val isFocused = undirectedEdgeType == browsePanelState.focusedElement

    li(".c-tree__item", key = undirectedEdgeType.id) {

        classes(
            "c-tree__item--expanded" to (hasChildren && isExpanded),
            "c-tree__item--expandable" to (hasChildren && !isExpanded),
            "c-tree__item--empty" to !hasChildren,
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", undirectedEdgeType.id.toString())

        onclick { e ->

            if (e.offsetX < 20) {
                e.stopPropagation()
                listOf(BrowsePanelActionMessage(BrowsePanelActions.toggleExpanded(undirectedEdgeType)))
            }
            else {
                emptyList()
            }

        }

        viewListItem(this, undirectedEdgeType)

        if (hasChildren && isExpanded) {

            ul(".c-tree") {

                onclick { e ->
                    e.stopPropagation()
                    emptyList()
                }

                for (at in undirectedEdgeType.attributeTypes) {
                    viewEdgeAttributeTypeTreeItem(this, at, browsePanelState)
                }

            }

        }

    }

}


/** Generates a tree item for a given [vertexAttributeType]. */
fun viewVertexAttributeTypeTreeItem(
    builder: KatyDomUnorderedListContentBuilder<Message>,
    vertexAttributeType: VertexAttributeType,
    browsePanelState: BrowsePanelState
) = katyDomListItemsComponent(builder) {

    val isFocused = vertexAttributeType == browsePanelState.focusedElement

    li(".c-tree__item.c-tree__item--empty", key = vertexAttributeType.id) {

        classes(
            "tree-item--focused" to isFocused,
            "tree-item--not-focused" to !isFocused
        )

        data("uuid", vertexAttributeType.id.toString())

        viewListItem(this, vertexAttributeType)

    }

}


/** Generates a tree item for a given [vertexType]. */
fun viewVertexTypeTreeItem(
    builder: KatyDomUnorderedListContentBuilder<Message>,
    vertexType: VertexType,
    browsePanelState: BrowsePanelState
) = katyDomListItemsComponent(builder) {

    val hasChildren = vertexType.attributeTypes.isNotEmpty()

    val isExpanded = browsePanelState.isExpandedElement(vertexType)

    val isFocused = vertexType == browsePanelState.focusedElement

    li(".c-tree__item", key = vertexType.id) {

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
                e.stopPropagation()
                listOf(BrowsePanelActionMessage(BrowsePanelActions.toggleExpanded(vertexType)))
            }
            else {
                emptyList()
            }

        }

        viewListItem(this, vertexType)

        if (hasChildren && isExpanded) {

            ul(".c-tree") {

                onclick { e ->
                    e.stopPropagation()
                    emptyList()
                }

                for (at in vertexType.attributeTypes) {
                    viewVertexAttributeTypeTreeItem(this, at, browsePanelState)
                }

            }

        }

    }

}



