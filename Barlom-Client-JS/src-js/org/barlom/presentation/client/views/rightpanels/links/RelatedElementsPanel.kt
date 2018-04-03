//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.rightpanels.links

import org.barlom.domain.metamodel.api.actions.AbstractEdgeTypeActions
import org.barlom.domain.metamodel.api.actions.ModelAction
import org.barlom.domain.metamodel.api.actions.PackageActions
import org.barlom.domain.metamodel.api.actions.VertexTypeActions
import org.barlom.domain.metamodel.api.vertices.*
import org.barlom.presentation.client.messages.Message
import org.barlom.presentation.client.messages.ModelActionMessage
import org.barlom.presentation.client.messages.UiActionMessage
import org.barlom.presentation.client.state.rightpanels.RelatedElementsPanelState
import org.barlom.presentation.client.views.listitems.viewListItem
import org.barlom.presentation.client.views.listitems.viewListItemIcon
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

/**
 * Builds the right panel with lists of elements related to the focused element.
 */
fun viewRelatedElements(
    builder: KatyDomFlowContentBuilder<Message>,
    relatedElementsPanelState: RelatedElementsPanelState,
    focusedElement: AbstractNamedElement
) = katyDomComponent(builder) {

    form("#related-elements-form.form--related-elements", action = "javascript:void(0);") {

        when (focusedElement) {

            is Package            -> {
                viewPackageChildElements(this, focusedElement)
                viewPackageSuppliers(this, relatedElementsPanelState, focusedElement)
                viewPackageConsumers(this, focusedElement)
            }

            is VertexType         -> if (!focusedElement.isRoot) {
                viewVertexTypeChildElements(this, focusedElement)
            }

            is UndirectedEdgeType -> if (!focusedElement.isRoot) {
                viewUndirectedEdgeTypeChildElements(this, focusedElement)
            }

            is DirectedEdgeType   -> if (!focusedElement.isRoot) {
                viewDirectedEdgeTypeChildElements(this, focusedElement)
            }

        }

    }

}


/**
 * Shows lists of the different kinds of child elements in a directed edge type.
 */
private fun viewDirectedEdgeTypeChildElements(
    builder: KatyDomFlowContentBuilder<Message>,
    focusedElement: DirectedEdgeType
) = katyDomComponent(builder) {

    viewChildElements(
        this,
        focusedElement,
        AbstractEdgeType::attributeTypes,
        "Attribute Types:",
        "attribute-types",
        listOf(
            AddElementConfig("Add an attribute type") { et ->
                AbstractEdgeTypeActions.addAttributeType(et)
            }
        )
    )

    viewChildElements(
        this,
        focusedElement,
        DirectedEdgeType::subTypes,
        "Sub Types:",
        "sub-types",
        emptyList()
    )

}


/**
 * Shows lists of the different kinds of child elements in a package.
 */
private fun viewPackageChildElements(
    builder: KatyDomFlowContentBuilder<Message>,
    focusedElement: Package
) = katyDomComponent(builder) {

    viewChildElements(
        this,
        focusedElement,
        Package::childPackages,
        "Child Packages:",
        "child-packages",
        listOf(
            AddElementConfig("Add a child package") { pkg ->
                PackageActions.addPackage(pkg)
            }
        )
    )

    viewChildElements(
        this,
        focusedElement,
        Package::vertexTypes,
        "Vertex Types:",
        "vertex-types",
        listOf(
            AddElementConfig("Add a vertex type") { pkg ->
                PackageActions.addVertexType(pkg)
            }
        )
    )

    viewChildElements(
        this,
        focusedElement,
        Package::undirectedEdgeTypes,
        "Undirected Edge Types:",
        "undirected-edge-types",
        listOf(
            AddElementConfig("Add an undirected edge type") { pkg ->
                PackageActions.addUndirectedEdgeType(pkg)
            }
        )
    )

    viewChildElements(
        this,
        focusedElement,
        Package::directedEdgeTypes,
        "Directed Edge Types:",
        "directed-edge-types",
        listOf(
            AddElementConfig("Add a directed edge type") { pkg ->
                PackageActions.addDirectedEdgeType(pkg)
            }
        )
    )

    viewChildElements(
        this,
        focusedElement,
        Package::constrainedDataTypes,
        "Constrained Data Types:",
        "constrained-data-types",
        listOf(
            AddElementConfig("Add a constrained string data type") { pkg ->
                PackageActions.addConstrainedString(pkg)
            },
            AddElementConfig("Add a constrained 32-bit integer data type") { pkg ->
                PackageActions.addConstrainedInteger32(pkg)
            },
            AddElementConfig("Add a constrained 64-bit floating point data type") { pkg ->
                PackageActions.addConstrainedFloat64(pkg)
            },
            AddElementConfig("Add a constrained boolean data type") { pkg ->
                PackageActions.addConstrainedBoolean(pkg)
            },
            AddElementConfig("Add a constrained date/time data type") { pkg ->
                PackageActions.addConstrainedDateTime(pkg)
            },
            AddElementConfig("Add a constrained UUID data type") { pkg ->
                PackageActions.addConstrainedUuid(pkg)
            }
        )
    )

}


