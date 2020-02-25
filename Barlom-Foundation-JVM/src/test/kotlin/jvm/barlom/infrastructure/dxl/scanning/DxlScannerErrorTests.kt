//
// (C) Copyright 2018-2019 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.dxl.scanning

import i.barlom.infrastructure.dxl.scanning.DxlToken
import i.barlom.infrastructure.dxl.scanning.EDxlTokenType
import org.junit.jupiter.api.Test

//---------------------------------------------------------------------------------------------------------------------

@Suppress("RemoveRedundantBackticks")
internal class DxlScannerErrorTests
    : DxlScannerTests() {

    @Test
    fun `Unterminated string literals are scanned`() {

        checkScan(
            " #key \"first \n\"second ",
            DxlToken(EDxlTokenType.HASH, "#", 1, 2),
            DxlToken(EDxlTokenType.IDENTIFIER, "key", 1, 3),
            DxlToken(EDxlTokenType.UNTERMINATED_STRING_LITERAL, "\"first ", 1, 7),
            DxlToken(EDxlTokenType.UNTERMINATED_STRING_LITERAL, "\"second ", 2, 1)
        )

    }

    @Test
    fun `Empty concept keywords are scanned`() {

        checkScan(
            "#key #2\n#- #ok",
            DxlToken(EDxlTokenType.HASH, "#", 1, 1),
            DxlToken(EDxlTokenType.IDENTIFIER, "key", 1, 2),
            DxlToken(EDxlTokenType.HASH, "#", 1, 6),
            DxlToken(EDxlTokenType.INTEGER_LITERAL, "2", 1, 7),
            DxlToken(EDxlTokenType.HASH, "#", 2, 1),
            DxlToken(EDxlTokenType.DASH, "-", 2, 2),
            DxlToken(EDxlTokenType.HASH, "#", 2, 4),
            DxlToken(EDxlTokenType.IDENTIFIER, "ok", 2, 5)
        )

    }

    @Test
    fun `Unterminated character literals are scanned`() {

        checkScan(
            " #key '1\n'2",
            DxlToken(EDxlTokenType.HASH, "#", 1, 2),
            DxlToken(EDxlTokenType.IDENTIFIER, "key", 1, 3),
            DxlToken(EDxlTokenType.UNTERMINATED_CHARACTER_LITERAL, "'1", 1, 7),
            DxlToken(EDxlTokenType.UNTERMINATED_CHARACTER_LITERAL, "'2", 2, 1)
        )

    }

    @Test
    fun `Unterminated documentation is scanned`() {

        checkScan(
            "/* starts but does not end\n",
            DxlToken(EDxlTokenType.UNTERMINATED_DOCUMENTATION, "/* starts but does not end\n", 1, 1)
        )

    }

    @Test
    fun `Invalid UUID literals are scanned`() {

        checkScan(
            " %1234-1234-1234 %abcW %123456789--%",
            DxlToken(EDxlTokenType.INVALID_UUID_LITERAL, "%1234-1234-1234", 1, 2),
            DxlToken(EDxlTokenType.INVALID_UUID_LITERAL, "%abc", 1, 18),
            DxlToken(EDxlTokenType.IDENTIFIER, "W", 1, 22),
            DxlToken(EDxlTokenType.INVALID_UUID_LITERAL, "%123456789--%", 1, 24)
        )

    }

    @Test
    fun `Unterminated quoted identifiers are scanned`() {

        checkScan(
            " `the first` `the second\n`the third",
            DxlToken(EDxlTokenType.IDENTIFIER, "`the first`", 1, 2),
            DxlToken(EDxlTokenType.UNTERMINATED_QUOTED_IDENTIFIER, "`the second", 1, 14),
            DxlToken(EDxlTokenType.UNTERMINATED_QUOTED_IDENTIFIER, "`the third", 2, 1)
        )

    }

    @Test
    fun `Invalid quoted identifiers are scanned`() {

        checkScan(
            "`a.` `b:` `c[` `d]` `e{` `f}` `g<` `h>` `i\\` `j/`",
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`a.`", 1, 1),
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`b:`", 1, 6),
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`c[`", 1, 11),
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`d]`", 1, 16),
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`e{`", 1, 21),
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`f}`", 1, 26),
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`g<`", 1, 31),
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`h>`", 1, 36),
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`i\\`", 1, 41),
            DxlToken(EDxlTokenType.INVALID_QUOTED_IDENTIFIER, "`j/`", 1, 46)
        )

    }

}

//---------------------------------------------------------------------------------------------------------------------

