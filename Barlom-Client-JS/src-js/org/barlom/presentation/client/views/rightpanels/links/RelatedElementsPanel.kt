//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.rightpanels.links

import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.actions.PackageActions
import org.barlom.domain.metamodel.api.actions.VertexTypeActions
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
    focusedElement: AbstractNamedElement,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    form("#related-elements-form.form--related-elements", action = "javascript:void(0);") {

        when (focusedElement) {

            is Package            -> {
                viewPackageChildElements(this, focusedElement, revDispatchUi, revDispatchModel)
            }

            is VertexType         -> if (!focusedElement.isRoot) {
                viewVertexTypeChildElements(this, focusedElement, revDispatchUi, revDispatchModel)
            }

            is UndirectedEdgeType -> {
            }

            is DirectedEdgeType   -> {
            }

        }

    }

}


/**
 * Shows lists of the different kinds of child elements in a package.
 */
private fun viewPackageChildElements(
    builder: KatyDomFlowContentBuilder,
    focusedElement: Package,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchModel: (modelAction: ModelAction) -> Unit
) = katyDomComponent(builder) {

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


/**
 * Shows lists of the different kinds of child elements in a vertex type.
 */
private fun viewVertexTypeChildElements(
    builder: KatyDomFlowContentBuilder,
    focusedElement: VertexType,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchModel: (modelAction: ModelAction) -> Unit
) = katyDomComponent(builder) {

    viewChildElements(
        this,
        focusedElement,
        VertexType::attributeTypes,
        revDispatchUi,
        "Attribute Types:",
        "attribute-types",
        listOf(
            AddElementConfig("Add an attribute type") { vt ->
                revDispatchModel(VertexTypeActions.addAttributeType(vt))
            }
        )
    )

}


data class AddElementConfig<Parent>(
    val label: String,
    val action: (Parent) -> Unit
);

/**
 * Shows a list of child elements for given parent.
 */
private inline fun <Parent, reified Child : AbstractNamedElement> viewChildElements(
    builder: KatyDomFlowContentBuilder,
    parent: Parent,
    noinline getChildElements: Parent.() -> List<Child>,
    noinline revDispatchUi: (uiAction: UiAction) -> Unit,
    label: String,
    name: String,
    addButtons: List<AddElementConfig<Parent>>
) = katyDomComponent(builder) {

    // Label the list.
    label("#${name}-label.c-label.o-form-element") {
        text(label)
    }

    ul("#${name}-list.c-tree") {

        // Show the child elements.
        for (child in parent.getChildElements()) {

            li(".tree-item--not-focused") {

                data("uuid", child.id.toString())

                viewListItem(this, child, revDispatchUi)

            }

        }

        // Show links for creating new child elements.
        for (addButton in addButtons) {
            li(".tree-item--not-focused") {

                onclick {
                    addButton.action(parent)
                }

                span(".c-link") {

                    viewListItemIcon(this, Child::class, false)

                    span(".mdi.mdi-plus-circle-outline.add-symbol", "add-symbol") {}

                    text(" ${addButton.label}")

                }
            }
        }

    }

}

