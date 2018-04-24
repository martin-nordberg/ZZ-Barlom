//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.katydom.builders

//---------------------------------------------------------------------------------------------------------------------

/**
 * Set of restrictions on content. E.g. a header cannot contain a header or footer; a form cannot be nested.
 */
class KatyDomContentRestrictions(
    private val anchorAllowed: Boolean,
    private var figCaptionProhibited: Boolean,
    private val footerAllowed: Boolean,
    private val formAllowed: Boolean,
    private val headerAllowed: Boolean,
    private val interactiveContentAllowed: Boolean,
    private val labelAllowed: Boolean,
    private var legendProhibited: Boolean,
    private val mainAllowed: Boolean,
    private val meterAllowed: Boolean,
    private val optionGroupAllowed: Boolean,
    private val progressAllowed: Boolean
) {

    constructor()
        : this(
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true
    )

    constructor(
        original: KatyDomContentRestrictions,
        anchorAllowed: Boolean = true,
        figCaptionProhibited: Boolean = true,
        footerAllowed: Boolean = true,
        formAllowed: Boolean = true,
        headerAllowed: Boolean = true,
        interactiveContentAllowed: Boolean = true,
        labelAllowed: Boolean = true,
        legendProhibited: Boolean = false,
        mainAllowed: Boolean = true,
        meterAllowed: Boolean = true,
        optionGroupAllowed: Boolean = true,
        progressAllowed: Boolean = true
    ) : this(
        original.anchorAllowed && anchorAllowed,
        original.figCaptionProhibited && figCaptionProhibited,
        original.footerAllowed && footerAllowed,
        original.formAllowed && formAllowed,
        original.headerAllowed && headerAllowed,
        original.interactiveContentAllowed && interactiveContentAllowed,
        original.labelAllowed && labelAllowed,
        original.legendProhibited && legendProhibited,
        original.mainAllowed && mainAllowed,
        original.meterAllowed && meterAllowed,
        original.optionGroupAllowed && optionGroupAllowed,
        original.progressAllowed && progressAllowed
    )

    ////

    fun confirmAnchorAllowed() {
        check(anchorAllowed) { "Element type <a> not allowed here" }
    }

    fun confirmFigCaptionAllowedThenDisallow() {
        check(!figCaptionProhibited) { "Element type <figcaption> not allowed here." }
        figCaptionProhibited = true
    }

    fun confirmFooterAllowed() {
        check(footerAllowed) { "Element type <footer> not allowed here." }
    }

    fun confirmFormAllowed() {
        check(formAllowed) { "Element type <form> not allowed here. (Form elements cannot be nested.)" }
    }

    fun confirmHeaderAllowed() {
        check(headerAllowed) { "Element type <header> not allowed here." }
    }

    fun confirmInteractiveContentAllowed() {
        check(interactiveContentAllowed) { "Interactive content not allowed here." }
    }

    fun confirmLabelAllowed() {
        check(labelAllowed) { "Element type <label> not allowed here." }
    }

    fun confirmLegendAllowedThenDisallow() {
        check(!legendProhibited) { "Element type <legend> not allowed here." }
        legendProhibited = true
    }

    fun confirmMainAllowed() {
        check(mainAllowed) { "Element type <main> not allowed here." }
    }

    fun confirmMeterAllowed() {
        check(mainAllowed) { "Element type <meter> not allowed here." }
    }

    fun confirmOptionGroupAllowed() {
        check(optionGroupAllowed) { "Element type <optgroup> not allowed here." }
    }

    fun confirmProgressAllowed() {
        check(progressAllowed) { "Element type <progress> not allowed here." }
    }

    fun withAnchorInteractiveContentNotAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            this,
            anchorAllowed = false,
            interactiveContentAllowed = false
        )
    }

    fun withFigCaptionAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            this,
            figCaptionProhibited = false
        )
    }

    fun withFooterHeaderMainNotAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            this,
            footerAllowed = false,
            headerAllowed = false,
            mainAllowed = false
        )
    }

    fun withFormNotAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(this, formAllowed = false)
    }

    fun withInteractiveContentNotAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            this,
            interactiveContentAllowed = false
        )
    }

    fun withLabelNotAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(this, labelAllowed = false)
    }

    fun withLegendAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(this, legendProhibited = false)
    }

    fun withMainNotAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(this, mainAllowed = false)
    }

    fun withMeterNotAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(this, meterAllowed = false)
    }

    fun withOptionGroupNotAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(this, optionGroupAllowed = false)
    }

    fun withProgressNotAllowed(): KatyDomContentRestrictions {
        return KatyDomContentRestrictions(this, progressAllowed = false)
    }

}

//---------------------------------------------------------------------------------------------------------------------

