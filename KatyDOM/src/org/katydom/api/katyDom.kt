//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.api

import org.katydom.abstractnodes.KatyDomNode
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomListItemContentBuilder
import org.katydom.concretenodes.KatyDomDiv
import org.katydom.lifecycle.KatyDomLifecycleImpl

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Constructs an object that orchestrates the main loop of the virtual DOM build and patch sequence.
 */
fun makeKatyDomLifecycle(/*TODO: options*/): KatyDomLifecycle {
    return KatyDomLifecycleImpl()
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Primary entry point for building a virtual DOM tree.
 * @param fillChildNodes function that builds one root DOM node and its contents.
 * @return the root DOM node after it has been built by the provided function.
 */
fun katyDom(fillChildNodes: KatyDomFlowContentBuilder.() -> Unit): KatyDomNode {
    val pseudoParentElement = KatyDomDiv(null, null, null)
    KatyDomFlowContentBuilder(pseudoParentElement).fillChildNodes()
    return pseudoParentElement.childNodes.first()
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Helper function for defining a component that builds one or more child DOM nodes. This helper is for HTML that is
 * generally available anywhere in the tree (so called "flow content").
 * @param fillChildNodes function that defines the one or more nodes of the component.
 * @return a function that builds the nodes as part of a larger tree.
 */
fun katyDomComponent(fillChildNodes: KatyDomFlowContentBuilder.() -> Unit)
    : KatyDomFlowContentBuilder.() -> Unit {
    return fillChildNodes
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Helper function for defining a component consisting of one or more list item elements.
 * @param fillChildNodes the function defining one or more <li> elements.
 * @return a function that builds the nodes as part of a larger tree.
 */
fun katyDomListItemComponent(fillChildNodes: KatyDomListItemContentBuilder.() -> Unit)
    : KatyDomListItemContentBuilder.() -> Unit {
    return fillChildNodes
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
