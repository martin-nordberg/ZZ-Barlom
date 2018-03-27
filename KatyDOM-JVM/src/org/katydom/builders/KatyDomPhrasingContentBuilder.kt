//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.forms.*
import org.katydom.concretenodes.grouping.KatyDomDataList
import org.katydom.concretenodes.text.*
import org.katydom.types.*

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM builder for the case of HTML "phrasing content".
 */
@Suppress("unused")
open class KatyDomPhrasingContentBuilder<Message>(

    /** The element whose content is being built. */
    element: KatyDomHtmlElement<Message>,

    /** Restrictions on content enforced at run time. */
    internal val contentRestrictions: KatyDomContentRestrictions = KatyDomContentRestrictions(),

    /** Dispatcher of event handling results for when we want event handling to be reactive or Elm-like. */
    dispatchMessages: (messages: Iterable<Message>) -> Unit

) : KatyDomAttributesContentBuilder<Message>(element, dispatchMessages) {

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
        defineContent: KatyDomPhrasingContentBuilder<Message>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomA(this, selector, key, accesskey, contenteditable, dir, download, hidden, href, hreflang, lang,
                     rel, rev, spellcheck, style, tabindex, target, title, translate, type, defineContent)
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineContent: KatyDomPhrasingContentBuilder<Message>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomButton(this, selector, key, accesskey, autofocus, contenteditable, dir, disabled, form, formaction,
                          formenctype, formmethod, formnovalidate, formtarget, hidden, lang, menu,
                          name, spellcheck, style, tabindex, title, translate, type, value, defineContent)
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
     * Adds a datalist element with any global attributes as the next child of the element under construction.
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
        defineContent: KatyDomOptionContentBuilder<Message>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomDataList(this, selector, key, accesskey, contenteditable, dir, hidden, lang, spellcheck, style,
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineAttributes: KatyDomAttributesContentBuilder<Message>.() -> Unit
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
        defineContent: KatyDomPhrasingContentBuilder<Message>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomLabel(this, selector, key, accesskey, contenteditable, dir, `for`, hidden, lang, spellcheck,
                         style, tabindex, title, translate, defineContent)
        )
    }

    /**
     * Adds a select element with any global attributes as the next child of the element under construction.
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
        defineContent: KatyDomOptionContentBuilder<Message>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomSelect(this, selector, key, accesskey, autofocus, contenteditable, dir, disabled, form,
                          hidden, lang, multiple, name, required, size, spellcheck, style,
                          tabindex, title, translate, value, defineContent)
        )
    }

    /**
     * Adds a span element with any global attributes as the next child of the element under construction.
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
        defineContent: KatyDomPhrasingContentBuilder<Message>.() -> Unit
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
        defineContent: KatyDomTextContentBuilder<Message>.() -> Unit
    ) {
        element.addChildNode(
            KatyDomTextArea(this, selector, key, accesskey, autocomplete, autofocus, cols, contenteditable, dir,
                            dirname,
                            disabled, form, hidden, inputmode, lang, maxlength, minlength, name,
                            placeholder, readonly, required, rows, spellcheck, style, tabindex, title, translate,
                            wrap, defineContent)
        )
    }

////

    /**
     * Creates a new attributes content builder for the given child [element].
     */
    internal fun attributesContent(element: KatyDomHtmlElement<Message>): KatyDomAttributesContentBuilder<Message> {
        return KatyDomAttributesContentBuilder(element, dispatchMessages)
    }

    /**
     * Creates a new option content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun optionContent(element: KatyDomHtmlElement<Message>): KatyDomOptionContentBuilder<Message> {
        return KatyDomOptionContentBuilder(element, contentRestrictions, dispatchMessages)
    }

    /**
     * Creates a new text content builder for the given child [element].
     */
    internal fun textContent(element: KatyDomHtmlElement<Message>): KatyDomTextContentBuilder<Message> {
        return KatyDomTextContentBuilder(element, dispatchMessages)
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no anchor element or interactive content allowed.
     */
    internal fun withAnchorInteractiveContentNotAllowed(
        element: KatyDomHtmlElement<Message>): KatyDomPhrasingContentBuilder<Message> {
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
        element: KatyDomHtmlElement<Message>): KatyDomPhrasingContentBuilder<Message> {
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
    internal fun withLabelNotAllowed(element: KatyDomHtmlElement<Message>): KatyDomPhrasingContentBuilder<Message> {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions.withLabelNotAllowed(),
            dispatchMessages
        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal open fun withNoAddedRestrictions(
        element: KatyDomHtmlElement<Message>): KatyDomPhrasingContentBuilder<Message> {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions,
            dispatchMessages
        )
    }


}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

