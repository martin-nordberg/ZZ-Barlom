//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

/**
 * Set of restrictions on content. E.g. a header cannot contain a header or footer; a form cannot be nested.
 */
class KatyDomContentRestrictions(
    private val anchorAllowed: Boolean,
    private val footerAllowed: Boolean,
    private val formAllowed: Boolean,
    private val headerAllowed: Boolean,
    private val interactiveContentAllowed: Boolean,
    private val labelAllowed : Boolean,
    private val mainAllowed : Boolean
) {

    constructor()
        : this(true,true,true,true,true,true,true)

    constructor(
        original: KatyDomContentRestrictions,
        anchorAllowed: Boolean = true,
        footerAllowed: Boolean = true,
        formAllowed: Boolean = true,
        headerAllowed: Boolean = true,
        interactiveContentAllowed: Boolean = true,
        labelAllowed : Boolean = true,
        mainAllowed : Boolean = true
    ) : this(
        original.anchorAllowed && anchorAllowed,
        original.footerAllowed && footerAllowed,
        original.formAllowed && formAllowed,
        original.headerAllowed && headerAllowed,
        original.interactiveContentAllowed && interactiveContentAllowed,
        original.labelAllowed && labelAllowed,
        original.mainAllowed && mainAllowed
    )

    fun confirmAnchorAllowed() {
        check(anchorAllowed) { "Element type <a> not allowed here" }
    }

    fun confirmFooterAllowed() {
        check(footerAllowed) { "Element type <footer> not allowed here."}
    }

    fun confirmFormAllowed() {
        check(formAllowed) { "Element type <form> not allowed here. (Form elements cannot be nested.)" }
    }

    fun confirmHeaderAllowed() {
        check(headerAllowed) { "Element type <header> not allowed here."}
    }

    fun confirmInteractiveContentAllowed() {
        check(interactiveContentAllowed) { "Interactive content not allowed here." }
    }

    fun confirmLabelAllowed() {
        check(mainAllowed) { "Element type <label> not allowed here." }
    }

    fun confirmMainAllowed() {
        check(mainAllowed) { "Element type <main> not allowed here." }
    }

    fun withAnchorInteractiveContentNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            this,
            anchorAllowed = false,
            interactiveContentAllowed = false
        )
    }

    fun withFooterHeaderMainNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            this,
            footerAllowed = false,
            headerAllowed = false,
            mainAllowed = false
        )
    }

    fun withFormNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions( this, formAllowed = false )
    }

    fun withLabelNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions( this, labelAllowed = false )
    }

    fun withMainNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions( this, mainAllowed = false )
    }

}