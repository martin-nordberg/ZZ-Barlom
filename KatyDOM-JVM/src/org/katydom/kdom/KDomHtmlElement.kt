//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//
package org.katydom.kdom

import org.w3c.dom.html.HTMLElement

/**
 * Implementation of DOM Document for generating HTML text for testing or server-side rendering.
 */
class KDomHtmlElement(
    _document: KDomDocument,
    _tagName: String
): KDomElement(_document, _tagName), HTMLElement {

    override fun setId(id: String?) {
        TODO("not yet needed")
    }

    override fun getId(): String {
        TODO("not yet needed")
    }

    override fun setTitle(title: String?) {
        TODO("not yet needed")
    }

    override fun getTitle(): String {
        TODO("not yet needed")
    }

    override fun setClassName(className: String?) {
        TODO("not yet needed")
    }

    override fun getLang(): String {
        TODO("not yet needed")
    }

    override fun getClassName(): String {
        TODO("not yet needed")
    }

    override fun setLang(lang: String?) {
        TODO("not yet needed")
    }

    override fun setDir(dir: String?) {
        TODO("not yet needed")
    }

    override fun getDir(): String {
        TODO("not yet needed")
    }

}
