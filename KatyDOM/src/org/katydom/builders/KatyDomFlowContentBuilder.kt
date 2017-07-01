//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.forms.*
import org.katydom.concretenodes.grouping.*
import org.katydom.concretenodes.sections.*
import org.katydom.concretenodes.text.KatyDomA
import org.katydom.concretenodes.text.KatyDomBr
import org.katydom.concretenodes.text.KatyDomSpan
import org.katydom.concretenodes.text.KatyDomText
import org.katydom.types.*

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM builder for the normal case of HTML "flow content".
 *
 * Typical global attribute parameters for builder methods:
 *  selector the "selector" for the element, e.g. "#myid.my-class.my-other-class".
 *  key a non-DOM key for this KatyDOM element that is unique among all the siblings of this element.
 *  accesskey a string specifying the HTML accesskey value.
 *  contenteditable whether the element has editable content.
 *  dir the left-to-right direction of text inside this element.
 *  hidden true if the element is to be hidden.
 *  lang the language of text within this element.
 *  spellcheck whether the element is subject to spell checking.
 *  style a string containing CSS for this element.
 *  tabindex the tab index for the element.
 *  title a tool tip for the element.
 *  translate whether to translate text within this element.
 *  defineContent a DSL-style lambda that builds the child nodes of the new element.
 */
