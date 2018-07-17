//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package js.org.barlom.presentation.client.views.rightpanels.links

import o.org.barlom.domain.metamodel.api.actions.AbstractEdgeTypeActions
import o.org.barlom.domain.metamodel.api.actions.ModelAction
import o.org.barlom.domain.metamodel.api.actions.PackageActions
import o.org.barlom.domain.metamodel.api.actions.VertexTypeActions
import o.org.barlom.domain.metamodel.api.vertices.*
import x.org.barlom.infrastructure.uuids.Uuid
import x.org.barlom.infrastructure.uuids.makeUuid
import o.org.barlom.presentation.client.actions.GeneralActions
import o.org.barlom.presentation.client.actions.leftpanels.browse.BrowsePanelActions
import o.org.barlom.presentation.client.actions.rightpanels.links.RelatedElementsPanelAction
import o.org.barlom.presentation.client.actions.rightpanels.links.RelatedElementsPanelActions
import js.org.barlom.presentation.client.messages.AppActionMessage
import js.org.barlom.presentation.client.messages.Message
import js.org.barlom.presentation.client.messages.ModelActionMessage
import js.org.barlom.presentation.client.messages.leftpanels.browse.BrowsePanelActionMessage
import js.org.barlom.presentation.client.messages.rightpanels.links.RelatedElementsPanelActionMessage
import o.org.barlom.presentation.client.state.rightpanels.RelatedElementsPanelState
import js.org.barlom.presentation.client.views.listitems.viewListItem
import js.org.barlom.presentation.client.views.listitems.viewListItemIcon
import o.org.katydom.api.katyDomComponent
import o.org.katydom.builders.KatyDomFlowContentBuilder
import o.org.katydom.eventhandling.onblur
import o.org.katydom.eventhandling.onclick

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
            AddElementConfig("Add an attribute type") { et, id ->
                AbstractEdgeTypeActions.addAttributeType(et, id)
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
            AddElementConfig("Add a child package") { pkg, id ->
                PackageActions.addPackage(pkg,id)
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
            AddElementConfig("Add a vertex type") { pkg, id ->
                PackageActions.addVertexType(pkg,id)
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
            AddElementConfig("Add an undirected edge type") { pkg, id ->
                PackageActions.addUndirectedEdgeType(pkg,id)
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
            AddElementConfig("Add a directed edge type") { pkg, id ->
                PackageActions.addDirectedEdgeType(pkg,id)
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
            AddElementConfig("Add a constrained string data type") { pkg, id ->
                PackageActions.addConstrainedString(pkg, id)
            },
            AddElementConfig("Add a constrained 32-bit integer data type") { pkg, id ->
                PackageActions.addConstrainedInteger32(pkg, id)
            },
            AddElementConfig("Add a constrained 64-bit floating point data type") { pkg, id ->
                PackageActions.addConstrainedFloat64(pkg, id)
            },
            AddElementConfig("Add a constrained boolean data type") { pkg, id ->
                PackageActions.addConstrainedBoolean(pkg, id)
            },
            AddElementConfig("Add a constrained date/time data type") { pkg, id ->
                PackageActions.addConstrainedDateTime(pkg, id)
            },
            AddElementConfig("Add a constrained UUID data type") { pkg, id ->
                PackageActions.addConstrainedUuid(pkg, id)
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
                newSupplierPackagePath,
                RelatedElementsPanelActions::setSupplierPackagePath
            ) { pkg ->
                listOf<Message>(
                    ModelActionMessage(
                        PackageActions.addPackageSupplier(pkg, newSupplierPackagePath)
                    ),
                    RelatedElementsPanelActionMessage(
                        RelatedElementsPanelActions.resetSupplierPackagePath()
                    )
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
            AddElementConfig("Add an attribute type") { et, id ->
                AbstractEdgeTypeActions.addAttributeType(et, id)
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
            AddElementConfig("Add an attribute type") { vt, id ->
                VertexTypeActions.addAttributeType(vt, id)
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
    val action: (Parent, Uuid) -> ModelAction
)

/**
 * Shows a list of child elements for given parent.
 */
private inline fun <Parent: AbstractDocumentedElement, reified Child : AbstractNamedElement> viewChildElements(
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
                    val id = makeUuid()
                    listOf(
                        ModelActionMessage(addButton.action(parent,id)),
                        // TODO: get rid of dependencies below using something like: afterAddElement(id:Uuid)->listOf<Message>
                        BrowsePanelActionMessage(BrowsePanelActions.setExpanded(parent)),
                        AppActionMessage(GeneralActions.focusById(id))
                    )
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
    val setPath: (String) -> RelatedElementsPanelAction,
    val messages: (Parent) -> Iterable<Message>
)

/**
 * Shows a list of connected elements for a given [parent] element.
 */
private fun <Element, ConnectedElement : AbstractNamedElement> viewConnectedElements(
    builder: KatyDomFlowContentBuilder<Message>,
    parent: Element,
    getConnectedElements: Element.() -> List<ConnectedElement>,
    getPotentialConnectedElements: Element.() -> List<ConnectedElement>,
    label: String,
    name: String,
    addButtons: List<AddConnectionConfig<Element>>
) = katyDomComponent(builder) {

    // Label the list.
    label("#$name-label.c-label.o-form-element") {
        text(label)
    }

    ul("#$name-list.c-tree") {

        // Show the connected elements.
        for (child in parent.getConnectedElements()) {

            li(".tree-item--not-focused", key = child.id) {

                data("uuid", child.id.toString())

                viewListItem(this, child)

            }

        }

        // Show links for creating new connected elements.
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

                                    listOf<Message>(
                                        RelatedElementsPanelActionMessage(
                                            addButton.setPath(newValue)
                                        )
                                    )

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

