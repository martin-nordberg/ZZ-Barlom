//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.rightpanels.links

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.actions.PackageActions
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.views.listitems.viewListItem
import org.barlom.presentation.client.views.listitems.viewListItemIcon
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Builds the right panel with lists of elements related to the focused element.
 */
fun viewRelatedElements(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: AbstractPackagedElement,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    form("#related-elements-form.form--related-elements", action = "javascript:void(0);") {

        when (focusedElement) {

            is Package            -> {

                viewChildElements(
                    this,
                    focusedElement,
                    Package::childPackages,
                    revDispatchUi,
                    "Child Packages:",
                    "child-packages",
                    ".mdi-folder.package-icon",
                    "Add a child package") { pkg ->
                    revDispatchModel(PackageActions.addPackage(pkg))
                }
                viewChildElements(
                    this,
                    focusedElement,
                    Package::vertexTypes,
                    revDispatchUi,
                    "Vertex Types:",
                    "vertex-types",
                    ".mdi-ray-vertex.vertex-type-icon",
                    "Add a vertex type") { pkg ->
                    revDispatchModel(PackageActions.addVertexType(pkg))
                }
                viewChildElements(
                    this,
                    focusedElement,
                    Package::undirectedEdgeTypes,
                    revDispatchUi,
                    "Undirected Edge Types:",
                    "undirected-edge-types",
                    ".mdi-ray-start-end.undirected-edge-type-icon",
                    "Add an undirected edge type") { pkg ->
                    revDispatchModel(PackageActions.addUndirectedEdgeType(pkg))
                }
                viewChildElements(
                    this,
                    focusedElement,
                    Package::directedEdgeTypes,
                    revDispatchUi,
                    "Directed Edge Types:",
                    "directed-edge-types",
                    ".mdi-ray-start-arrow.directed-edge-type-icon",
                    "Add a directed edge type") { pkg ->
                    revDispatchModel(PackageActions.addDirectedEdgeType(pkg))
                }
                viewChildElements(
                    this,
                    focusedElement,
                    Package::constrainedDataTypes,
                    revDispatchUi,
                    "Constrained Data Types:",
                    "constrained-data-types",
                    ".mdi-square-outline.constrained-string-icon",
                    "Add a constrained string data type") { pkg ->
                    revDispatchModel(PackageActions.addConstrainedString(pkg))
                }

            }

            is VertexType         -> {
            }

            is UndirectedEdgeType -> {
            }

            is DirectedEdgeType   -> {
            }

        }

    }

}

private inline fun <reified T : AbstractPackagedElement> viewChildElements(
    builder: KatyDomFlowContentBuilder,
    pkg: Package,
    noinline getElements: Package.() -> List<T>,
    noinline revDispatchUi: (uiAction: UiAction) -> Unit,
    label: String,
    name: String,
    iconClasses: String,
    addLabel: String,
    noinline addAction: (Package) -> Unit
) = katyDomComponent(builder) {

    label("#${name}-label.c-label.o-form-element") {
        text(label)
    }

    ul("#${name}-list.c-tree") {

        for (childPkg in pkg.getElements()) {

            li(".tree-item--not-focused") {

                data("uuid", childPkg.id.toString())

                viewListItem(this, childPkg, revDispatchUi)

            }

        }

        li(".tree-item--not-focused") {

            onclick {
                addAction(pkg)
            }

            span(".c-link") {

                viewListItemIcon(this, T::class, false)

                span(".mdi.mdi-plus-circle-outline.add-symbol", "add-symbol") {}

                text(" ${addLabel}")

            }
        }

    }

}

