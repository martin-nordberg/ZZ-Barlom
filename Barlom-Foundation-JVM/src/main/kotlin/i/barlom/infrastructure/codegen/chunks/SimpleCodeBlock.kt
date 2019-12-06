//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.codegen.chunks

import i.barlom.infrastructure.codegen.builders.CodeStringBuilder

//---------------------------------------------------------------------------------------------------------------------


internal class SimpleCodeBlock(chunks: List<ICodeChunk>) :
    ICodeChunk {

    private var codeChunks = mutableListOf<ICodeChunk>()

    override var hasNestedBlocks: Boolean = false

    ////

    init {
        for (chunk in chunks) {
            add(chunk)
        }
    }

    fun add(codeChunk: ICodeChunk) {
        codeChunks.add(codeChunk)
        hasNestedBlocks = hasNestedBlocks || codeChunk !is SimpleTextCodeChunk && codeChunk !is SimpleCodeBlock
    }

    override fun writeCode(output: CodeStringBuilder) {

        for (codeChunk in codeChunks) {
            codeChunk.writeCode(output)
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------
