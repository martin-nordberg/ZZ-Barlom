//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package /*jvm*/x.org.katydom.dom

//---------------------------------------------------------------------------------------------------------------------

interface Document : Node {

    fun createComment(data: String): Comment

    fun createElement(tagName: String): Element

    fun createTextNode(data: String): Text

}

//---------------------------------------------------------------------------------------------------------------------

