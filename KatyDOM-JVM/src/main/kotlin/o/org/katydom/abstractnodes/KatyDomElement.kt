//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.abstractnodes

import o.org.katydom.infrastructure.UnusedMap
import o.org.katydom.infrastructure.UnusedSet
import x.org.katydom.dom.Element
import x.org.katydom.dom.Node
import x.org.katydom.dom.setAttributeAndProperty

//---------------------------------------------------------------------------------------------------------------------

/**
 * Abstract class representing a KatyDom virtual element. Corresponds to DOM Element.
 * @param Msg the type of message returned by events from this element when an Elm-like architecture is in use.
 */
abstract class KatyDomElement<Msg> : KatyDomNode<Msg> {

    private constructor(
        selectorPieces: List<String>?,
        key: Any?
    ) : super(key ?: keyFromSelector(selectorPieces)) {

        if (selectorPieces != null && selectorPieces.isNotEmpty()) {

            if (selectorPieces[0].startsWith("#")) {
                setAttribute("id", selectorPieces[0].substring(1))
            }
            else if (selectorPieces[0].isEmpty()) {
                check(selectorPieces[0].isEmpty()) {
                    "Selector should start with '#' or '.'."
                }
            }

            classList.addAll(selectorPieces.subList(1, selectorPieces.size))

        }

    }

    /**
     * Constructs a new element with global attributes beyond ID and class.
     * @param selector The "selector" for the element, e.g. "#myid.my-class.my-other-class". The selector can combine
     *                 an optional element ID (starting with "#") with zero or more class names (each starting with
     *                 ".").
     * @param key a virtual DOM key for this KatyDOM element that is unique among all the siblings of this element.
     *            If key is null, the key is taken as the ID given in the [selector]. If there is no ID, then the key
     *            becomes the node name in base class KatyDomNode.
     * @param style a string containing CSS for this element (the style element of the eventual HTML element).
     * @param tabindex the tab index for the element.
     */
    constructor(
        selector: String?,
        key: Any?,
        style: String?,
        tabindex: Int?
    ) : this(selector?.split("."), key) {

        setAttribute("style", style)
        setNumberAttribute("tabindex", tabindex)

    }

    ////

    /** The attributes of this element, mapped from name to value. */
    private val attributes: MutableMap<String, String> = mutableMapOf()

    /** A list of classes for this element. */
    private var classList: MutableSet<String> = mutableSetOf()

    /** A list of the data-* properties of this element, keyed without the "data-" prefix. */
    private var dataset: MutableMap<String, String> = mutableMapOf()

    /** Static placeholders to replace attributes under construction when no longer needed. */
    private object Unused {
        val classList = UnusedSet<String>()
        val dataset = UnusedMap<String, String>()
    }

    ////

