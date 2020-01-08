//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.parameters

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlParameters(
    origin: DxlOrigin,
    private val parameters: List<DxlParameter>
) : DxlOptParameters(origin),
    Iterable<DxlParameter> by parameters {

    override fun writeCode(output: CodeWriter) =
        output.writeParenCommaBlock(parameters) { p ->
            p.writeCode(this)
        }

}

//---------------------------------------------------------------------------------------------------------------------

