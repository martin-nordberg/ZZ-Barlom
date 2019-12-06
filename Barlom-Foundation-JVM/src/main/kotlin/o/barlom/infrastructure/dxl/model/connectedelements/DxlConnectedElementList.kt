//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connectedelements

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.elements.DxlElement
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlConnectedElementList(
    origin: DxlOrigin,
    val connectedElements: List<DxlElement>
) : DxlConnectedElement(origin) {

    override fun writeCode(output: CodeWriter) =
        output.writeSqBracketCommaBlock( connectedElements) { e ->
            e.writeCode(this)
        }

}

//---------------------------------------------------------------------------------------------------------------------

