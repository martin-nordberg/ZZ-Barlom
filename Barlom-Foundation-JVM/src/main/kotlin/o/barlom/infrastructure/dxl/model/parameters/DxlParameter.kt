//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.parameters

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.expressions.DxlNoValue
import o.barlom.infrastructure.dxl.model.expressions.DxlOptExpression
import o.barlom.infrastructure.dxl.model.names.DxlSimpleName
import o.barlom.infrastructure.dxl.model.types.DxlOptTypeRef

//---------------------------------------------------------------------------------------------------------------------

class DxlParameter(
    origin: DxlOrigin,
    val name: DxlSimpleName,
    val typeRef: DxlOptTypeRef,
    val defaultValue: DxlOptExpression
) : DxlItem(origin) {

    override fun writeCode(output: CodeWriter) {

        output.write(name.text)

        typeRef.writeCode(output)

        if (defaultValue !is DxlNoValue) {
            output.write(" = ")
            defaultValue.writeCode(output)
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

