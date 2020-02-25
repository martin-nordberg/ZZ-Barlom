//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.codegen.blocks

import i.barlom.infrastructure.codegen.builders.CodeStringBuilder
import i.barlom.infrastructure.codegen.chunks.ICodeChunk

//---------------------------------------------------------------------------------------------------------------------


internal class NewLineSeparatedIndentedCodeBlock(chunks: List<ICodeChunk>) :
    AbstractCodeBlock() {

    init {
        for (chunk in chunks) {
            add(chunk)
        }
    }

    override val debugNodeName = "NewLineSeparatedIndentedBlock"

    override fun writeCode(output: CodeStringBuilder) {
        super.writeCode(output, ECodeDensity.NEW_LINE_PER_ITEM)
    }

    override fun getSeparator(density: ECodeDensity): String =
        ""

}

//---------------------------------------------------------------------------------------------------------------------
