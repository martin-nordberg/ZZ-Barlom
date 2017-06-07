//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.api

import org.junit.jupiter.api.Test
import org.katydom.types.EAnchorHtmlLinkType

/**
 * Tests for quick trials of KatyDOM DSL.
 */
class ExperimentationTests {

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

                div(".some-class") {
                    classes("big" to true, "small" to false, "smelly" to true)
                    attribute("class", "very-classy")
                    attributes("a1" to "v1", "a2" to "v2")

                    onclick { event ->
                      event.clientX
                    }
                }

                text("example")

                hr {
                    attribute("id", "me")
                }

            }

        }

        val html = """<div class="my-class" id="myDiv" style="color:red">
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
                     |  <div a1="v1" a2="v2" class="big smelly some-class very-classy"></div>
                     |  example
                     |  <hr id="me"></hr>
                     |</div>""".trimMargin()

        checkBuild(html, vdomNode)

    }

    @Test
    fun `Sample 2 of KatyDOM DSL should produce correct HTML`() {

        val vdomNode = katyDom {

            div("#myDiv.my-class", style = "color:red") {

                div(hidden=true) {
                    text("a sample")
                }

                div(".some-class",title="A Title") {
                    classes("big" to true, "small" to false, "smelly" to true)
                    attribute("class", "very-classy")
                    attributes("a1" to "v1", "a2" to "v2")

                    onclick { event ->
                      event.clientX
                    }
                }

            }

        }

        val html = """<div class="my-class" id="myDiv" style="color:red">
                     |  <div hidden="">
                     |    a sample
                     |  </div>
                     |  <div a1="v1" a2="v2" class="big smelly some-class very-classy" title="A Title"></div>
                     |</div>""".trimMargin()

        checkBuild(html, vdomNode)

    }

    @Test
    fun `Sample 3 of KatyDOM DSL should produce correct HTML`() {

        val vdomNode = katyDom {

            div("#myDiv.my-class", "div1" ) {

                span ( ".mottled" ) {
                    a ( href="#somewhere", rel=listOf(EAnchorHtmlLinkType.NEXT, EAnchorHtmlLinkType.NOREFERRER) ) {
                        text( "Go Somewhere" )
                    }
                }

            }

        }

        val html = """<div class="my-class" id="myDiv">
                     |  <span class="mottled">
                     |    <a href="#somewhere" rel="next noreferrer">
                     |      Go Somewhere
                     |    </a>
                     |  </span>
                     |</div>""".trimMargin()

        checkBuild(html, vdomNode)

    }

}
