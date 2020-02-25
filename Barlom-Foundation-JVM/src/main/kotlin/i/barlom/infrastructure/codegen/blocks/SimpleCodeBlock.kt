//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.codegen.blocks

import i.barlom.infrastructure.codegen.builders.CodeStringBuilder
import i.barlom.infrastructure.codegen.chunks.ICodeChunk

//---------------------------------------------------------------------------------------------------------------------


internal class SimpleCodeBlock(chunks: List<ICodeChunk>) :
    ICodeChunk {

    private var codeChunks = mutableListOf<ICodeChunk>()

    ////

    init {
        for (chunk in chunks) {
            add(chunk)
        }
    }

    fun add(codeChunk: ICodeChunk) {
        codeChunks.add(codeChunk)
    }

    override fun writeDebugString(output: CodeStringBuilder) {

        output.append( "SimpleBlock {")
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

        for (codeChunk in codeChunks) {
            codeChunk.writeCode(output)
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------
