//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.expressions.literals

import o.barlom.infrastructure.dxl.model.expressions.DxlExpression
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.documentation.DxlDocumentation
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

abstract class DxlLiteralExpression(
    origin: DxlOrigin,
    documentation: DxlDocumentation
) : DxlExpression(origin, documentation) {

    abstract val text: String

    final override fun writeCode(output: CodeWriter) {
        output.write(text)
    }

}

//---------------------------------------------------------------------------------------------------------------------

