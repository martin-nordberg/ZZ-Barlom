package org.katydom.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.katydom.abstractnodes.KatyDomHtmlElement
import org.katydom.abstractnodes.KatyDomNode
import org.katydom.kdom.KDomDocument

/**
 * Tests for quick trials of KatyDOM DSL.
 */
class ExperimentationTest {

    private fun check(expectedHtml : String, vdomNode : KatyDomNode) {

        val body = KDomDocument().createElement("body")
        val div = body.ownerDocument.createElement( "div" )

        body.appendChild( div )

        assertEquals( div, body.firstChild )

        val lifecycle = makeKatyDomLifecycle()

        if ( vdomNode is KatyDomHtmlElement) {
            lifecycle.build(div, vdomNode)
        }

        assertEquals( expectedHtml, body.firstChild?.toHtmlString() )

    }

    @Test
    fun `Sample 1 of KatyDOM DSL should produce correct HTML`() {

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

                div( ".some-class" ) {
                    classes( "big" to true, "small" to false, "smelly" to true)
                    attribute("class", "very-classy")
                    attributes("a1" to "v1", "a2" to "v2")
                }

                text("example")

                hr {
                    attribute("id", "me")
                }

            }

        }

        val html = """<div id="myDiv" class="my-class" style="color:red">
                     |  <div>
                     |    a sample
                     |    <ul id="a-list">
                     |      <li>
                     |        item 1
                     |      </li>
                     |      <li>
                     |        item 2
                     |      </li>
                     |      <li>
                     |        item 3
                     |        <div></div>
                     |      </li>
                     |    </ul>
                     |  </div>
                     |  <div class="some-class big smelly very-classy" a1="v1" a2="v2"></div>
                     |  example
                     |  <hr id="me"></hr>
                     |</div>""".trimMargin()

        check( html, vdomNode )

    }

}
