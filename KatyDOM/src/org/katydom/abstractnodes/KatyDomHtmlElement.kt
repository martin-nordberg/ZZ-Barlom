//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

import org.katydom.infrastructure.Cell
import org.katydom.infrastructure.MutableCell

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

abstract class KatyDomHtmlElement(
    selector: String?,
    key: String?,
    style: String?
) : KatyDomElement(selector,key) {

    // TODO: Needs to be String/String map of many styles
    val style: Cell<String>

////

    private class Scaffolding(
        style: String?
    ) {
        var style = MutableCell<String>(style)
    }

    private var _scaffolding: Scaffolding?

    init {
        val sc = Scaffolding(style)
        _scaffolding = sc
        this.style = sc.style

    }

    private val scaffolding: Scaffolding
        get() = _scaffolding ?: throw IllegalStateException("Attempted to modify a fully constructed KatyDomHtmlElement.")

    internal fun setStyle(style: String) {
        scaffolding.style.set(style)
    }

    override fun removeStillMoreScaffolding() {
        _scaffolding = null
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

