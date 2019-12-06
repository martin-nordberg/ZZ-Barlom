//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.dxl.parsing

import i.barlom.infrastructure.dxl.scanning.DxlScanner
import i.barlom.infrastructure.dxl.scanning.DxlToken
import i.barlom.infrastructure.dxl.scanning.EDxlTokenType
import i.barlom.infrastructure.dxl.scanning.StringTokenizer

//---------------------------------------------------------------------------------------------------------------------

/**
 * Implementation of DxlExpectedTokenBuffer for testing and consuming tokens from a look-ahead buffer.
 */
internal class DxlExpectedTokenBufferImpl(
    code: String
) : DxlExpectedTokenBuffer,
    DxlTokenBuffer by DxlTokenBufferImpl(DxlScanner(StringTokenizer(code))) {

    override fun expected(description: String): Nothing {
        val token = lookAhead(1)

        val msg = if (token == null) "Expected $description."
        else "Expected $description at (${token.line},${token.column}); found '${token.text}'"

        throw IllegalArgumentException(msg)
    }

    override fun expected(vararg tokenTypes: EDxlTokenType): Nothing {

        if (tokenTypes.size == 1) {
            expected(tokenTypes[0].text)
        }

        val tokenText = tokenTypes.joinToString(", ") { t -> t.text }
        expected("one of { $tokenText }")

    }

    override fun expected(description: String, vararg tokenTypes: EDxlTokenType): Nothing {

        if (tokenTypes.size == 1) {
            expected("$description - ${tokenTypes[0].text}.")
        }

        val tokenText = tokenTypes.joinToString(", ") { t -> t.text }
        expected("$description - one of { $tokenText }")

    }

    override fun hasLookAheadOnNewLine(): Boolean {
        val lastToken = lookAhead(0)
        return (lastToken != null && hasLookAhead() && lastToken.line < lookAhead(1)!!.line) ||
                hasLookAhead(EDxlTokenType.END_OF_INPUT)
    }

    override fun read(tokenType: EDxlTokenType): DxlToken {

        if (!hasLookAhead(tokenType)) {
            expected(tokenType)
        }

        return read()

    }

    override fun readOneOf(vararg tokenTypes: EDxlTokenType): DxlToken {

        for (tokenType in tokenTypes) {

            if (hasLookAhead(tokenType)) {
                return read()
            }

        }

        expected(*tokenTypes)

    }

}

//---------------------------------------------------------------------------------------------------------------------

