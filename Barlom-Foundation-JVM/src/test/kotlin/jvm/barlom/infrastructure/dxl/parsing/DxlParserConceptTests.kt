//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.dxl.parsing

import o.barlom.infrastructure.dxl.parsing.DxlParser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

//---------------------------------------------------------------------------------------------------------------------

@Suppress("RemoveRedundantBackticks")
internal class DxlParserConceptTests {

    private fun checkParseAndGenerate(code: String) {
        val parser = DxlParser("test.dxl", code)
        val element = parser.parseTopLevel()

        assertEquals(code, element.code)
    }

    @Test
    fun `An anonymous documented concept is parsed`() {

        val code = """
            /* documentation */
            []
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A named documented concept is parsed`() {

        val code = """
            /* documentation */
            [sample.test]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with UUID is parsed`() {

        val code = """
            /* documentation */
            [%11111111-2222-3333-4444-555555555555% sample.test]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with empty parameters is parsed`() {

        val code = """
            /* documentation */
            [sample.test()]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with simple parameters is parsed`() {

        val code = """
            /* documentation */
            [sample.test(a, b, c)]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with typed parameters is parsed`() {

        val code = """
            /* documentation */
            [sample.test(a: A, b: B, c: C)]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with default valued parameters is parsed`() {

        val code = """
            /* documentation */
            [sample.test(a = 3, b: B = "bee", c: C = 17.4f)]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with a type is parsed`() {

        val code = """
            [sample.test(a): S]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with empty type arguments is parsed`() {

        val code = """
            [sample.test(a): S()]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with an unnamed type argument is parsed`() {

        val code = """
            [sample.test(a): S("example")]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with unnamed type arguments is parsed`() {

        val code = """
            [sample.test(a): S("example", 123)]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with named type arguments is parsed`() {

        val code = """
            [sample.test(a): S(a="example", b=123)]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `Multiple concepts are parsed`() {

        val code = """
            /* first */
            [sample.test: S]
            /* second */
            [example.two(a, b): S]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with properties is parsed`() {

        val code = """
            [sample.test(a): S ~ x = 1 ~ y = "two" ~ ch = 'a' ~ b = true]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept property removal is parsed`() {

        val code = """
            [sample.test(a): S ~ x = 1 ~ !z]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept property rename is parsed`() {

        val code = """
            [sample.test(a): S ~ x^xx = 1 ~ z^zz]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with long-valued properties is parsed`() {

        val code = """
            [sample.test(a): S
              ~ x = "a long string that ensures line wrapping"
              ~ y = "another long string to really make sure"
              ~ z = "definitely this will force multiple lines"
            ]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with content is parsed`() {

        val code = """
            [sample.test(a): S] { [a] [b] }
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with a connection is parsed`() {

        val code = """
            [sample.test(a): S]---|:hasStuff|---[s: Stuff]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with a right-directed connection is parsed`() {

        val code = """
            [sample.test(a): S]---|:hasStuff|-->[s: Stuff]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with a left-directed connection is parsed`() {

        val code = """
            [sample.test(a): S]<--|:hasStuff|---[s: Stuff]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with a disconnection is parsed`() {

        val code = """
            [sample.test(a): S]--!-|:hasStuff|---[s: Stuff]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with a right-directed disconnection is parsed`() {

        val code = """
            [sample.test(a): S]--!-|:hasStuff|-->[s: Stuff]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept with a left-directed disconnection is parsed`() {

        val code = """
            [sample.test(a): S]<-!-|:hasStuff|---[s: Stuff]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept connection chain is parsed`() {

        val code = """
            [`Ms Miser`: Person]---|:hasStuff|-->[s: Stuff]---|:fillsCloset|-->[c: Closet]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept deletion is parsed`() {

        val code = """
            ![ObsoleteConcept]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A concept rename is parsed`() {

        val code = """
            [OldName^NewName]
        """.trimIndent()

        checkParseAndGenerate(code)

    }

}

//---------------------------------------------------------------------------------------------------------------------

