//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.abstractnodes

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

abstract class KatyDomHtmlElement(
    selector: String?,
    style: String?
) : KatyDomElement(selector) {

    // TODO: Needs to be String/String map of many styles
    val style: String?

    ////

    private class Scaffolding(
        style: String?
    ) {

        var style: String? = null

        init {
            this.style = style
        }

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
        scaffolding.style = style
    }

    override fun removeScaffolding3() {
        _scaffolding = null
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

