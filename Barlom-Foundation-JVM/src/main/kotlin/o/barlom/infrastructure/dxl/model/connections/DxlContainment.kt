//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.elements.DxlElement
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlContainment(
    origin: DxlOrigin,
    val containedElements: List<DxlElement>
) : DxlConnection(origin) {

    override fun writeCode(output: CodeWriter) =
        output.writeBraceSemicolonBlock(containedElements) { e ->
            e.writeCode(this)
        }

}

//---------------------------------------------------------------------------------------------------------------------

