//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.dxl.parsing

import i.barlom.infrastructure.dxl.scanning.DxlScanner
import i.barlom.infrastructure.dxl.scanning.DxlToken
import i.barlom.infrastructure.dxl.scanning.EDxlTokenType

//---------------------------------------------------------------------------------------------------------------------

/**
 * Implementation of DxlTokenBuffer using a ring buffer with fixed size.
 */
internal class DxlTokenBufferImpl(
    private val scanner: DxlScanner
) : DxlTokenBuffer {

    /** The index of the first unread token in the ring buffer. */
    private var first = 0

    /** The number of unconsumed tokens in the ring buffer. */
    private var length = 0

    /** The size of the ring. */
    private var size = 2

    /** The ring buffer of tokens consumed and waiting to be consumed. */
    private var tokens = arrayOfNulls<DxlToken>(size)

    ////

    /**
     * Reads tokens from the lexer into this buffer until there are at least [count] tokens of look-ahead and
     * one token of look-behind.
     */
    private fun fill(count: Int) {

        while (count > size - 1) {
            tokens += arrayOfNulls<DxlToken>(size)
            size *= 2
        }

        while (length < count) {
            tokens[ringIndex(first + length)] = scanner.scan()
            length += 1
        }

    }

    /**
     * Advances the indexes after consuming [count] tokens from the buffer.
     */
    private fun advance(count: Int) {
        first = ringIndex(first + count)
        length -= count
    }

    /**
     * Performs the modulo arithmetic needed to keep an [index] within the ring buffer range.
     */
    private fun ringIndex(index: Int) =
        index % size

    ////

    override fun consume(count: Int) {
        fill(count)
        advance(count)
    }

    override fun consumeWhen(vararg tokenTypes: EDxlTokenType): Boolean {

        fill(tokenTypes.size)

        for (i in 0 until tokenTypes.size) {
            if (tokens[ringIndex(first + i)]?.type != tokenTypes[i]) {
                return false
            }
        }

        consume(tokenTypes.size)
        return true

    }

    override fun hasLookAhead() =
        lookAhead(1)?.type != null

    override fun hasLookAhead(tokenType: EDxlTokenType) =
        tokenType == lookAhead(1)?.type

    override fun hasLookAhead(count: Int, tokenType: EDxlTokenType) =
        tokenType == lookAhead(count)?.type

    override fun lookAhead(count: Int): DxlToken? {
        fill(count)
        return tokens[ringIndex(first + count + size - 1)]
    }

    override fun read(): DxlToken {
        val result = lookAhead(1) ?: throw IllegalStateException("Unexpected end of input.")
        advance(1)
        return result
    }

}

//---------------------------------------------------------------------------------------------------------------------

