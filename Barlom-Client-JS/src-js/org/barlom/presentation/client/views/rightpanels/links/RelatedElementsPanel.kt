//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.rightpanels.links

import org.barlom.domain.metamodel.api.actions.AbstractEdgeTypeActions
import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.actions.PackageActions
import org.barlom.domain.metamodel.api.actions.VertexTypeActions
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.actions.UiAction
import org.barlom.presentation.client.state.rightpanels.RelatedElementsPanelState
import org.barlom.presentation.client.views.listitems.viewListItem
import org.barlom.presentation.client.views.listitems.viewListItemIcon
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Builds the right panel with lists of elements related to the focused element.
 */
fun viewRelatedElements(
    builder: KatyDomFlowContentBuilder,
    relatedElementsPanelState: RelatedElementsPanelState,
    revDispatchModel: (modelAction: ModelAction) -> Unit,
    focusedElement: AbstractNamedElement,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    form("#related-elements-form.form--related-elements", action = "javascript:void(0);") {

        when (focusedElement) {

            is Package            -> {
                viewPackageChildElements(this, focusedElement, revDispatchUi, revDispatchModel)
                viewPackageSuppliers(this, relatedElementsPanelState, focusedElement, revDispatchUi,
                                     revDispatchModel)
                viewPackageConsumers(this, focusedElement, revDispatchUi)
            }

            is VertexType         -> if (!focusedElement.isRoot) {
                viewVertexTypeChildElements(this, focusedElement, revDispatchUi, revDispatchModel)
            }

            is UndirectedEdgeType -> if (!focusedElement.isRoot) {
                viewUndirectedEdgeTypeChildElements(this, focusedElement, revDispatchUi, revDispatchModel)
            }

            is DirectedEdgeType   -> if (!focusedElement.isRoot) {
                viewDirectedEdgeTypeChildElements(this, focusedElement, revDispatchUi, revDispatchModel)
            }

        }

    }

}


/**
 * Shows lists of the different kinds of child elements in a directed edge type.
 */
