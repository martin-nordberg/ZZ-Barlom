//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.builders

import org.katydom.abstractnodes.KatyDomHtmlElement

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class KatyDomPhrasingOptionContentBuilder(

        internal val phrasingContent: KatyDomPhrasingContentBuilder,

        private val element: KatyDomHtmlElement

) : KatyDomElementContentBuilder(element) {


}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
