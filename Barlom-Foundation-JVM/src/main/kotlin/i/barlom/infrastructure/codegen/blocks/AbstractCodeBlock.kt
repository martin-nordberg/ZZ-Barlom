//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.codegen.blocks

import i.barlom.infrastructure.codegen.builders.CodeStringBuilder
import i.barlom.infrastructure.codegen.chunks.ICodeChunk

//---------------------------------------------------------------------------------------------------------------------


internal abstract class AbstractCodeBlock
    : ICodeChunk {

    private var codeChunks = mutableListOf<ICodeChunk>()

    abstract val debugNodeName: String

    ////

    fun add(codeChunk: ICodeChunk) {
        codeChunks.add(codeChunk)
    }

    protected open fun getPrefix(density: ECodeDensity): String {
        return ""
    }

    abstract fun getSeparator(density: ECodeDensity): String

    protected open fun getSuffix(density: ECodeDensity): String {
        return ""
    }

    fun hasContent() =
        codeChunks.isNotEmpty()

    override fun writeDebugString(output:CodeStringBuilder) {

        output.append(this.debugNodeName)
        output.append(" {")
        output.appendNewLine()

        output.indented {
            for (codeChunk in codeChunks) {
                codeChunk.writeDebugString(output)
                output.appendNewLine()
            }
        }


        output.append("}")

    }

    override fun writeCode(output: CodeStringBuilder) {

        if ( codeChunks.size == 1 ) {
            writeCode(output, ECodeDensity.ALL_ONE_LINE)
        }
        else {

            // First try all one line.
            var written = output.lineLengthLimited {
                writeCode(this, ECodeDensity.ALL_ONE_LINE)
            }

            // If too long, use extra line or nested and indented.
            if (!written) {
                written = output.lineLengthLimited {
                    writeCode(this, ECodeDensity.EXTRA_LINE)
                }

                if (!written) {
                    writeCode(output, ECodeDensity.NEW_LINE_PER_ITEM)
                }
            }

        }

    }

    protected fun writeCode(output: CodeStringBuilder, density: ECodeDensity) {

        val prefix = getPrefix(density)
        val separator = getSeparator(density)
        val suffix = getSuffix(density)

        output.append(prefix)

        var sep = ""

        when (density) {

            ECodeDensity.ALL_ONE_LINE      ->
                for (codeChunk in codeChunks) {
                    output.append(sep)
                    codeChunk.writeCode(output)
                    sep = separator
                }

            ECodeDensity.EXTRA_LINE        -> {

                output.appendNewLine()

                output.indented {
                    for (codeChunk in codeChunks) {
                        output.append(sep)
                        codeChunk.writeCode(output)
                        sep = separator
                    }
                }

                output.appendNewLine()

            }

            ECodeDensity.NEW_LINE_PER_ITEM -> {

                output.indented {
                    for (codeChunk in codeChunks) {
                        output.append(sep)
                        output.appendNewLine()
                        codeChunk.writeCode(output)
                        sep = separator
                    }
                }

                output.appendNewLine()

            }

        }

        output.append(suffix)

    }

}

//---------------------------------------------------------------------------------------------------------------------
