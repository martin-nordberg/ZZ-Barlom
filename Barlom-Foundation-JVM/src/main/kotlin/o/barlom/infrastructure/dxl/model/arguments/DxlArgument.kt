//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.arguments

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.expressions.DxlExpression
import o.barlom.infrastructure.dxl.model.names.DxlName
import o.barlom.infrastructure.dxl.model.names.DxlOptName
import o.barlom.infrastructure.dxl.model.names.DxlSimpleName

//---------------------------------------------------------------------------------------------------------------------

class DxlArgument(
    val name: DxlOptName,
    val value: DxlExpression
) : DxlItem(if (name is DxlSimpleName) name.origin else value.origin) {

    override fun writeCode(output: CodeWriter) {

        if (name is DxlName) {
            output.write(name.text, "=")
        }

        value.writeCode(output)

    }

}

//---------------------------------------------------------------------------------------------------------------------

