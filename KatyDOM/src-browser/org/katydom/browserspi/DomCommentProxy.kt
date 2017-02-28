//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.browserspi

import org.katydom.spi.CommentProxy
import org.w3c.dom.Comment

/**
 * Implementation of CommentProxy for the browser DOM.
 */
class DomCommentProxy(text: Comment) : DomNodeProxy(text), CommentProxy {

}