//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.text

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomFlowContentBuilder
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EAnchorHtmlLinkType
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an anchor <a> element.
 */
internal class KatyDomA : KatyDomHtmlElement {

    constructor(
        flowContent: KatyDomFlowContentBuilder,
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        download: String? = null,
        hidden: Boolean? = null,
        href: String? = null,
        hreflang: String? = null,
        lang: String? = null,
        rel: Iterable<EAnchorHtmlLinkType>? = null,
        rev: Iterable<EAnchorHtmlLinkType>? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        target: String? = null,
        title: String? = null,
        translate: Boolean? = null,
        type: String? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        check( flowContent.contentRestrictions.anchorTagAllowed ) { "Anchor tag not allowed here." }
        check( href==null || flowContent.contentRestrictions.interactiveContentAllowed ) { "Interactive content (anchor with href) not allowed here." }

        setAttributes(download, href, hreflang, rel, rev, target, type)

        flowContent.withNoAddedRestrictions(this).defineContent()
        this.freeze()
    }

    constructor(
        phrasingContent: KatyDomPhrasingContentBuilder,
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        download: String? = null,
        hidden: Boolean? = null,
        href: String? = null,
        hreflang: String? = null,
        lang: String? = null,
        rel: Iterable<EAnchorHtmlLinkType>? = null,
        rev: Iterable<EAnchorHtmlLinkType>? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        target: String? = null,
        title: String? = null,
        translate: Boolean? = null,
        type: String? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) : super(selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style, tabindex, title, translate) {

        check( phrasingContent.contentRestrictions.anchorTagAllowed ) { "Anchor tag not allowed here." }
        check( href==null || phrasingContent.contentRestrictions.interactiveContentAllowed ) { "Interactive content (anchor with href) not allowed here." }

        setAttributes(download, href, hreflang, rel, rev, target, type)

        phrasingContent.withNoAddedRestrictions(this).defineContent()
        this.freeze()
    }

    override val nodeName = "A"

    private fun setAttributes(
        download: String?,
        href: String?,
        hreflang: String?,
        rel: Iterable<EAnchorHtmlLinkType>?,
        rev: Iterable<EAnchorHtmlLinkType>?,
        target: String?,
        type: String?
    ) {

        this.setAttribute("download", download)
        this.setAttribute("href", href)
        this.setAttribute("hreflang", hreflang)

        if (rel != null) {
            this.setAttribute("rel", rel.map { r -> r.toHtmlString() }.joinToString(" "))
        }

        if (rev != null) {
            this.setAttribute("rev", rev.map { r -> r.toHtmlString() }.joinToString(" "))
        }

        this.setAttribute("target", target)
        this.setAttribute("type", type)

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
