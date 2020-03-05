//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.dxl.scanning

import i.barlom.infrastructure.dxl.scanning.DxlToken
import i.barlom.infrastructure.dxl.scanning.EDxlTokenType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

//---------------------------------------------------------------------------------------------------------------------

@Suppress("RemoveRedundantBackticks")
internal class DxlScannerSimpleTests
    : DxlScannerTests() {

    @Test
    fun `Token types have names`() {

        assertEquals("'['", EDxlTokenType.LEFT_BRACKET.text)

    }

    @Test
    fun `Single character tokens are scanned`() {

        checkScan(
            " { } ( ) [ ] . : , = ; - ~ % @ \\ / ^ & * # ",
            DxlToken(EDxlTokenType.LEFT_BRACE, "{", 1, 2),
            DxlToken(EDxlTokenType.RIGHT_BRACE, "}", 1, 4),
            DxlToken(EDxlTokenType.LEFT_PARENTHESIS, "(", 1, 6),
            DxlToken(EDxlTokenType.RIGHT_PARENTHESIS, ")", 1, 8),
            DxlToken(EDxlTokenType.LEFT_BRACKET, "[", 1, 10),
            DxlToken(EDxlTokenType.RIGHT_BRACKET, "]", 1, 12),
            DxlToken(EDxlTokenType.DOT, ".", 1, 14),
            DxlToken(EDxlTokenType.COLON, ":", 1, 16),
            DxlToken(EDxlTokenType.COMMA, ",", 1, 18),
            DxlToken(EDxlTokenType.EQUALS, "=", 1, 20),
            DxlToken(EDxlTokenType.SEMICOLON, ";", 1, 22),
            DxlToken(EDxlTokenType.DASH, "-", 1, 24),
            DxlToken(EDxlTokenType.TILDE, "~", 1, 26),
            DxlToken(EDxlTokenType.PERCENT, "%", 1, 28),
            DxlToken(EDxlTokenType.AT, "@", 1, 30),
            DxlToken(EDxlTokenType.BACKSLASH, "\\", 1, 32),
            DxlToken(EDxlTokenType.SLASH, "/", 1, 34),
            DxlToken(EDxlTokenType.CARET, "^", 1, 36),
            DxlToken(EDxlTokenType.AMPERSAND, "&", 1, 38),
            DxlToken(EDxlTokenType.ASTERISK, "*", 1, 40),
            DxlToken(EDxlTokenType.HASH, "#", 1, 42)
        )

    }

    @Test
    fun `Identifiers are scanned`() {

        checkScan(
            " a abc Ab234 _ _something rr_12_pq true false",
            DxlToken(EDxlTokenType.IDENTIFIER, "a", 1, 2),
            DxlToken(EDxlTokenType.IDENTIFIER, "abc", 1, 4),
            DxlToken(EDxlTokenType.IDENTIFIER, "Ab234", 1, 8),
            DxlToken(EDxlTokenType.IDENTIFIER, "_", 1, 14),
            DxlToken(EDxlTokenType.IDENTIFIER, "_something", 1, 16),
            DxlToken(EDxlTokenType.IDENTIFIER, "rr_12_pq", 1, 27),
            DxlToken(EDxlTokenType.BOOLEAN_LITERAL, "true", 1, 36),
            DxlToken(EDxlTokenType.BOOLEAN_LITERAL, "false", 1, 41)
        )

    }

    @Test
    fun `Quoted identifiers are scanned`() {

        checkScan(
            " `a` `this one - has punctuation; really`",
            DxlToken(EDxlTokenType.IDENTIFIER, "`a`", 1, 2),
            DxlToken(EDxlTokenType.IDENTIFIER, "`this one - has punctuation; really`", 1, 6)
        )

    }

    @Test
    fun `Concept keywords are scanned`() {

        checkScan(
            "#abc #pqrs \n\n #xyz_123 \n",
            DxlToken(EDxlTokenType.HASH, "#", 1, 1),
            DxlToken(EDxlTokenType.IDENTIFIER, "abc", 1, 2),
            DxlToken(EDxlTokenType.HASH, "#", 1, 6),
            DxlToken(EDxlTokenType.IDENTIFIER, "pqrs", 1, 7),
            DxlToken(EDxlTokenType.HASH, "#", 3, 2),
            DxlToken(EDxlTokenType.IDENTIFIER, "xyz_123", 3, 3)
        )

    }

    @Test
    fun `Connector lines are scanned`() {

        checkScan(
            "-- -> <- -| |- ---| |---",
            DxlToken(EDxlTokenType.DOUBLE_DASH, "--", 1, 1),
            DxlToken(EDxlTokenType.RIGHT_ARROW, "->", 1, 4),
            DxlToken(EDxlTokenType.LEFT_ARROW, "<-", 1, 7),
            DxlToken(EDxlTokenType.LEFT_LINE_BRACKET, "-|", 1, 10),
            DxlToken(EDxlTokenType.RIGHT_LINE_BRACKET, "|-", 1, 13),
            DxlToken(EDxlTokenType.DOUBLE_DASH, "--", 1, 16),
            DxlToken(EDxlTokenType.LEFT_LINE_BRACKET, "-|", 1, 18),
            DxlToken(EDxlTokenType.RIGHT_LINE_BRACKET, "|-", 1, 21),
            DxlToken(EDxlTokenType.DOUBLE_DASH, "--", 1, 23)
        )

    }

    @Test
    fun `Connector keywords are scanned`() {

        checkScan(
            "~abc ~pqrs \n\n ~xyz_123 \n",
            DxlToken(EDxlTokenType.TILDE, "~", 1, 1),
            DxlToken(EDxlTokenType.IDENTIFIER, "abc", 1, 2),
            DxlToken(EDxlTokenType.TILDE, "~", 1, 6),
            DxlToken(EDxlTokenType.IDENTIFIER, "pqrs", 1, 7),
            DxlToken(EDxlTokenType.TILDE, "~", 3, 2),
            DxlToken(EDxlTokenType.IDENTIFIER, "xyz_123", 3, 3)
        )

    }

    @Test
    fun `String literals are scanned`() {

        checkScan(
            " \"abc\" \n \"qrs\"",
            DxlToken(EDxlTokenType.STRING_LITERAL, "\"abc\"", 1, 2),
            DxlToken(EDxlTokenType.STRING_LITERAL, "\"qrs\"", 2, 2)
        )

    }

    @Test
    fun `Character literals are scanned`() {

        checkScan(
            " 'a' '\\n' '\\t' 'Q' ",
            DxlToken(EDxlTokenType.CHARACTER_LITERAL, "'a'", 1, 2),
            DxlToken(EDxlTokenType.CHARACTER_LITERAL, "'\\n'", 1, 6),
            DxlToken(EDxlTokenType.CHARACTER_LITERAL, "'\\t'", 1, 11),
            DxlToken(EDxlTokenType.CHARACTER_LITERAL, "'Q'", 1, 16)
        )

    }

    @Test
    fun `Integer literals are scanned`() {

        checkScan(
            "123\n123_456\n5678L",
            DxlToken(EDxlTokenType.INTEGER_LITERAL, "123", 1, 1),
            DxlToken(EDxlTokenType.INTEGER_LITERAL, "123_456", 2, 1),
            DxlToken(EDxlTokenType.INTEGER_LITERAL, "5678L", 3, 1)
        )

    }

    @Test
    fun `Floating point literals are scanned`() {

        checkScan(
            "123.0\n123_456e78f\n1.00E-30D",
            DxlToken(EDxlTokenType.FLOATING_POINT_LITERAL, "123.0", 1, 1),
            DxlToken(EDxlTokenType.FLOATING_POINT_LITERAL, "123_456e78f", 2, 1),
            DxlToken(EDxlTokenType.FLOATING_POINT_LITERAL, "1.00E-30D", 3, 1)
        )

    }

    @Test
    fun `UUID literals are scanned`() {

        checkScan(
            "%12345678-ABCD-EFab-cdef-901234567890%\n%11111111-2222-3333-4444-555555555555%",
            DxlToken(EDxlTokenType.UUID_LITERAL, "%12345678-ABCD-EFab-cdef-901234567890%", 1, 1),
            DxlToken(EDxlTokenType.UUID_LITERAL, "%11111111-2222-3333-4444-555555555555%", 2, 1)
        )

    }

    @Test
    fun `Documentation blocks are scanned`() {

        checkScan(
            "/* this is a block of documentation */\n\n /* this is ** another */",
            DxlToken(EDxlTokenType.DOCUMENTATION, "/* this is a block of documentation */", 1, 1),
            DxlToken(EDxlTokenType.DOCUMENTATION, "/* this is ** another */", 3, 2)
        )

    }

    @Test
    fun `Connections scan correctly`() {

        checkScan(
            "[a]---|b|---[c]",
            DxlToken(EDxlTokenType.LEFT_BRACKET, "[", 1, 1),
            DxlToken(EDxlTokenType.IDENTIFIER, "a", 1, 2),
            DxlToken(EDxlTokenType.RIGHT_BRACKET, "]", 1, 3),
            DxlToken(EDxlTokenType.DOUBLE_DASH, "--", 1, 4),
            DxlToken(EDxlTokenType.LEFT_LINE_BRACKET, "-|", 1, 6),
            DxlToken(EDxlTokenType.IDENTIFIER, "b", 1, 8),
            DxlToken(EDxlTokenType.RIGHT_LINE_BRACKET, "|-", 1, 9),
            DxlToken(EDxlTokenType.DOUBLE_DASH, "--", 1, 11),
            DxlToken(EDxlTokenType.LEFT_BRACKET, "[", 1, 13),
            DxlToken(EDxlTokenType.IDENTIFIER, "c", 1, 14),
            DxlToken(EDxlTokenType.RIGHT_BRACKET, "]", 1, 15)
        )

    }

}

//---------------------------------------------------------------------------------------------------------------------

