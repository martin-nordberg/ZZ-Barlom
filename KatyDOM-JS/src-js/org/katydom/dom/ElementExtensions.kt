package org.katydom.dom

import org.w3c.dom.Element


fun Element.setAttributeAndProperty(key: String, value: String) {
    this.setAttribute(key, value)
    this.asDynamic()[key] = value
}
