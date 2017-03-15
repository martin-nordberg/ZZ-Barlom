package org.katydom.api

import org.junit.jupiter.api.Test

/**
 * Tests of the virtual DOM patch function.
 */
class PatchTests {

    @Test
    fun `Sample 1 patches correctly`() {

        val vnode1 = katyDom {

            div("#mydiv.funnyone") {

                ul("#theList") {

                    li(key = "1") { text("One") }
                    li(key = "2") { text("Two") }
                    li(key = "3") { text("Three") }
                    li(key = "4") { text("Four") }

                }

            }

        }

        val html1 = """<div id="mydiv" class="funnyone">
                      |  <ul id="theList">
                      |    <li>
                      |      One
                      |    </li>
                      |    <li>
                      |      Two
                      |    </li>
                      |    <li>
                      |      Three
                      |    </li>
                      |    <li>
                      |      Four
                      |    </li>
                      |  </ul>
                      |</div>""".trimMargin()

        val vnode2 = katyDom {

            div("#mydiv.funnyone") {

                ul("#theList") {

                    li(key = "1") { text("One") }
                    li(key = "2") { text("Two") }
                    li(key = "4") { text("Four") }
                    li(key = "5") { text("Five") }

                }

            }

        }

        val html2 = """<div id="mydiv" class="funnyone">
                      |  <ul id="theList">
                      |    <li>
                      |      One
                      |    </li>
                      |    <li>
                      |      Two
                      |    </li>
                      |    <li>
                      |      Four
                      |    </li>
                      |    <li>
                      |      Five
                      |    </li>
                      |  </ul>
                      |</div>""".trimMargin()

        checkPatch(html2, vnode2, html1, vnode1)

    }

    @Test
    fun `Reordered nodes (A) patch correctly`() {

        val vnode1 = katyDom {

            div("#holder") {

                div("#a") {}
                div("#b") {}
                div("#c") {}
                div("#d") {}

            }

        }

        val html1 = """<div id="holder">
                      |  <div id="a"></div>
                      |  <div id="b"></div>
                      |  <div id="c"></div>
                      |  <div id="d"></div>
                      |</div>""".trimMargin()

        val vnode2 = katyDom {

            div("#holder") {

                div("#a") {}
                div("#c") {}
                div("#b") {}
                div("#d") {}

            }

        }

        val html2 = """<div id="holder">
                      |  <div id="a"></div>
                      |  <div id="c"></div>
                      |  <div id="b"></div>
                      |  <div id="d"></div>
                      |</div>""".trimMargin()

        checkPatch(html2, vnode2, html1, vnode1)

    }

}