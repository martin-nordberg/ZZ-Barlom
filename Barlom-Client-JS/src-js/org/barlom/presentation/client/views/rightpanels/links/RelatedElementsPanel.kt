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
                    listOf(
                        AddElementConfig("Add a child package") { pkg ->
                            revDispatchModel(PackageActions.addPackage(pkg))
                        }
                    )
                )
                viewChildElements(
                    this,
                    focusedElement,
                    Package::vertexTypes,
                    revDispatchUi,
                    "Vertex Types:",
                    "vertex-types",
                    listOf(
                        AddElementConfig("Add a vertex type") { pkg ->
                            revDispatchModel(PackageActions.addVertexType(pkg))
                        }
                    )
                )
                viewChildElements(
                    this,
                    focusedElement,
                    Package::undirectedEdgeTypes,
                    revDispatchUi,
                    "Undirected Edge Types:",
                    "undirected-edge-types",
                    listOf(
                        AddElementConfig("Add an undirected edge type") { pkg ->
                            revDispatchModel(PackageActions.addUndirectedEdgeType(pkg))
                        }
                    )
                )
                viewChildElements(
                    this,
                    focusedElement,
                    Package::directedEdgeTypes,
                    revDispatchUi,
                    "Directed Edge Types:",
                    "directed-edge-types",
                    listOf(
                        AddElementConfig("Add a directed edge type") { pkg ->
                            revDispatchModel(PackageActions.addDirectedEdgeType(pkg))
                        }
                    )
                )
                viewChildElements(
                    this,
                    focusedElement,
                    Package::constrainedDataTypes,
                    revDispatchUi,
                    "Constrained Data Types:",
                    "constrained-data-types",
                    listOf(
                        AddElementConfig("Add a constrained string data type") { pkg ->
                            revDispatchModel(PackageActions.addConstrainedString(pkg))
                        },
                        AddElementConfig("Add a constrained 32-bit integer data type") { pkg ->
                            revDispatchModel(PackageActions.addConstrainedInteger32(pkg))
                        },
                        AddElementConfig("Add a constrained 64-bit floating point data type") { pkg ->
                            revDispatchModel(PackageActions.addConstrainedFloat64(pkg))
                        },
                        AddElementConfig("Add a constrained boolean data type") { pkg ->
                            revDispatchModel(PackageActions.addConstrainedBoolean(pkg))
                        },
                        AddElementConfig("Add a constrained date/time data type") { pkg ->
                            revDispatchModel(PackageActions.addConstrainedDateTime(pkg))
                        },
                        AddElementConfig("Add a constrained UUID data type") { pkg ->
                            revDispatchModel(PackageActions.addConstrainedUuid(pkg))
                        }
                    )
                )

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

data class AddElementConfig(
    val label: String,
    val action: (Package) -> Unit
);

private inline fun <reified T : AbstractPackagedElement> viewChildElements(
    builder: KatyDomFlowContentBuilder,
    pkg: Package,
    noinline getElements: Package.() -> List<T>,
    noinline revDispatchUi: (uiAction: UiAction) -> Unit,
    label: String,
    name: String,
    addButtons: List<AddElementConfig>
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

        for (addButton in addButtons) {
            li(".tree-item--not-focused") {

                onclick {
                    addButton.action(pkg)
                }

                span(".c-link") {

                    viewListItemIcon(this, T::class, false)

                    span(".mdi.mdi-plus-circle-outline.add-symbol", "add-symbol") {}

                    text(" ${addButton.label}")

                }
            }
        }

    }

}