@Suppress("unused")
class KatyDomFlowContentBuilder(

    /** The element whose content is being built. */
    private val element: KatyDomHtmlElement,

    /** Restrictions on content enforced at run time. */
    internal val contentRestrictions: KatyDomContentRestrictions = KatyDomContentRestrictions()

) : KatyDomElementContentBuilder(element) {

    /**
     * Adds an a element with any global attributes as the next child of the element under construction.
     */
    fun a(
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
    ) {
        element.addChildNode(
            KatyDomA(this, selector, key, accesskey, contenteditable, dir, download, hidden, href, hreflang, lang,
                     rel, rev, spellcheck, style, tabindex, target, title, translate, type, defineContent)
        )
    }

    /**
     * Adds an article element with any global attributes as the next child of the element under construction.
     */
    fun article(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomArticle(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                           tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an aside element with any global attributes as the next child of the element under construction.
     */
    fun aside(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomAside(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                             tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a br element as the next child of the element under construction.
     * @param defineAttributes a DSL-style lambda that adds any nonstandard attributes to the new element.
     */
    fun br(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomBr(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                      tabindex, title, translate, defineAttributes)

        )
    }

    /**
     * Adds a button element with given attributes as the next child of the element under construction.
     */
    fun button(
        selector: String?,
        key: String?,
        accesskey: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        formaction: String?,
        formenctype: EFormEncodingType?,
        formmethod: EFormSubmissionMethod?,
        formnovalidate: Boolean?,
        formtarget: String?,
        hidden: Boolean?,
        lang: String?,
        menu: String?,
        name: String?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        type: EButtonType?,
        value: String?,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomButton(this, selector, key, accesskey, autofocus, contenteditable, dir, disabled, form, formaction,
                formenctype, formmethod, formnovalidate, formtarget, hidden, lang, menu,
                name, spellcheck, style, tabindex, title, translate, type, value, defineContent)
        )
    }

    /**
     * Adds a div element with any global attributes as the next child of the element under construction.
     */
    fun div(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomDiv(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                           tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an footer element with any global attributes as the next child of the element under construction.
     */
    fun footer(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomFooter(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                              tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a form element with its attributes as the next child of the element under construction.
     */
    fun form(
        selector: String? = null,
        key: String? = null,
        acceptCharset: String?,
        accesskey: String?,
        action: String?,
        autocomplete: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        enctype: EFormEncodingType?,
        hidden: Boolean?,
        lang: String?,
        method: EFormSubmissionMethod?,
        name: String?,
        novalidate: Boolean?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        target: String?,
        title: String?,
        translate: Boolean?,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomForm(this, selector, key, acceptCharset, accesskey, action, autocomplete, contenteditable, dir,
                            enctype, hidden, lang, method, name, novalidate, spellcheck, style, tabindex, title,
                            target, translate, defineContent)
        )
    }

    /**
     * Adds a first level heading element with any global attributes as the next child of the element under construction.
     */
    fun h1(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH1(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a second level heading element with any global attributes as the next child of the element under construction.
     */
    fun h2(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH2(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a third level heading element with any global attributes as the next child of the element under construction.
     */
    fun h3(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH3(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a fourth level heading element with any global attributes as the next child of the element under construction.
     */
    fun h4(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH4(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a fifth level heading element with any global attributes as the next child of the element under construction.
     */
    fun h5(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH5(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a sixth level heading element with any global attributes as the next child of the element under construction.
     */
    fun h6(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomH6(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a header element with any global attributes as the next child of the element under construction.
     */
    fun header(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomHeader(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                              tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an hr element as the next child of the element under construction.
     * @param defineAttributes a DSL-style lambda that adds any nonstandard attributes to the new element.
     */
    fun hr(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomHr(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                      tabindex, title, translate, defineAttributes)

        )
    }

    /**
     * Adds an input type="button" element with given attributes as the next child of the element under construction.
     */
    fun inputButton(
        selector: String?,
        key: String?,
        accesskey: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        name: String?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputButton(this, selector, key, accesskey, contenteditable, dir, disabled, form, hidden, lang,
                name, spellcheck, style, tabindex, title, translate, value, defineAttributes)
        )
    }

    /**
     * Adds an input type="checkbox" element with given attributes as the next child of the element under construction.
     */
    fun inputCheckbox(
        selector: String?,
        key: String?,
        accesskey: String?,
        autofocus: Boolean?,
        checked: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        name: String?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputCheckbox(this, selector, key, accesskey, autofocus, checked, contenteditable, dir,
                disabled, form, hidden, lang, name, required,
                spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="color" element with given attributes as the next child of the element under construction.
     */
    fun inputColor(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        name: String?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputColor(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, name,
                spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="date" element with given attributes as the next child of the element under construction.
     */
    fun inputDate(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: String?,
        min: String?,
        name: String?,
        readonly: Boolean?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputDate(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, max, min, name,
                readonly, required, spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="datetime-local" element with given attributes as the next child of the element under construction.
     */
    fun inputDateTimeLocal(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: String?,
        min: String?,
        name: String?,
        readonly: Boolean?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputDateTimeLocal(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, max, min, name,
                readonly, required, spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="email" element with given attributes as the next child of the element under construction.
     */
    fun inputEmail(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        maxlength: Int?,
        minlength: Int?,
        multiple: Boolean?,
        name: String?,
        pattern: String?,
        placeholder: String?,
        readonly: Boolean?,
        required: Boolean?,
        size: Int?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputEmail(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, maxlength, minlength, multiple, name, pattern,
                placeholder, readonly, required, size, spellcheck, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="file" element with given attributes as the next child of the element under construction.
     */
    fun inputFile(
        selector: String?,
        key: String?,
        accept: String?,
        accesskey: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        multiple: Boolean?,
        name: String?,
        required: Boolean?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputFile(this, selector, key, accept, accesskey, autofocus, contenteditable, dir, disabled,
                form, hidden, lang, multiple, name, required, spellcheck, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="hidden" element with given attributes as the next child of the element under construction.
     */
    fun inputHidden(
        selector: String?,
        key: String?,
        accesskey: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        name: String?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputHidden(this, selector, key, accesskey, contenteditable, dir, disabled, form, hidden, lang,
                name, spellcheck, style, tabindex, title, translate, value, defineAttributes)
        )
    }

    /**
     * Adds an input type="image" element with given attributes as the next child of the element under construction.
     */
    fun inputImageButton(
        selector: String?,
        key: String?,
        accesskey: String?,
        alt: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        formaction: String?,
        formenctype: EFormEncodingType?,
        formmethod: EFormSubmissionMethod?,
        formnovalidate: Boolean?,
        formtarget: String?,
        height: Int?,
        hidden: Boolean?,
        lang: String?,
        name: String?,
        spellcheck: Boolean?,
        src: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        width: Int?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputImageButton(this, selector, key, accesskey, alt, contenteditable, dir, disabled, form, formaction,
                formenctype, formmethod, formnovalidate, formtarget, height, hidden, lang,
                name, spellcheck, src, style, tabindex, title, translate, value, width, defineAttributes)
        )
    }

    /**
     * Adds an input type="month" element with given attributes as the next child of the element under construction.
     */
    fun inputMonth(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: String?,
        min: String?,
        name: String?,
        readonly: Boolean?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputMonth(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, max, min, name,
                readonly, required, spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="number" (double) element with given attributes as the next child of the element under construction.
     */
    fun inputNumber(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: Double?,
        min: Double?,
        name: String?,
        placeholder: String?,
        readonly: Boolean?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: Double?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputNumber(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, max, min, name, placeholder,
                readonly, required, spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="number" (integer) element with given attributes as the next child of the element under construction.
     */
    fun inputNumber(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: Int?,
        min: Int?,
        name: String?,
        placeholder: String?,
        readonly: Boolean?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: Int?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputNumber(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, max, min, name, placeholder,
                readonly, required, spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="password" element with given attributes as the next child of the element under construction.
     */
    fun inputPassword(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        inputmode: EInputMode?,
        lang: String?,
        maxlength: Int?,
        minlength: Int?,
        name: String?,
        pattern: String?,
        placeholder: String?,
        readonly: Boolean?,
        required: Boolean?,
        size: Int?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputPassword(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, inputmode, lang, maxlength, minlength, name, pattern,
                placeholder, readonly, required, size, spellcheck, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="radio" element with given attributes as the next child of the element under construction.
     */
    fun inputRadioButton(
        selector: String?,
        key: String?,
        accesskey: String?,
        autofocus: Boolean?,
        checked: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        name: String?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputRadioButton(this, selector, key, accesskey, autofocus, checked, contenteditable, dir,
                disabled, form, hidden, lang, name, required,
                spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="range" (double) element with given attributes as the next child of the element under construction.
     */
    fun inputRange(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: Double?,
        min: Double?,
        name: String?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: Double?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputRange(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, max, min, name,
                spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="range" (integer) element with given attributes as the next child of the element under construction.
     */
    fun inputRange(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: Int?,
        min: Int?,
        name: String?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: Int?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputRange(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, max, min, name,
                spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="reset" element with given attributes as the next child of the element under construction.
     */
    fun inputResetButton(
        selector: String?,
        key: String?,
        accesskey: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        name: String?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputResetButton(this, selector, key, accesskey, contenteditable, dir, disabled, form, hidden, lang,
                name, spellcheck, style, tabindex, title, translate, value, defineAttributes)
        )
    }

    /**
     * Adds an input type="submit" element with given attributes as the next child of the element under construction.
     */
    fun inputSubmitButton(
        selector: String?,
        key: String?,
        accesskey: String?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        formaction: String?,
        formenctype: EFormEncodingType?,
        formmethod: EFormSubmissionMethod?,
        formnovalidate: Boolean?,
        formtarget: String?,
        hidden: Boolean?,
        lang: String?,
        name: String?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputSubmitButton(this, selector, key, accesskey, contenteditable, dir, disabled, form, formaction,
                formenctype, formmethod, formnovalidate, formtarget, hidden, lang,
                name, spellcheck, style, tabindex, title, translate, value, defineAttributes)
        )
    }

    /**
     * Adds an input type="tel" element with given attributes as the next child of the element under construction.
     */
    fun inputTelephone(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        maxlength: Int?,
        minlength: Int?,
        name: String?,
        pattern: String?,
        placeholder: String?,
        readonly: Boolean?,
        required: Boolean?,
        size: Int?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputTelephone(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                                  disabled, form, hidden, lang, list, maxlength, minlength, name, pattern,
                                  placeholder, readonly, required, size, spellcheck, style, tabindex, title, translate,
                                  value, defineAttributes)
        )
    }

    /**
     * Adds an input type="text" element with given attributes as the next child of the element under construction.
     */
    fun inputText(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        dirname: String?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        inputmode: EInputMode?,
        lang: String?,
        list: String?,
        maxlength: Int?,
        minlength: Int?,
        name: String?,
        pattern: String?,
        placeholder: String?,
        readonly: Boolean?,
        required: Boolean?,
        size: Int?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputText(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir, dirname,
                             disabled, form, hidden, inputmode, lang, list, maxlength, minlength, name, pattern,
                             placeholder, readonly, required, size, spellcheck, style, tabindex, title, translate,
                             value, defineAttributes)
        )
    }

    /**
     * Adds an input type="time" element with given attributes as the next child of the element under construction.
     */
    fun inputTime(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: String?,
        min: String?,
        name: String?,
        readonly: Boolean?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputTime(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, max, min, name,
                readonly, required, spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="url" element with given attributes as the next child of the element under construction.
     */
    fun inputUrl(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        maxlength: Int?,
        minlength: Int?,
        name: String?,
        pattern: String?,
        placeholder: String?,
        readonly: Boolean?,
        required: Boolean?,
        size: Int?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputUrl(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, maxlength, minlength, name, pattern,
                placeholder, readonly, required, size, spellcheck, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds an input type="week" element with given attributes as the next child of the element under construction.
     */
    fun inputWeek(
        selector: String?,
        key: String?,
        accesskey: String?,
        autocomplete: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        list: String?,
        max: String?,
        min: String?,
        name: String?,
        readonly: Boolean?,
        required: Boolean?,
        spellcheck: Boolean?,
        step: String?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineAttributes: KatyDomElementContentBuilder.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputWeek(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                disabled, form, hidden, lang, list, max, min, name,
                readonly, required, spellcheck, step, style, tabindex, title, translate,
                value, defineAttributes)
        )
    }

    /**
     * Adds a label element with its attributes as the next child of the element under construction.
     */
    fun label(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        `for`: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomLabel(this, selector, key, accesskey, contenteditable, dir, `for`, hidden, lang, spellcheck,
                             style, tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a main element with any global attributes as the next child of the element under construction.
     */
    fun main(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomMain(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                            tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a nav element with any global attributes as the next child of the element under construction.
     */
    fun nav(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomNav(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                           tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an ol element as the next child of the element under construction. Allows setting all global HTML
     * attributes.
     * @param reversed whether the list is to appear in reverse order.
     * @param start the numeric value for the first list item.
     * @param type the type of list counter to use.
     */
    fun ol(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        reversed: Boolean? = null,
        spellcheck: Boolean? = null,
        start: Int? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        type: EOrderedListType? = null,
        defineContent: KatyDomListItemContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomOl(this, selector, key, accesskey, contenteditable, dir, hidden, lang, reversed, spellcheck,
                          start, style, tabindex, title, translate, type, defineContent)
        )
    }

    /**
     * Adds a paragraph element with any global attributes as the next child of the element under construction.
     */
    fun p(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomP(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                         tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a section element with any global attributes as the next child of the element under construction.
     */
    fun section(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomFlowContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomSection(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                               tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a select element with any global attributes as the next child of the element under construction.
     */
    fun select(
        selector: String?,
        key: String?,
        accesskey: String?,
        autofocus: Boolean?,
        contenteditable: Boolean?,
        dir: EDirection?,
        disabled: Boolean?,
        form: String?,
        hidden: Boolean?,
        lang: String?,
        multiple: Boolean?,
        name: String?,
        required: Boolean?,
        size: Int?,
        spellcheck: Boolean?,
        style: String?,
        tabindex: Int?,
        title: String?,
        translate: Boolean?,
        value: String?,
        defineContent: KatyDomOptionContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomSelect(this, selector, key, accesskey, autofocus, contenteditable, dir, disabled, form,
                              hidden, lang, multiple, name, required, size, spellcheck, style,
                              tabindex, title, translate, value, defineContent)
        )
    }

    /**
     * Adds a span element with any global attributes as the next child of the element under construction.
     */
    fun span(
            selector: String? = null,
            key: String? = null,
            accesskey: String? = null,
            contenteditable: Boolean? = null,
            dir: EDirection? = null,
            hidden: Boolean? = null,
            lang: String? = null,
            spellcheck: Boolean? = null,
            style: String? = null,
            tabindex: Int? = null,
            title: String? = null,
            translate: Boolean? = null,
            defineContent: KatyDomPhrasingContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomSpan(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a text node as the next child of the element under construction.
     * @param nodeValue the text within the node.
     */
    fun text(nodeValue: String) {
        element.addChildNode(KatyDomText(nodeValue))
    }

    /**
     * Adds a new ul element as the next child of the element under construction. Allows setting all global HTML
     * attributes.
     */
    fun ul(
        selector: String? = null,
        key: String? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomListItemContentBuilder.() -> Unit
    ) {
        element.addChildNode(
                KatyDomUl(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

////

    /**
     * Creates a new attributes content builder for the given child [element].
     */
    internal fun attributesContent(element: KatyDomHtmlElement) : KatyDomElementContentBuilder {
        return KatyDomElementContentBuilder(element)
    }

    /**
     * Creates a new option content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun optionContent(element: KatyDomHtmlElement) : KatyDomOptionContentBuilder {
        return KatyDomOptionContentBuilder(element, contentRestrictions)
    }

    /**
     * Creates a new phrasing content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun phrasingContent(element: KatyDomHtmlElement) : KatyDomPhrasingContentBuilder {
        return KatyDomPhrasingContentBuilder(element, contentRestrictions)
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no footer, header or main elements allowed.
     */
    internal fun withFooterHeaderMainNotAllowed(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions.withFooterHeaderMainNotAllowed())
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no form element allowed.
     */
    internal fun withFormNotAllowed(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions.withFormNotAllowed())
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no label element allowed.
     */
    internal fun withLabelNotAllowed(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions.withLabelNotAllowed())
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no main element allowed.
     */
    internal fun withMainNotAllowed(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions.withMainNotAllowed())
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun withNoAddedRestrictions(element: KatyDomHtmlElement) : KatyDomFlowContentBuilder {
        return KatyDomFlowContentBuilder(element, contentRestrictions)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

