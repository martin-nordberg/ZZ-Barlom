//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.codegen

import i.barlom.infrastructure.codegen.blocks.*
import i.barlom.infrastructure.codegen.builders.CodeStringBuilder
import i.barlom.infrastructure.codegen.chunks.ICodeChunk
import i.barlom.infrastructure.codegen.chunks.NewLineCodeChunk
import i.barlom.infrastructure.codegen.chunks.SimpleTextCodeChunk

//---------------------------------------------------------------------------------------------------------------------

class CodeWriter() {

    /** The sequence of code chunks written so far. */
    private val codeChunks = mutableListOf<ICodeChunk>()

    ////

    /**
     * Converts the code writer output to a simple string.
     */
    override fun toString(): String =
        toCodeString()

    /**
     * Converts the code writer output to a simple string.
     */
    fun toCodeString(
        indentWhiteSpace: String = "  ",
        maxLineWidth: Int = 100
    ): String {

        val output = CodeStringBuilder(indentWhiteSpace, maxLineWidth)

        for (chunk in codeChunks) {
            chunk.writeCode(output)
        }

        return output.toString()

    }

    /**
     * Writes [snippets] of code to the current line.
     */
    fun write(vararg snippets: String) {

        for (snippet in snippets) {
            require(!snippet.contains("\n"))
            if (snippet.isNotEmpty()) {
                codeChunks.add(SimpleTextCodeChunk(snippet))
            }
        }

    }

    /**
     * Outputs a sequence of items separated by whitespace.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeBlankSeparated(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(BlankSeparatedCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items enclosed in braces and separated by blanks or new lines.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeBraceBlankBlock(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(BraceBlankCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items enclosed in braces and separated by semicolons or new lines.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeBraceSemicolonBlock(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(BraceSemicolonCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items separated by commas.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeCommaSeparated(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(CommaSeparatedCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items separated by blanks and enclosed in line brackets ("-|" ... "|-").
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeLineBracketBlankBlock(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(LineBracketBlankCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items separated by line breaks.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeNewLineSeparated(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(NewLineSeparatedCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items separated by line breaks all indented one level below the current line.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeNewLineSeparatedIndented(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(NewLineSeparatedIndentedCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items separated by commas and enclosed in parentheses.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeParenCommaBlock(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(ParenCommaCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items separated by semicolons or line breaks.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeSemicolonSeparated(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(SemicolonSeparatedCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items separated by blanks and enclosed in square brackets.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeSqBracketBlankBlock(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(SqBracketBlankCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Outputs a sequence of items separated by commas and enclosed in square brackets.
     * @param items a collection of items to output.
     * @param itemOut the code to output a single item.
     */
    fun <T> writeSqBracketCommaBlock(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit) {
        codeChunks.add(SqBracketCommaCodeBlock(writeBracketedChunks(items, itemOut)))
    }

    /**
     * Writes an explicit new line to the output.
     */
    fun writeNewLine() {
        codeChunks.add(NewLineCodeChunk())
    }

    ////

    private fun <T> writeBracketedChunks(items: Iterable<T>, itemOut: CodeWriter.(item: T) -> Unit): MutableList<ICodeChunk> {

        val blockChunks = mutableListOf<ICodeChunk>()

        for (item in items) {
            val itemOutput = CodeWriter()
            itemOutput.itemOut(item)
            if (itemOutput.codeChunks.isNotEmpty()) {
                blockChunks.add(SimpleCodeBlock(itemOutput.codeChunks))
            }
        }
        return blockChunks

    }

}

//---------------------------------------------------------------------------------------------------------------------