    /**
     * Adds a given class to this element.
     * @param className the name of the class to add.
     */
    internal fun addClass(className: String) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        classList.add(className)

    }

    /**
     * Adds multiple classes to this element.
     * @param classes a sequence of class names to add.
     */
    internal fun addClasses(classes: Iterable<String>) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        classList.addAll(classes)

    }

    final override fun establishAttributes(domElement: Node) {

        if (domElement !is Element) throw IllegalArgumentException("DOM node expected to be an element.")

        // Establish other attributes.
        for ((key, value) in attributes) {
            domElement.setAttribute(key, value)
        }

        if (false /*TODO: ...isInstrumented*/) {
            val debugAttr = nodeCount.toString() + ";" + this.key

            domElement.setAttribute("data-debug", debugAttr)
            nodeCount += 1
        }


    }

    final override fun freezeAttributes() {

        if (classList.isNotEmpty()) {

            val attrClasses = attributes["class"]
            if (attrClasses != null) {
                classList.addAll(attrClasses.split(" "))
            }
            attributes["class"] = classList.sorted().joinToString(" ")

        }

        for ((name, value) in dataset) {
            setAttribute("data-$name", value)
        }

        classList = Unused.classList
        dataset = Unused.dataset
    }

    final override fun patchAttributes(domElement: Node, priorElement: KatyDomNode<Msg>) {

        if (domElement !is Element) throw IllegalArgumentException("DOM node expected to be an element.")
        if (priorElement !is KatyDomElement) throw IllegalArgumentException("KatyDOM node expected to be element.")

        // Patch other attributes.
        for ((key, newValue) in attributes) {
            if (newValue != priorElement.attributes[key]) {
                domElement.setAttributeAndProperty(key, newValue)
            }
        }
        for ((key, _) in priorElement.attributes) {
            if (!attributes.contains(key)) {
                domElement.removeAttribute(key)
            }
        }

    }

    /**
     * Sets one attribute by name and value. Splits out specific attributes like class and id to their specific
     * handlers (with warnings).
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal fun setAttribute(name: String, value: String?) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        if (value == null) {
            attributes.remove(name)
        }
        else {
            attributes[name] = value
        }
    }

    /**
     * Sets multiple attributes provided as a mpa from name to value.
     * @param attributes the attribute name/value pairs to set.
     */
    internal fun setAttributes(attributes: Map<String, String>) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        for ((name, value) in attributes) {
            setAttribute(name, value)
        }

    }

    /**
     * Sets one boolean attribute by name and value. A boolean attribute has the value true if present or false if absent.
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal fun setBooleanAttribute(name: String, value: Boolean?) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        if (value != null && value) {
            attributes[name] = ""
        }
        else {
            attributes.remove(name)
        }

    }

    /**
     * Sets one data- attribute.
     * @param name the name of the attribute without its "data-" prefix.
     * @param value the value of the attribute.
     */
    internal fun setData(name: String, value: String) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        if (name.startsWith("data-")) {
            // TODO: Warning: "data-" prefix not required for dataset additions.
            dataset[name.substring(5)] = value
        }
        else {
            dataset[name] = value
        }

    }

    /**
     * Sets multiple data attributes at once.
     * @param dataset a collection of name/value pairs of "data-" attributes.
     */
    internal fun setData(dataset: Map<String, String>) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        for ((name, value) in dataset) {
            setData(name, value)
        }

    }

    /**
     * Sets one numeric attribute by name and value.
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal fun setNumberAttribute(name: String, value: Number?) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        if (value != null) {
            attributes[name] = value.toString()
        }
        else {
            attributes.remove(name)
        }

    }

    /**
     * Sets the style attribute for this element. TODO: addStyle( cssKey, cssValue )
     */
    internal fun setStyle(style: String?) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        setAttribute("style", style)

    }

    /**
     * Sets one true/false attribute by name and value. A true/false attribute has the value "true" or "false".
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal fun setTrueFalseAttribute(name: String, value: Boolean?) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        if (value != null) {
            if (value) {
                attributes[name] = "true"
            }
            else {
                attributes[name] = "false"
            }
        }
        else {
            attributes.remove(name)
        }

    }

    /**
     * Sets one yes/no attribute by name and value. A boolean attribute has the value "yes" or "no".
     * @param name the name of the attribute to set.
     * @param value the value of the attribute.
     */
    internal fun setYesNoAttribute(name: String, value: Boolean?) {

        check(isAddingAttributes) {
            "Cannot modify KatyDOM attributes after beginning to add event handlers or child nodes."
        }

        if (value != null) {
            if (value) {
                attributes[name] = "yes"
            }
            else {
                attributes[name] = "no"
            }
        }
        else {
            attributes.remove(name)
        }

    }

    /**
     * Converts this element to a string for debugging purposes. The output looks like an HTML opening tag:
     * `<sometag attr1="value1" attr2="value2">`
     */
    override fun toString(): String {
        var result = "<" + nodeName.toLowerCase()
        attributes.forEach { entry ->
            result += " " + entry.key + "=\"" + entry.value + "\""
        }
        return "$result>"
    }

    ////

    private companion object {

        /**
         * Computes the key as the ID when there is no key.
         */
        fun keyFromSelector(selectorPieces: List<String>?): String? {
            if (selectorPieces != null && selectorPieces.isNotEmpty() && selectorPieces[0].startsWith("#")) {
                return selectorPieces[0].substring(1)
            }
            return null
        }

        var nodeCount = 1

    }

}

//---------------------------------------------------------------------------------------------------------------------
