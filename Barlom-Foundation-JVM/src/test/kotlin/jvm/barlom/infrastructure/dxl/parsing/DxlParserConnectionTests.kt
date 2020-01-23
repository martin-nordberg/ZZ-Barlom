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
internal class DxlParserConnectionTests {

    private fun checkParseAndGenerate(code: String) {
        val parser = DxlParser("test.dxl", code)
        val element = parser.parseTopLevel()

        assertEquals(code, element.code)
    }

    @Test
    fun `An anonymous documented connection is parsed`() {

        val code = """
            /* documentation */
            -||-
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A named documented connection is parsed`() {

        val code = """
            /* documentation */
            -|sample.test|-
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A connection with UUID is parsed`() {

        val code = """
            /* documentation */
            -|%11111111-2222-3333-4444-555555555555% sample.test|-
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A connection with empty parameters is parsed`() {

        val code = """
            /* documentation */
            -|sample.test()|-
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A connection with simple parameters is parsed`() {

        val code = """
            /* documentation */
            -|sample.test(a, b, c)|-
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A connection with a type is parsed`() {

        val code = """
            -|sample.test(a): S|-
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `Multiple connections are parsed`() {

        val code = """
            /* first */
            -|sample.test: S|-
            /* second */
            -|example.two(a, b): S|-
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A connection with properties is parsed`() {

        val code = """
            -|sample.test(a): S ~ x = 1 ~ y = "two" ~ ch = 'a' ~ b = true|-
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `A connection with long-valued properties is parsed`() {

        val code = """
            -|sample.test(a): S
              ~ x = "a long string that ensures line wrapping"
              ~ y = "another long string to really make sure"
              ~ z = "definitely this will force multiple lines"
            |-
        """.trimIndent()

        checkParseAndGenerate(code)

    }

}

//---------------------------------------------------------------------------------------------------------------------

