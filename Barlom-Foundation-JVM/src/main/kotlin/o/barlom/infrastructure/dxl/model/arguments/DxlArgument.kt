//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.arguments

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.expressions.DxlExpression
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlArgument(
    val origin: DxlOrigin,
    val name: DxlArgumentName,
    val expression: DxlExpression
) {

    val code: String
        get() {
            val output = CodeWriter()
            writeCode(output)
            return output.toString()
        }

    fun writeCode(output: CodeWriter) {

        if (name is DxlSpecifiedArgumentName) {
            output.write( name.text, " = ")
        }

        expression.writeCode(output)

    }

}

//---------------------------------------------------------------------------------------------------------------------

