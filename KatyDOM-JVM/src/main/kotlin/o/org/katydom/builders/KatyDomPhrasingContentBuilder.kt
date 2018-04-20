//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.builders

import o.org.katydom.abstractnodes.KatyDomHtmlElement
import o.org.katydom.concretenodes.forms.*
import o.org.katydom.concretenodes.text.*
import o.org.katydom.types.*

//---------------------------------------------------------------------------------------------------------------------

/**
 * Virtual DOM builder for the case of HTML "phrasing content".
 */
@Suppress("unused")
open class KatyDomPhrasingContentBuilder<Msg>(

    /** The element whose content is being built. */
    element: KatyDomHtmlElement<Msg>,

    /** Restrictions on content enforced at run time. */
    internal val contentRestrictions: KatyDomContentRestrictions = KatyDomContentRestrictions(),

    /** Dispatcher of event handling results for when we want event handling to be reactive or Elm-like. */
    dispatchMessages: (messages: Iterable<Msg>) -> Unit

) : KatyDomAttributesContentBuilder<Msg>(element, dispatchMessages) {

    /**
     * Adds an a element with its attributes as the next child of the element under construction.
     */
    fun a(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomA(this, selector, key, accesskey, contenteditable, dir, download, hidden, href, hreflang, lang,
                     rel, rev, spellcheck, style, tabindex, target, title, translate, type, defineContent)
        )
    }

    /**
     * Adds an abbr element with its attributes as the next child of the element under construction.
     */
    fun abbr(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomAbbr(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, defineContent)
        )
    }

    /**
     * Creates a new attributes content builder for the given child [element].
     */
    internal fun attributesContent(element: KatyDomHtmlElement<Msg>): KatyDomAttributesContentBuilder<Msg> {
        return KatyDomAttributesContentBuilder(element, dispatchMessages)
    }

    /**
     * Adds a b element with its attributes as the next child of the element under construction.
     */
    fun b(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomB(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                     tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a bdi element with its attributes as the next child of the element under construction.
     */
    fun bdi(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomBdi(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                       tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a bdo element with its attributes as the next child of the element under construction.
     */
    fun bdo(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomBdo(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                       tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a br element as the next child of the element under construction.
     * @param defineAttributes a DSL-style lambda that adds any nonstandard attributes to the new element.
     */
    fun br(
        selector: String? = null,
        key: Any? = null,
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
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        formaction: String? = null,
        formenctype: EFormEncodingType? = null,
        formmethod: EFormSubmissionMethod? = null,
        formnovalidate: Boolean? = null,
        formtarget: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        menu: String? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        type: EButtonType? = EButtonType.BUTTON,
        value: String? = null,
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomButton(this, selector, key, accesskey, autofocus, contenteditable, dir, disabled, form, formaction,
                          formenctype, formmethod, formnovalidate, formtarget, hidden, lang, menu,
                          name, spellcheck, style, tabindex, title, translate, type, value, defineContent)
        )
    }

    /**
     * Adds a cite element with its attributes as the next child of the element under construction.
     */
    fun cite(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomCite(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a code element with its attributes as the next child of the element under construction.
     */
    fun code(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomCode(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a comment node as the next child of the element under construction.
     * @param nodeValue the text within the node.
     * @param key unique key for this comment within its parent node.
     */
    fun comment(nodeValue: String,
                key: Any? = null) {
        element.addChildNode(KatyDomComment(nodeValue, key))
    }

    /**
     * Adds a data element with its attributes as the next child of the element under construction.
     */
    fun data(
        selector: String? = null,
        key: Any? = null,
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
        value: String,
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomData(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, value, defineContent)
        )
    }

    /**
     * Adds a datalist element with its attributes as the next child of the element under construction.
     */
    fun datalist(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomOptionContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomDataList(this, selector, key, accesskey, contenteditable, dir,
                            hidden, lang, spellcheck, style,
                            tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a dfn element with its attributes as the next child of the element under construction.
     */
    fun dfn(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomDfn(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                       tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an em element with its attributes as the next child of the element under construction.
     */
    fun em(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomEm(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                      tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an i element with its attributes as the next child of the element under construction.
     */
    fun i(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomI(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                     tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an input type="button" element with given attributes as the next child of the element under construction.
     */
    fun inputButton(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autofocus: Boolean? = null,
        checked: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        name: String? = null,
        required: Boolean? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        max: String? = null,
        min: String? = null,
        name: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        max: String? = null,
        min: String? = null,
        name: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        maxlength: Int? = null,
        minlength: Int? = null,
        multiple: Boolean? = null,
        name: String? = null,
        pattern: String? = null,
        placeholder: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        size: Int? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accept: String? = null,
        accesskey: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        multiple: Boolean? = null,
        name: String? = null,
        required: Boolean? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputFile(this, selector, key, accept, accesskey, autofocus, contenteditable, dir, disabled,
                             form, hidden, lang, multiple, name, required, spellcheck, style, tabindex, title,
                             translate,
                             value, defineAttributes)
        )
    }

    /**
     * Adds an input type="hidden" element with given attributes as the next child of the element under construction.
     */
    fun inputHidden(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        alt: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        formaction: String? = null,
        formenctype: EFormEncodingType? = null,
        formmethod: EFormSubmissionMethod? = null,
        formnovalidate: Boolean? = null,
        formtarget: String? = null,
        height: Int? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        src: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        width: Int? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputImageButton(this, selector, key, accesskey, alt, contenteditable, dir, disabled, form,
                                    formaction,
                                    formenctype, formmethod, formnovalidate, formtarget, height, hidden, lang,
                                    name, spellcheck, src, style, tabindex, title, translate, value, width,
                                    defineAttributes)
        )
    }

    /**
     * Adds an input type="month" element with given attributes as the next child of the element under construction.
     */
    fun inputMonth(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        max: String? = null,
        min: String? = null,
        name: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputMonth(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                              disabled, form, hidden, lang, list, max, min, name,
                              readonly, required, spellcheck, step, style, tabindex, title, translate,
                              value, defineAttributes)
        )
    }

    /**
     * Adds an input type="number" element with given attributes as the next child of the element under construction.
     */
    fun <T : Number> inputNumber(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        max: T? = null,
        min: T? = null,
        name: String? = null,
        placeholder: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: T? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        inputmode: EInputMode? = null,
        lang: String? = null,
        maxlength: Int? = null,
        minlength: Int? = null,
        name: String? = null,
        pattern: String? = null,
        placeholder: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        size: Int? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autofocus: Boolean? = null,
        checked: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        name: String? = null,
        required: Boolean? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputRadioButton(this, selector, key, accesskey, autofocus, checked, contenteditable, dir,
                                    disabled, form, hidden, lang, name, required,
                                    spellcheck, step, style, tabindex, title, translate,
                                    value, defineAttributes)
        )
    }

    /**
     * Adds an input type="range" element with given attributes as the next child of the element under construction.
     */
    fun <T : Number> inputRange(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        max: T? = null,
        min: T? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: T? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputResetButton(this, selector, key, accesskey, contenteditable, dir, disabled, form, hidden, lang,
                                    name, spellcheck, style, tabindex, title, translate, value, defineAttributes)
        )
    }

    /**
     * Adds an input type="search" element with given attributes as the next child of the element under construction.
     */
    fun inputSearch(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        dirname: String? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        inputmode: EInputMode? = null,
        lang: String? = null,
        list: String? = null,
        maxlength: Int? = null,
        minlength: Int? = null,
        name: String? = null,
        pattern: String? = null,
        placeholder: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        size: Int? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputSearch(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir, dirname,
                               disabled, form, hidden, inputmode, lang, list, maxlength, minlength, name, pattern,
                               placeholder, readonly, required, size, spellcheck, style, tabindex, title, translate,
                               value, defineAttributes)
        )
    }

    /**
     * Adds an input type="submit" element with given attributes as the next child of the element under construction.
     */
    fun inputSubmitButton(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        formaction: String? = null,
        formenctype: EFormEncodingType? = null,
        formmethod: EFormSubmissionMethod? = null,
        formnovalidate: Boolean? = null,
        formtarget: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        maxlength: Int? = null,
        minlength: Int? = null,
        name: String? = null,
        pattern: String? = null,
        placeholder: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        size: Int? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        dirname: String? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        inputmode: EInputMode? = null,
        lang: String? = null,
        list: String? = null,
        maxlength: Int? = null,
        minlength: Int? = null,
        name: String? = null,
        pattern: String? = null,
        placeholder: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        size: Int? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        max: String? = null,
        min: String? = null,
        name: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        maxlength: Int? = null,
        minlength: Int? = null,
        name: String? = null,
        pattern: String? = null,
        placeholder: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        size: Int? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
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
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        list: String? = null,
        max: String? = null,
        min: String? = null,
        name: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        spellcheck: Boolean? = null,
        step: String? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomInputWeek(this, selector, key, accesskey, autocomplete, autofocus, contenteditable, dir,
                             disabled, form, hidden, lang, list, max, min, name,
                             readonly, required, spellcheck, step, style, tabindex, title, translate,
                             value, defineAttributes)
        )
    }

    /**
     * Adds a kbd element with its attributes as the next child of the element under construction.
     */
    fun kbd(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomKbd(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                       tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a label element with its attributes as the next child of the element under construction.
     */
    fun label(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomLabel(this, selector, key, accesskey, contenteditable, dir, `for`, hidden, lang, spellcheck,
                         style, tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a mark element with its attributes as the next child of the element under construction.
     */
    fun mark(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomMark(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a meter element with its attributes as the next child of the element under construction.
     */
    fun meter(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        high: String? = null,
        lang: String? = null,
        low: String? = null,
        max: String? = null,
        min: String? = null,
        optimum: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomMeter(this, selector, key, accesskey, contenteditable, dir, hidden, high, lang,
                         low, max, min, optimum, spellcheck, style, tabindex, title, translate, value, defineContent)
        )
    }

    /**
     * Creates a new option content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun optionContent(element: KatyDomHtmlElement<Msg>): KatyDomOptionContentBuilder<Msg> {
        return KatyDomOptionContentBuilder(element, contentRestrictions, dispatchMessages)
    }

    /**
     * Adds an output element with given attributes as the next child of the element under construction.
     */
    fun output(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        `for`: String? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        name: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomOutput(this, selector, key, accesskey, contenteditable, dir, disabled, `for`, form,
                          hidden, lang, name, spellcheck, style, tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a progress element with its attributes as the next child of the element under construction.
     */
    fun progress(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        max: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomProgress(this, selector, key, accesskey, contenteditable, dir, hidden, lang, max,
                            spellcheck, style, tabindex, title, translate, value, defineContent)
        )
    }

    /**
     * Adds a q element with its attributes as the next child of the element under construction.
     */
    fun q(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomQ(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                     tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds an s element with its attributes as the next child of the element under construction.
     */
    fun s(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomS(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                     tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a samp element with its attributes as the next child of the element under construction.
     */
    fun samp(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomSamp(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a select element with its attributes as the next child of the element under construction.
     */
    fun select(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autofocus: Boolean? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        multiple: Boolean? = null,
        name: String? = null,
        required: Boolean? = null,
        size: Int? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        value: String? = null,
        defineContent: KatyDomOptionContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomSelect(this, selector, key, accesskey, autofocus, contenteditable, dir, disabled, form,
                          hidden, lang, multiple, name, required, size, spellcheck, style,
                          tabindex, title, translate, value, defineContent)
        )
    }

    /**
     * Adds a small element with its attributes as the next child of the element under construction.
     */
    fun small(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomSmall(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                         tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a span element with its attributes as the next child of the element under construction.
     * @param selector the "selector" for the element, e.g. "#myid.my-class.my-other-class".
     * @param key a non-DOM key for this KatyDOM element that is unique among all the siblings of this element.
     * @param accesskey a string specifying the HTML accesskey value.
     * @param contenteditable whether the element has editable content.
     * @param dir the left-to-right direction of text inside this element.
     * @param hidden true if the element is to be hidden.
     * @param lang the language of text within this element.
     * @param spellcheck whether the element is subject to spell checking.
     * @param style a string containing CSS for this element.
     * @param tabindex the tab index for the element.
     * @param title a tool tip for the element.
     * @param translate whether to translate text within this element.
     * @param defineContent a DSL-style lambda that builds the child nodes of the new element.
     */
    fun span(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomSpan(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a strong element with its attributes as the next child of the element under construction.
     */
    fun strong(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomStrong(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                          tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a sub element with its attributes as the next child of the element under construction.
     */
    fun sub(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomSub(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                       tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a sup element with its attributes as the next child of the element under construction.
     */
    fun sup(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomSup(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
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
     * Adds a textarea element with given attributes as the next child of the element under construction.
     */
    fun textarea(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        autocomplete: String? = null,
        autofocus: Boolean? = null,
        cols: Int? = null,
        contenteditable: Boolean? = null,
        dir: EDirection? = null,
        dirname: String? = null,
        disabled: Boolean? = null,
        form: String? = null,
        hidden: Boolean? = null,
        inputmode: EInputMode? = null,
        lang: String? = null,
        maxlength: Int? = null,
        minlength: Int? = null,
        name: String? = null,
        placeholder: String? = null,
        readonly: Boolean? = null,
        required: Boolean? = null,
        rows: Int? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        wrap: EWrapType? = null,
        defineContent: KatyDomTextContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomTextArea(this, selector, key, accesskey, autocomplete, autofocus, cols, contenteditable, dir,
                            dirname,
                            disabled, form, hidden, inputmode, lang, maxlength, minlength, name,
                            placeholder, readonly, required, rows, spellcheck, style, tabindex, title, translate,
                            wrap, defineContent)
        )
    }

    /**
     * Creates a new text content builder for the given child [element].
     */
    internal fun textContent(element: KatyDomHtmlElement<Msg>): KatyDomTextContentBuilder<Msg> {
        return KatyDomTextContentBuilder(element, dispatchMessages)
    }

    /**
     * Adds a time element with text content as the next child of the element under construction.
     */
    fun time(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomTextContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomTime(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                        tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a time element with datetime attribute as the next child of the element under construction.
     */
    fun time(
        selector: String? = null,
        key: Any? = null,
        accesskey: String? = null,
        contenteditable: Boolean? = null,
        datetime: String,
        dir: EDirection? = null,
        hidden: Boolean? = null,
        lang: String? = null,
        spellcheck: Boolean? = null,
        style: String? = null,
        tabindex: Int? = null,
        title: String? = null,
        translate: Boolean? = null,
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomTime(this, selector, key, accesskey, contenteditable, datetime, dir, hidden, lang,
                        spellcheck, style, tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a u element with its attributes as the next child of the element under construction.
     */
    fun u(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomU(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                     tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a var element with its attributes as the next child of the element under construction.
     */
    fun `var`(
        selector: String? = null,
        key: Any? = null,
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
        defineContent: KatyDomPhrasingContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomVar(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                       tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a wbr element as the next child of the element under construction.
     * @param defineAttributes a DSL-style lambda that adds any nonstandard attributes to the new element.
     */
    fun wbr(
        selector: String? = null,
        key: Any? = null,
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
        defineAttributes: KatyDomAttributesContentBuilder<Msg>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomBr(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
                      tabindex, title, translate, defineAttributes)

        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no anchor element or interactive content allowed.
     */
    internal fun withAnchorInteractiveContentNotAllowed(
        element: KatyDomHtmlElement<Msg>): KatyDomPhrasingContentBuilder<Msg> {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions.withAnchorInteractiveContentNotAllowed(),
            dispatchMessages
        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no interactive content allowed.
     */
    internal fun withInteractiveContentNotAllowed(
        element: KatyDomHtmlElement<Msg>): KatyDomPhrasingContentBuilder<Msg> {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions.withInteractiveContentNotAllowed(),
            dispatchMessages
        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no label element allowed.
     */
    internal fun withLabelNotAllowed(element: KatyDomHtmlElement<Msg>): KatyDomPhrasingContentBuilder<Msg> {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions.withLabelNotAllowed(),
            dispatchMessages
        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no meter element allowed.
     */
    internal fun withMeterNotAllowed(element: KatyDomHtmlElement<Msg>): KatyDomPhrasingContentBuilder<Msg> {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions.withMeterNotAllowed(),
            dispatchMessages
        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal open fun withNoAddedRestrictions(
        element: KatyDomHtmlElement<Msg>): KatyDomPhrasingContentBuilder<Msg> {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions,
            dispatchMessages
        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no progress element allowed.
     */
    internal fun withProgressNotAllowed(element: KatyDomHtmlElement<Msg>): KatyDomPhrasingContentBuilder<Msg> {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions.withProgressNotAllowed(),
            dispatchMessages
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

