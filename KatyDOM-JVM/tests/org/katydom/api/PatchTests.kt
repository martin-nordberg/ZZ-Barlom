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
    fun `Sample 2 patches correctly`() {

        val vnode1 = katyDom {

            section("#mysection") {

                h1 {
                    span( ".pink") {
                        text( "A Heading" )
                    }
                }

                span("#theStuff") {
                    text("Stuff is here.")
                }

            }

        }

        val html1 = """<section id="mysection">
                      |  <h1>
                      |    <span class="pink">
                      |      A Heading
                      |    </span>
                      |  </h1>
                      |  <span id="theStuff">
                      |    Stuff is here.
                      |  </span>
                      |</section>""".trimMargin()

        val vnode2 = katyDom {

            section("#mysection") {

                h6 {
                    text( "An Unstyled Heading" )
                }

                span("#theStuff") {
                    text("Stuff is here.")
                }

            }

        }

        val html2 = """<section id="mysection">
                      |  <h6>
                      |    An Unstyled Heading
                      |  </h6>
                      |  <span id="theStuff">
                      |    Stuff is here.
                      |  </span>
                      |</section>""".trimMargin()

        checkPatch(html2, vnode2, html1, vnode1)

    }

    @Test
    fun `Reordered nodes (A) patch correctly`() {

        val vnode1 = katyDom {

            article("#holder") {

                div("#a") {}
                div("#b") {}
                div("#c") {}
                div("#d") {}

            }

        }

        val html1 = """<article id="holder">
                      |  <div id="a"></div>
                      |  <div id="b"></div>
                      |  <div id="c"></div>
                      |  <div id="d"></div>
                      |</article>""".trimMargin()

        val vnode2 = katyDom {

            article("#holder") {

                div("#a") {}
                div("#c") {}
                div("#b") {}
                div("#d") {}

            }

        }

        val html2 = """<article id="holder">
                      |  <div id="a"></div>
                      |  <div id="c"></div>
                      |  <div id="b"></div>
                      |  <div id="d"></div>
                      |</article>""".trimMargin()

        checkPatch(html2, vnode2, html1, vnode1)

    }

}