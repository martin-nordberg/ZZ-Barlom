//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.concretenodes.forms.*
import org.katydom.concretenodes.text.KatyDomA
import org.katydom.concretenodes.text.KatyDomBr
import org.katydom.concretenodes.text.KatyDomSpan
import org.katydom.concretenodes.text.KatyDomText
import org.katydom.types.*

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Virtual DOM builder for the case of HTML "phrasing content".
 */
@Suppress("unused")
class KatyDomPhrasingContentBuilder(

    /** The element whose content is being built. */
    private val element: KatyDomHtmlElement,

    /** Restrictions on content enforced at run time. */
    internal val contentRestrictions: KatyDomContentRestrictions = KatyDomContentRestrictions()

) : KatyDomElementContentBuilder(element) {

    /**
     * Adds an a element with its attributes as the next child of the element under construction.
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
        defineContent: KatyDomPhrasingContentBuilder.() -> Unit
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
                tabindex, title, translate,defineAttributes)

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

////

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no anchor element or interactive content allowed.
     */
    internal fun withAnchorInteractiveContentNotAllowed(element: KatyDomHtmlElement) : KatyDomPhrasingContentBuilder {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions.withAnchorInteractiveContentNotAllowed()
        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no interactive content allowed.
     */
    internal fun withInteractiveContentNotAllowed(element: KatyDomHtmlElement) : KatyDomPhrasingContentBuilder {
        return KatyDomPhrasingContentBuilder(
            element,
            contentRestrictions.withInteractiveContentNotAllowed()
        )
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder plus no label element allowed.
     */
    internal fun withLabelNotAllowed(element: KatyDomHtmlElement) : KatyDomPhrasingContentBuilder {
        return KatyDomPhrasingContentBuilder(element, contentRestrictions.withLabelNotAllowed())
    }

    /**
     * Creates a new content builder for the given child [element] that has the same restrictions
     * as this builder.
     */
    internal fun withNoAddedRestrictions(element: KatyDomHtmlElement) : KatyDomPhrasingContentBuilder {
        return KatyDomPhrasingContentBuilder(element, contentRestrictions)
    }


}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

