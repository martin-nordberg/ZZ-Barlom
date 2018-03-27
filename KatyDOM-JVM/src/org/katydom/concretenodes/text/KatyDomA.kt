//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.concretenodes.text

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.builders.KatyDomPhrasingContentBuilder
import org.katydom.types.EAnchorHtmlLinkType
import org.katydom.types.EDirection

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual node for an anchor <a> element.
 */
internal class KatyDomA<Message>(
    phrasingContent: KatyDomPhrasingContentBuilder<Message>,
    selector: String?,
    key: Any?,
    accesskey: String?,
    contenteditable: Boolean?,
    dir: EDirection?,
    download: String?,
    hidden: Boolean?,
    href: String?,
    hreflang: String?,
    lang: String?,
    rel: Iterable<EAnchorHtmlLinkType>?,
    rev: Iterable<EAnchorHtmlLinkType>?,
    spellcheck: Boolean?,
    style: String?,
    tabindex: Int?,
    target: String?,
    title: String?,
    translate: Boolean?,
    type: String?,
    defineContent: KatyDomPhrasingContentBuilder<Message>.() -> Unit
) : KatyDomHtmlElement<Message>(selector, key, accesskey, contenteditable, dir,
                                hidden, lang, spellcheck, style, tabindex, title, translate) {

    override val nodeName = "A"

    init {
        phrasingContent.contentRestrictions.confirmAnchorAllowed()
        if( href!=null ) {
            phrasingContent.contentRestrictions.confirmInteractiveContentAllowed()
        }

        this.setAttribute("download", download)
        this.setAttribute("href", href)
        this.setAttribute("hreflang", hreflang)

        if (rel != null) {
            this.setAttribute("rel", rel.map(EAnchorHtmlLinkType::toHtmlString).joinToString(" "))
        }

        if (rev != null) {
            this.setAttribute("rev", rev.map(EAnchorHtmlLinkType::toHtmlString).joinToString(" "))
        }

        this.setAttribute("target", target)
        this.setAttribute("type", type)

        phrasingContent.withNoAddedRestrictions(this).defineContent()
        this.freeze()
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