private fun viewDirectedEdgeTypeChildElements(
    builder: KatyDomFlowContentBuilder,
    focusedElement: DirectedEdgeType,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchModel: (modelAction: ModelAction) -> Unit
) = katyDomComponent(builder) {

    viewChildElements(
        this,
        focusedElement,
        AbstractEdgeType::attributeTypes,
        revDispatchUi,
        "Attribute Types:",
        "attribute-types",
        listOf(
            AddElementConfig("Add an attribute type") { et ->
                revDispatchModel(AbstractEdgeTypeActions.addAttributeType(et))
            }
        )
    )

    viewChildElements(
        this,
        focusedElement,
        DirectedEdgeType::subTypes,
        revDispatchUi,
        "Sub Types:",
        "sub-types",
        listOf()
    )

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
 * Shows lists of the packages consuming a package.
 */
private fun viewPackageConsumers(
    builder: KatyDomFlowContentBuilder,
    focusedElement: Package,
    revDispatchUi: (uiAction: UiAction) -> Unit
) = katyDomComponent(builder) {

    viewChildElements(
        this,
        focusedElement,
        Package::consumers,
        revDispatchUi,
        "Consumer Packages:",
        "consumer-packages",
        listOf()
    )

}


/**
 * Shows a list of the supplier packages for a package.
 */
private fun viewPackageSuppliers(
    builder: KatyDomFlowContentBuilder,
    relatedElementsPanelState: RelatedElementsPanelState,
    focusedElement: Package,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchModel: (modelAction: ModelAction) -> Unit
) = katyDomComponent(builder) {

    viewConnectedElements(
        this,
        focusedElement,
        Package::suppliers,
        Package::findPotentialSuppliers,
        revDispatchUi,
        "Supplier Packages:",
        "supplier-packages",
        listOf(
            AddConnectionConfig(
                "Add a supplier package",
                relatedElementsPanelState.newSupplierPackagePath
            ) { pkg ->
                revDispatchUi { uiState ->
                    val supplierPath = uiState.relatedElementsPanelState.newSupplierPackagePath
                    revDispatchModel(PackageActions.addPackageSupplier(pkg, supplierPath))
                    uiState.relatedElementsPanelState.newSupplierPackagePath = ""
                    "Added supplier package."
                }
            }
        )
    )

}


/**
 * Shows lists of the different kinds of child elements in an undirected edge type.
 */
private fun viewUndirectedEdgeTypeChildElements(
    builder: KatyDomFlowContentBuilder,
    focusedElement: UndirectedEdgeType,
    revDispatchUi: (uiAction: UiAction) -> Unit,
    revDispatchModel: (modelAction: ModelAction) -> Unit
) = katyDomComponent(builder) {

    viewChildElements(
        this,
        focusedElement,
        AbstractEdgeType::attributeTypes,
        revDispatchUi,
        "Attribute Types:",
        "attribute-types",
        listOf(
            AddElementConfig("Add an attribute type") { et ->
                revDispatchModel(AbstractEdgeTypeActions.addAttributeType(et))
            }
        )
    )
    viewChildElements(
        this,
        focusedElement,
        UndirectedEdgeType::subTypes,
        revDispatchUi,
        "Sub Types:",
        "sub-types",
        listOf()
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

    viewChildElements(
        this,
        focusedElement,
        VertexType::subTypes,
        revDispatchUi,
        "Sub Types:",
        "sub-types",
        listOf()
    )

    viewChildElements(
        this,
        focusedElement,
        VertexType::connectingEdgeTypes,
        revDispatchUi,
        "Connecting Edge Types (Undirected):",
        "connecting-edge-types-undirected",
        listOf()
    )

    viewChildElements(
        this,
        focusedElement,
        VertexType::connectingHeadEdgeTypes,
        revDispatchUi,
        "Connecting Edge Types (Head):",
        "connecting-edge-types-head",
        listOf()
    )

    viewChildElements(
        this,
        focusedElement,
        VertexType::connectingTailEdgeTypes,
        revDispatchUi,
        "Connecting Edge Types (Tail):",
        "connecting-edge-types-tail",
        listOf()
    )

}


data class AddElementConfig<in Parent>(
    val label: String,
    val action: (Parent) -> Unit
)

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
    label("#$name-label.c-label.o-form-element") {
        text(label)
    }

    ul("#$name-list.c-tree") {

        // Show the child elements.
        for (child in parent.getChildElements()) {

            li(".tree-item--not-focused", key = child.id) {

                data("uuid", child.id.toString())

                viewListItem(this, child, revDispatchUi)

            }

        }

        // Show links for creating new child elements.
        for (addButton in addButtons) {

            li(".tree-item--not-focused", key=addButton.label) {

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

        // Show a placeholder if no items and no buttons.
        if (parent.getChildElements().isEmpty() && addButtons.isEmpty()) {

            li(".tree-item--not-focused") {
                text("(None)")
            }

        }

    }

}


data class AddConnectionConfig<in Parent>(
    val label: String,
    val path: String,
    val action: (Parent) -> Unit
)

/**
 * Shows a list of connected elements for given element.
 */
private inline fun <Element, reified ConnectedElement : AbstractNamedElement> viewConnectedElements(
    builder: KatyDomFlowContentBuilder,
    parent: Element,
    noinline getConnectedElements: Element.() -> List<ConnectedElement>,
    noinline getPotentialConnectedElements: Element.() -> List<ConnectedElement>,
    noinline revDispatchUi: (uiAction: UiAction) -> Unit,
    label: String,
    name: String,
    addButtons: List<AddConnectionConfig<Element>>
) = katyDomComponent(builder) {

    // Label the list.
    label("#$name-label.c-label.o-form-element") {
        text(label)
    }

    ul("#$name-list.c-tree") {

        // Show the child elements.
        for (child in parent.getConnectedElements()) {

            li(".tree-item--not-focused", key = child.id) {

                data("uuid", child.id.toString())

                viewListItem(this, child, revDispatchUi)

            }

        }

        // Show links for creating new child elements.
        for (addButton in addButtons) {

            val buttonName = addButton.label.replace(" ", "-")

            li(".tree-item--not-focused", key = "$buttonName-item") {

                val dataListId = "$name-data-list"

                datalist(
                    "#$dataListId"
                ) {

                    for (element in parent.getPotentialConnectedElements()) {
                        option("#${element.id}") { text(element.path) }
                    }

                }

                div(".c-input-group", key = "$buttonName-group") {

                    button(
                        ".c-button.c-button--success.u-small",
                        key = "$buttonName-action-button",
                        style = "width:3em"
                    ) {

                        onclick {
                            addButton.action(parent)
                        }

                        span(".mdi.mdi-plus-circle-outline", "add-symbol") {}

                    }

                    div(".o-field", key = "$buttonName-field") {

                        inputText(
                            ".c-field.u-small",
                            key = "$name-input-field",
                            list = dataListId,
                            placeholder = addButton.label,
                            value = addButton.path

                        ) {

                            onblur { event ->

                                val newValue: String = event.target.asDynamic().value as String

                                if (newValue.isNotBlank()) {

                                    revDispatchUi { uiState ->
                                        uiState.relatedElementsPanelState.newSupplierPackagePath = newValue
                                        "Entered new supplier package path '$newValue'."
                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

    }

}

