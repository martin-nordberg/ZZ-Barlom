//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.dxl.scanning

import i.barlom.infrastructure.dxl.scanning.EDxlTokenType.*
import i.barlom.infrastructure.dxl.scanning.StringTokenizer.Companion.END_OF_INPUT_CHAR

//---------------------------------------------------------------------------------------------------------------------

/**
 * Scanner for L-Zero code. Orchestrates the given [input] tokenizer to produce the individual tokens of a string
 * of raw L-Zero code.
 */
internal class DxlScanner(
    private val input: StringTokenizer
) {

    /**
     * Reads the next token from the input.
     */
    fun scan(): DxlToken {

        var nextChar = input.lookAhead()

        // Ignore whitespace.
        while (isWhitespace(nextChar)) {
            nextChar = input.advanceAndLookAhead()
        }

        // Consume the one character after marking the start of a token.
        input.markAndAdvance()

        return when (nextChar) {

            // Single character punctuation tokens
            '&'  -> input.extractTokenFromMark(AMPERSAND)
            '*'  -> input.extractTokenFromMark(ASTERISK)
            '@'  -> input.extractTokenFromMark(AT)
            '\\' -> input.extractTokenFromMark(BACKSLASH)
            '^'  -> input.extractTokenFromMark(CARET)
            ':'  -> input.extractTokenFromMark(COLON)
            ','  -> input.extractTokenFromMark(COMMA)
            '.'  -> input.extractTokenFromMark(DOT)
            '='  -> input.extractTokenFromMark(EQUALS)
            '>'  -> input.extractTokenFromMark(GREATER_THAN)
            '#'  -> input.extractTokenFromMark(HASH)
            '{'  -> input.extractTokenFromMark(LEFT_BRACE)
            '['  -> input.extractTokenFromMark(LEFT_BRACKET)
            '('  -> input.extractTokenFromMark(LEFT_PARENTHESIS)
            '?'  -> input.extractTokenFromMark(QUESTION_MARK)
            '}'  -> input.extractTokenFromMark(RIGHT_BRACE)
            ']'  -> input.extractTokenFromMark(RIGHT_BRACKET)
            ')'  -> input.extractTokenFromMark(RIGHT_PARENTHESIS)
            ';'  -> input.extractTokenFromMark(SEMICOLON)
            '~'  -> input.extractTokenFromMark(TILDE)

            // "<-" or "<"
            '<'  ->
                if (input.lookAhead() == '-') input.advanceAndExtractTokenFromMark(LEFT_ARROW)
                else input.extractTokenFromMark(LESS_THAN)

            // "->", "--", "-[", or "-"
            '-'  ->
                when (input.lookAhead()) {
                    '>'  -> input.advanceAndExtractTokenFromMark(RIGHT_ARROW)
                    '-'  -> input.advanceAndExtractTokenFromMark(DOUBLE_DASH)
                    '|'  -> input.advanceAndExtractTokenFromMark(LEFT_LINE_BRACKET)
                    else -> input.extractTokenFromMark(DASH)
                }

            // "|-" or "|"
            '|'  ->
                when (input.lookAhead()) {
                    '-'  -> input.advanceAndExtractTokenFromMark(RIGHT_LINE_BRACKET)
                    else -> input.extractTokenFromMark(VERTICAL_LINE)
                }

            // documentation or "/"
            '/'  ->
                when (input.lookAhead()) {
                    '*'  -> scanDocumentation()
                    else -> input.extractTokenFromMark(SLASH)
                }

            // String literal
            '"'  -> scanStringLiteral()

            // Character literal
            '\'' -> scanCharacterLiteral()

            // Identifier enclosed in back ticks
            '`'  -> scanQuotedIdentifier()

            // UUID literal
            '%'  -> scanUuid()

            // End of input sentinel
            END_OF_INPUT_CHAR -> input.extractTokenFromMark(END_OF_INPUT)

            // Miscellaneous
            else ->
                when {
                    // Scan an identifier.
                    isIdentifierStart(nextChar) -> scanIdentifier()

                    // Scan a numeric literal (integer or floating point).
                    isDigit(nextChar)           -> scanNumericLiteral()

                    // Error - nothing else it could be.
                    else                        -> input.extractTokenFromMark(INVALID_CHARACTER)
                }

        }
    }

    ////

    /**
     * @return true if the given [character] can be the first character of an identifier.
     */
    private fun isDigit(character: Char) =
        "0123456789".contains(character)

    /**
     * @return true if the given [character] can be part of an identifier (after its first character).
     */
    private fun isIdentifierPart(character: Char) =
        isIdentifierStart(character) || isDigit(character)

    /**
     * @return true if the given [character] can be the first character of an identifier.
     */
    private fun isIdentifierStart(character: Char) =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".contains(character)

    /**
     * @return true if the given [character] is whitespace.
     */
    private fun isWhitespace(character: Char) =
        " \t\r\n".contains(character)

    /**
     * Scans a character literal token after its opening "'" character has been marked and consumed in the tokenizer.
     */
    private fun scanCharacterLiteral(): DxlToken {

        var nextChar = input.lookAhead()

        while (nextChar != '\'') {

            if (nextChar == '\n' || nextChar == END_OF_INPUT_CHAR) {
                return input.extractTokenFromMark(UNTERMINATED_CHARACTER_LITERAL)
            }

            nextChar = input.advanceAndLookAhead()

        }

        return input.advanceAndExtractTokenFromMark(CHARACTER_LITERAL)

    }

    /**
     * Scans a block of documentation after its opening '/' character has been marked and consumed in
     * the tokenizer and the '*' character has been recognized.
     */
    private fun scanDocumentation(): DxlToken {

        // "*"
        input.advance()

        var nextChar = input.lookAhead()

        while (true) {

            if (nextChar == END_OF_INPUT_CHAR) {
                return input.extractTokenFromMark(UNTERMINATED_DOCUMENTATION)
            }

            input.advance()

            if (nextChar == '*' && input.lookAhead() == '/') {
                return input.advanceAndExtractTokenFromMark(DOCUMENTATION)
            }

            nextChar = input.lookAhead()

        }

    }

    /**
     * Scans a floating point literal after marking the first digit and consuming up until (but not including) the
     * first character seen that distinguishes it from an integer literal.
     */
    private fun scanFloatingPointLiteral(): DxlToken {

        var nextChar = input.lookAhead()

        if (nextChar == '.') {

            nextChar = input.advanceAndLookAhead()

            while (isDigit(nextChar) || '_' == nextChar) {
                nextChar = input.advanceAndLookAhead()
            }

        }

        if (nextChar == 'e' || nextChar == 'E') {

            nextChar = input.advanceAndLookAhead()

            if (nextChar == '-' || nextChar == '+') {
                nextChar = input.advanceAndLookAhead()
            }

            while (isDigit(nextChar) || '_' == nextChar) {
                nextChar = input.advanceAndLookAhead()
            }

        }

        if (nextChar == 'd' || nextChar == 'D' || nextChar == 'f' || nextChar == 'F') {
            input.advance()
        }

        return input.extractTokenFromMark(FLOATING_POINT_LITERAL)

    }

    /**
     * Scans an identifier after its first character has been marked and consumed.
     */
    private fun scanIdentifier(): DxlToken {

        while (isIdentifierPart(input.lookAhead())) {
            input.advance()
        }

        when (input.extractedTokenText()) {
            "alias" -> return input.extractTokenFromMark(ALIAS)
            "false" -> return input.extractTokenFromMark(BOOLEAN_LITERAL)
            "true" -> return input.extractTokenFromMark(BOOLEAN_LITERAL)
        }

        return input.extractTokenFromMark(IDENTIFIER)

    }

    /**
     * Scans a numerical literal (integer or floating point) after its first numeric digit has been marked and consumed.
     */
    private fun scanNumericLiteral(): DxlToken {

        var nextChar = input.lookAhead()
        while (isDigit(nextChar) || '_' == nextChar) {
            nextChar = input.advanceAndLookAhead()
        }

        if (FLOATING_POINT_STARTERS.contains(nextChar)) {
            return scanFloatingPointLiteral()
        }

        // TODO: hexadecimal

        // TODO: binary

        if (nextChar == 'l' || nextChar == 'L') {
            input.advance()
        }

        return input.extractTokenFromMark(INTEGER_LITERAL)

    }

    /**
     * Scans a backtick-quoted identifier after the open "`" character has been marked and consumed.
     */
    private fun scanQuotedIdentifier(): DxlToken {

        var nextChar = input.lookAhead()

        while (nextChar != '`') {

            if (nextChar == '\n' || nextChar == END_OF_INPUT_CHAR) {
                return input.extractTokenFromMark(UNTERMINATED_QUOTED_IDENTIFIER)
            }

            nextChar = input.advanceAndLookAhead()

        }

        return input.advanceAndExtractTokenFromMark(IDENTIFIER)

    }

    /**
     * Scans a string literal after the opening double quote character has been marked and consumed.
     */
    private fun scanStringLiteral(): DxlToken {

        var nextChar = input.lookAhead()

        while (nextChar != '"') {

            if (nextChar == '\n' || nextChar == END_OF_INPUT_CHAR) {
                return input.extractTokenFromMark(UNTERMINATED_STRING_LITERAL)
            }

            nextChar = input.advanceAndLookAhead()

        }

        return input.advanceAndExtractTokenFromMark(STRING_LITERAL)

    }

    /**
     * Scans a UUID after the initial '%' character has been consumed.
     */
    private fun scanUuid(): DxlToken {

        val uuidChars = "abcdefABCDEF1234567890"

        fun readUuidChars(nChars: Int): Boolean {
            for (i in 1..nChars) {
                val nextChar = input.lookAhead()
                if (!uuidChars.contains(nextChar)) {
                    return false
                }
                input.advance()
            }
            return true
        }

        fun readChar(ch: Char): Boolean {
            val nextChar = input.lookAhead()
            if (nextChar == ch) {
                input.advance()
                return true
            }
            return false
        }

        fun readDash() =
            readChar('-')

        fun readPercent() =
            readChar('%')

        if (!readUuidChars(1)) {
            return input.extractTokenFromMark(PERCENT)
        }

        val scanned = readUuidChars(7) && readDash() &&
                readUuidChars(4) && readDash() &&
                readUuidChars(4) && readDash() &&
                readUuidChars(4) && readDash() &&
                readUuidChars(12) && readPercent()

        if (scanned) {
            return input.extractTokenFromMark(UUID_LITERAL)
        }

        while (readUuidChars(1) || readDash()) {
            // keep consuming
        }

        readPercent()

        return input.extractTokenFromMark(INVALID_UUID_LITERAL)

    }

    ////

    companion object {

        /** Characters that distinguish a floating point literal from an integer literal. */
        private val FLOATING_POINT_STARTERS = setOf('.', 'd', 'D', 'e', 'E', 'f', 'F')

    }

}

//---------------------------------------------------------------------------------------------------------------------

