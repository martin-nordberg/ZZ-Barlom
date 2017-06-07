//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

/**
 * Set of restrictions on content. E.g. a header cannot contain a header or footer.
 */
class KatyDomContentRestrictions(
    val anchorTagAllowed: Boolean = true,
    val footerAllowed: Boolean = true,
    val headerAllowed: Boolean = true,
    val interactiveContentAllowed: Boolean = true,
    val mainAllowed : Boolean = true
) {

    fun withAnchorTagNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = false,
            footerAllowed = footerAllowed,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = mainAllowed
        )
    }

    fun withAnchorTagInteractiveContentNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = false,
            footerAllowed = footerAllowed,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = false,
            mainAllowed = mainAllowed
        )
    }

    fun withFooterNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = false,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = mainAllowed
        )
    }

    fun withFooterHeaderMainNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = false,
            headerAllowed = false,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = false
        )
    }

    fun withHeaderNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = footerAllowed,
            headerAllowed = false,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = mainAllowed
        )
    }

    fun withInteractiveContentNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = footerAllowed,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = false,
            mainAllowed = mainAllowed
        )
    }

    fun withMainNotAllowed() : KatyDomContentRestrictions {
        return KatyDomContentRestrictions(
            anchorTagAllowed = anchorTagAllowed,
            footerAllowed = footerAllowed,
            headerAllowed = headerAllowed,
            interactiveContentAllowed = interactiveContentAllowed,
            mainAllowed = false
        )
    }

}