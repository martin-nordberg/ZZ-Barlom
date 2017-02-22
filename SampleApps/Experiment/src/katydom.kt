
import org.katydom.api.katyDom
import org.katydom.api.katyDomComponent
import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.dom.createElement


fun Element.appendChildWithText(tag: String, text: String) {

    appendChild(document.createElement(tag) {
        appendChild(document.createTextNode(text))
    })

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

val cmp0 = katyDomComponent {
    div {
        div("#abc.cls1.cls2") {

        }
    }
    div("#another") {

    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

val vdomNode = katyDom {

    div("#myDiv.my-class", style = "color:red") {

        div {
            text("a sample")

            ul("#a-list") {
                li { text("item 1") }
                li { text("item 2") }
                li {
                    text("item 3")
                    div {}
                }
            }

        }

        div {
            attribute("src", "some/url")
            attributes("a1" to "v1", "a2" to "v2")
        }

        text("example")

        hr {
            attribute("id", "me")
        }

        cmp0

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
