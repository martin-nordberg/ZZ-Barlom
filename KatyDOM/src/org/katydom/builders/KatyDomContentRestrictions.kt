//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

/**
 * Set of restrictions on content. E.g. a header cannot contain a header or footer; a form cannot be nested.
 */
class KatyDomContentRestrictions(
    private val anchorTagAllowed: Boolean,
    private val footerAllowed: Boolean,
    private val formAllowed: Boolean,
    private val headerAllowed: Boolean,
    private val interactiveContentAllowed: Boolean,
    private val mainAllowed : Boolean
) {

    constructor()
        : this(true,true,true,true,true,true)

    fun confirmAnchorTagAllowed() {
        check(anchorTagAllowed) { "Anchor element <a> not allowed here" }
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

    fun confirmMainAllowed() {
        check(mainAllowed) { "Element type <main> not allowed here." }
    }

    fun withAnchorTagNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = false,
            footerAllowed = footerAllowed,
            formAllowed = formAllowed,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = mainAllowed
        )
    }

    fun withAnchorTagInteractiveContentNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = false,
            footerAllowed = footerAllowed,
            formAllowed = formAllowed,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = false,
            mainAllowed = mainAllowed
        )
    }

    fun withFooterNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = false,
            formAllowed = formAllowed,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = mainAllowed
        )
    }

    fun withFooterHeaderMainNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = false,
            formAllowed = formAllowed,
            headerAllowed = false,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = false
        )
    }

    fun withFormNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = footerAllowed,
            formAllowed = false,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = mainAllowed
        )
    }

    fun withHeaderNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = footerAllowed,
            formAllowed = formAllowed,
            headerAllowed = false,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = mainAllowed
        )
    }

    fun withInteractiveContentNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = footerAllowed,
            formAllowed = formAllowed,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = false,
            mainAllowed = mainAllowed
        )
    }

    fun withMainNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = footerAllowed,
            formAllowed = formAllowed,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = false
        )
    }

}