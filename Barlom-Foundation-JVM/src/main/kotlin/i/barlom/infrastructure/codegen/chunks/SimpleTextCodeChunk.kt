//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.codegen.chunks

import i.barlom.infrastructure.codegen.builders.CodeStringBuilder

//---------------------------------------------------------------------------------------------------------------------


internal class SimpleTextCodeChunk(
    private var code: String
) : ICodeChunk {

    override fun writeDebugString(output: CodeStringBuilder) {
        output.append("Text:'$code'")
    }

    override fun writeCode(output: CodeStringBuilder) {
        output.append(code)
    }

}

//---------------------------------------------------------------------------------------------------------------------