/**
 * Shows lists of the packages consuming a package.
 */
private fun viewPackageConsumers(
    builder: KatyDomFlowContentBuilder<Message>,
    focusedElement: Package
) = katyDomComponent(builder) {

    viewChildElements(
        this,
        focusedElement,
        Package::consumers,
        "Consumer Packages:",
        "consumer-packages",
        emptyList()
    )

}


/**
 * Shows a list of the supplier packages for a package.
 */
private fun viewPackageSuppliers(
    builder: KatyDomFlowContentBuilder<Message>,
    relatedElementsPanelState: RelatedElementsPanelState,
    focusedElement: Package
) = katyDomComponent(builder) {

    val newSupplierPackagePath = relatedElementsPanelState.newSupplierPackagePath

    viewConnectedElements(
        this,
        focusedElement,
        Package::suppliers,
        Package::findPotentialSuppliers,
        "Supplier Packages:",
        "supplier-packages",
        listOf(
            AddConnectionConfig(
                "Add a supplier package",
                newSupplierPackagePath
            ) { pkg ->
                listOf<Message>(
                    ModelActionMessage(
                        PackageActions.addPackageSupplier(pkg, newSupplierPackagePath)
                    ),
                    UiActionMessage { uiState ->
                        uiState.relatedElementsPanelState.newSupplierPackagePath = ""
                        "Blanked out supplier package path."
                    }
                )
            }
        )
    )

}


/**
 * Shows lists of the different kinds of child elements in an undirected edge type.
 */
private fun viewUndirectedEdgeTypeChildElements(
    builder: KatyDomFlowContentBuilder<Message>,
    focusedElement: UndirectedEdgeType
) = katyDomComponent(builder) {

    viewChildElements(
        this,
        focusedElement,
        AbstractEdgeType::attributeTypes,
        "Attribute Types:",
        "attribute-types",
        listOf(
            AddElementConfig("Add an attribute type") { et ->
                AbstractEdgeTypeActions.addAttributeType(et)
            }
        )
    )
    viewChildElements(
        this,
        focusedElement,
        UndirectedEdgeType::subTypes,
        "Sub Types:",
        "sub-types",
        emptyList()
    )

}


/**
 * Shows lists of the different kinds of child elements in a vertex type.
 */
private fun viewVertexTypeChildElements(
    builder: KatyDomFlowContentBuilder<Message>,
    focusedElement: VertexType
) = katyDomComponent(builder) {

    viewChildElements(
        this,
        focusedElement,
        VertexType::attributeTypes,
        "Attribute Types:",
        "attribute-types",
        listOf(
            AddElementConfig("Add an attribute type") { vt ->
                VertexTypeActions.addAttributeType(vt)
            }
        )
    )

    viewChildElements(
        this,
        focusedElement,
        VertexType::subTypes,
        "Sub Types:",
        "sub-types",
        emptyList()
    )

    viewChildElements(
        this,
        focusedElement,
        VertexType::connectingEdgeTypes,
        "Connecting Edge Types (Undirected):",
        "connecting-edge-types-undirected",
        emptyList()
    )

    viewChildElements(
        this,
        focusedElement,
        VertexType::connectingHeadEdgeTypes,
        "Connecting Edge Types (Head):",
        "connecting-edge-types-head",
        emptyList()
    )

    viewChildElements(
        this,
        focusedElement,
        VertexType::connectingTailEdgeTypes,
        "Connecting Edge Types (Tail):",
        "connecting-edge-types-tail",
        emptyList()
    )

}


data class AddElementConfig<in Parent>(
    val label: String,
    val action: (Parent) -> ModelAction
)

/**
 * Shows a list of child elements for given parent.
 */
private inline fun <Parent, reified Child : AbstractNamedElement> viewChildElements(
    builder: KatyDomFlowContentBuilder<Message>,
    parent: Parent,
    noinline getChildElements: Parent.() -> List<Child>,
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

                viewListItem(this, child)

            }

        }

        // Show links for creating new child elements.
        for (addButton in addButtons) {

            li(".tree-item--not-focused", key = addButton.label) {

                onclick {
                    listOf(ModelActionMessage(addButton.action(parent)))
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
    val messages: (Parent) -> Iterable<Message>
)

/**
 * Shows a list of connected elements for given element.
 */
private inline fun <Element, reified ConnectedElement : AbstractNamedElement> viewConnectedElements(
    builder: KatyDomFlowContentBuilder<Message>,
    parent: Element,
    noinline getConnectedElements: Element.() -> List<ConnectedElement>,
    noinline getPotentialConnectedElements: Element.() -> List<ConnectedElement>,
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

                viewListItem(this, child)

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
                            addButton.messages(parent)
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

                                    listOf<Message>(UiActionMessage { uiState ->
                                        uiState.relatedElementsPanelState.newSupplierPackagePath = newValue
                                        "Entered new supplier package path '$newValue'."
                                    })

                                }
                                else {
                                    emptyList()
                                }

                            }

                        }

                    }

                }

            }

        }

    }

}

