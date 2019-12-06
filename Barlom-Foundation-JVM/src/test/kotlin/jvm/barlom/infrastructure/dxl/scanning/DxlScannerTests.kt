//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.dxl.scanning

import i.barlom.infrastructure.dxl.scanning.DxlScanner
import i.barlom.infrastructure.dxl.scanning.DxlToken
import i.barlom.infrastructure.dxl.scanning.EDxlTokenType
import i.barlom.infrastructure.dxl.scanning.StringTokenizer
import kotlin.test.assertEquals

//---------------------------------------------------------------------------------------------------------------------

@Suppress("RemoveRedundantBackticks")
internal abstract class DxlScannerTests {

    protected fun checkScan( code: String, vararg expectedTokens: DxlToken) {

        val scanner = DxlScanner( StringTokenizer( code ) )

        for ( expectedToken in expectedTokens ) {
            val token = scanner.scan()
            assertEquals( expectedToken.type, token.type )
            assertEquals( expectedToken.text, token.text )
            assertEquals( expectedToken.line, token.line )
            assertEquals( expectedToken.column, token.column )
            assertEquals( expectedToken.text.length, token.length )
        }

        assertEquals( EDxlTokenType.END_OF_INPUT, scanner.scan().type )

    }

}

//---------------------------------------------------------------------------------------------------------------------

