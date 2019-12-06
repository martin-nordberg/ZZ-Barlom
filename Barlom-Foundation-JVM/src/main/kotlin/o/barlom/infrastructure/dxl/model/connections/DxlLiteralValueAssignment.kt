//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.expressions.literals.DxlLiteralExpression
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlLiteralValueAssignment(
    origin: DxlOrigin,
    val expression: DxlLiteralExpression
) : DxlValueAssignment(origin) {

    override fun writeCode(output: CodeWriter) {
        output.write("= ")
        expression.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

