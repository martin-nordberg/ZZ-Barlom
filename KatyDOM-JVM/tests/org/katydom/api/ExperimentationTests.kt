//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.api

import org.junit.jupiter.api.Test

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
                }

                text("example")

                hr {
                    attribute("id", "me")
                }

            }

        }

        val html = """<div style="color:red" id="myDiv" class="my-class">
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
                     |  <div a1="v1" a2="v2" class="some-class big smelly very-classy"></div>
                     |  example
                     |  <hr id="me"></hr>
                     |</div>""".trimMargin()

        checkBuild(html, vdomNode)

    }

}
