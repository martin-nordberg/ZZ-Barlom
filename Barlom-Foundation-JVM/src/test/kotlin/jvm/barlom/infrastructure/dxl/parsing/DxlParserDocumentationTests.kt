//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.dxl.parsing

import o.barlom.infrastructure.dxl.model.elements.DxlDeclaration
import o.barlom.infrastructure.dxl.parsing.DxlParser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

//---------------------------------------------------------------------------------------------------------------------

@Suppress("RemoveRedundantBackticks")
internal class DxlParserDocumentationTests {

    @Test
    fun `An element's documentation is parsed`() {

        val code = """
            /* first line of documentation
               second line of documentation */
            #example;
        """.trimIndent()

        val parser = DxlParser("test.dxl", code)

        val element = parser.parseElement()

        assertNotNull(element.documentation)
        assertEquals(
            "/* first line of documentation\n" +
                    "   second line of documentation */", element.documentation.text
        )
        assertEquals("test.dxl(1,1)", element.documentation.origin.toString())

        element as DxlDeclaration

        assertEquals("#example", element.concept.text)
        assertEquals("test.dxl(3,1)", element.origin.toString())
        assertEquals("test.dxl(3,1)", element.concept.origin.toString())

    }

}

//---------------------------------------------------------------------------------------------------------------------

