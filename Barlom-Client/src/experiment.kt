import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.api.katyDom
import org.katydom.api.katyDomComponent
import org.katydom.api.makeKatyDomLifecycle
import kotlin.browser.document

val cmp0 = katyDomComponent {
    div {
        div("#abc.cls1.cls2") {

        }
    }
    div("#another") {

    }
}
val vdomNode = katyDom {

    div("#myDiv.my-class", style = "color:red") {

        div {
            text("a sample")

            ul("#a-list") {
                li { text("item 1") }
                li { text("item 2") }
                li {
                    onclick {
                        console.log("Clicked!")
                    }

                    text("item 3")
                    div {}
                }
            }

        }

        div(".some-class") {
            classes("big" to true, "small" to false, "smelly" to true)
            attribute("class", "very-classy")
            attributes("a1" to "v1", "a2" to "v2")
        }

        text("example")

        hr {
            attribute("id", "me")
        }

        cmp0()

    }

}

fun main(args: Array<String> ) {

    val lifecycle = makeKatyDomLifecycle()

    val appElement = document.getElementById("app")

    if ( appElement != null && vdomNode is KatyDomHtmlElement) {
        lifecycle.build(appElement, vdomNode)
    }

    console.log( "DONE" )

}