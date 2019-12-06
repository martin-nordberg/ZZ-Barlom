//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.codegen.chunks

import i.barlom.infrastructure.codegen.builders.CodeStringBuilder

//---------------------------------------------------------------------------------------------------------------------


internal class NewLineCodeChunk
    : ICodeChunk {

    override val hasNestedBlocks = false

    override fun writeCode(output: CodeStringBuilder) {
        output.appendNewLine()
    }

}

//---------------------------------------------------------------------------------------------------------------------
