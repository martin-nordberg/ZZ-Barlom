//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.parameters

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlSpecifiedParameterList(
    origin: DxlOrigin,
    val parameters: List<DxlParameter>
) : DxlParameterList(origin) {

    override fun writeCode(output: CodeWriter) =
        output.writeParenCommaBlock(parameters) { p ->
            p.writeCode(this)
        }

}

//---------------------------------------------------------------------------------------------------------------------

