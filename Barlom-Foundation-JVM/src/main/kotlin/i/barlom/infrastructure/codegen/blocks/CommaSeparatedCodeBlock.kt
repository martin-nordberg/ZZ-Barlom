//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.codegen.blocks

import i.barlom.infrastructure.codegen.chunks.ICodeChunk

//---------------------------------------------------------------------------------------------------------------------


internal class CommaSeparatedCodeBlock(chunks: List<ICodeChunk>) :
    AbstractCodeBlock() {

    init {
        for (chunk in chunks) {
            add(chunk)
        }
    }

    override val debugNodeName = "CommaSeparatedBlock"

    override fun getSeparator(density: ECodeDensity): String =
        when (density) {
            ECodeDensity.ALL_ONE_LINE      -> ", "
            ECodeDensity.EXTRA_LINE        -> ", "
            ECodeDensity.NEW_LINE_PER_ITEM -> ","
        }

}

//---------------------------------------------------------------------------------------------------------------------
