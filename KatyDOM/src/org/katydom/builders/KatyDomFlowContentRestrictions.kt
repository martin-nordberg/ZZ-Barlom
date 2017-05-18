//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

/**
 * Set of restrictions on flow content. E.g. a header cannot contain a header or footer.
 */
class KatyDomFlowContentRestrictions(
    val footerAllowed: Boolean = true,
    val headerAllowed: Boolean = true,
    val mainAllowed : Boolean = true
) {

    fun withFooterNotAllowed() : KatyDomFlowContentRestrictions {
        return KatyDomFlowContentRestrictions(
            footerAllowed = false,
            headerAllowed = headerAllowed,
            mainAllowed = mainAllowed
        )
    }

    fun withFooterHeaderMainNotAllowed() : KatyDomFlowContentRestrictions {
        return KatyDomFlowContentRestrictions(
            footerAllowed = false,
            headerAllowed = false,
            mainAllowed = false
        )
    }

    fun withHeaderNotAllowed() : KatyDomFlowContentRestrictions {
        return KatyDomFlowContentRestrictions(
            footerAllowed = footerAllowed,
            headerAllowed = false,
            mainAllowed = mainAllowed
        )
    }

    fun withMainNotAllowed() : KatyDomFlowContentRestrictions {
        return KatyDomFlowContentRestrictions(
            footerAllowed = footerAllowed,
            headerAllowed = headerAllowed,
            mainAllowed = false
        )
    }

}