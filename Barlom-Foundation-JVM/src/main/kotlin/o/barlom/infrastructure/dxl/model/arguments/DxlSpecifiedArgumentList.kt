//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.arguments

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlSpecifiedArgumentList(
    origin: DxlOrigin,
    val arguments: List<DxlArgument>
) : DxlArgumentList(origin) {

    override fun writeCode(output: CodeWriter) =
        output.writeParenCommaBlock(arguments) { a ->
            a.writeCode(this)
        }

}

//---------------------------------------------------------------------------------------------------------------------

