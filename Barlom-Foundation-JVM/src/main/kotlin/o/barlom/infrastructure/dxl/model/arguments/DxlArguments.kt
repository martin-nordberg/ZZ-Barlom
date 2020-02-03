//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.arguments

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlArguments(
    origin: DxlOrigin,
    private val arguments: List<DxlArgument>
) : DxlOptArguments(origin),
    Iterable<DxlArgument> by arguments {

    override fun writeCode(output: CodeWriter) =
        output.writeParenCommaBlock(arguments) { p ->
            p.writeCode(this)
        }

}

//---------------------------------------------------------------------------------------------------------------------

