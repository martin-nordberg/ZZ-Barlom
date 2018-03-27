//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.api

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomListItemContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.concretenodes.application.KatyDomAppPseudoNode
import org.katydom.lifecycle.KatyDomLifecycleImpl

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Constructs an object that orchestrates the main loop of the virtual DOM build and patch sequence.
 */
fun <Message> makeKatyDomLifecycle(/*TODO: options*/): KatyDomLifecycle<Message> {
    return KatyDomLifecycleImpl()
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Primary entry point for building a virtual DOM tree.
 * @param defineContent function that builds one root DOM node and its contents.
 * @return the root DOM node after it has been built by the provided function.
 */
fun <Message> katyDom(
    dispatchMessages: (Iterable<Message>) -> Unit = {},
    defineContent: KatyDomFlowContentBuilder<Message>.() -> Unit
): KatyDomHtmlElement<Message> {
    val pseudoParentElement = KatyDomAppPseudoNode<Message>()
    pseudoParentElement.fill(dispatchMessages = dispatchMessages, defineContent = defineContent)
    return pseudoParentElement.soleChildNode as KatyDomHtmlElement<Message>
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Helper function for defining a component that builds one or more child DOM nodes. This helper is for HTML that is
 * generally available anywhere in the tree (so called "flow content").
 * @param defineContent function that defines the one or more nodes of the component.
 * @return a function that builds the nodes as part of a larger tree.
 */
fun <Message> katyDomComponent(builder: KatyDomFlowContentBuilder<Message>,
                               defineContent: KatyDomFlowContentBuilder<Message>.() -> Unit) {
    return defineContent(builder)
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Helper function for defining a component consisting of one or more list item elements.
 * @param defineContent the function defining one or more <li> elements.
 * @return a function that builds the nodes as part of a larger tree.
 */
fun <Message> katyDomListItemComponent(builder: KatyDomListItemContentBuilder<Message>,
                                       defineContent: KatyDomListItemContentBuilder<Message>.() -> Unit) {
    return defineContent(builder)
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Helper function for defining a component consisting of phrasing content.
 * @param defineContent the function defining one or more elements.
 * @return a function that builds the nodes as part of a larger tree.
 */
fun <Message> katyDomPhrasingComponent(builder: KatyDomPhrasingContentBuilder<Message>,
                                       defineContent: KatyDomPhrasingContentBuilder<Message>.() -> Unit) {
    return defineContent(builder)
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
