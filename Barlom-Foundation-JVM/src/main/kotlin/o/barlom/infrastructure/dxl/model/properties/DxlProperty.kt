//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.properties

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.expressions.DxlNoValue
import o.barlom.infrastructure.dxl.model.expressions.DxlOptExpression
import o.barlom.infrastructure.dxl.model.names.DxlOptName
import o.barlom.infrastructure.dxl.model.names.DxlSimpleName
import o.barlom.infrastructure.dxl.model.types.DxlOptTypeRef

//---------------------------------------------------------------------------------------------------------------------

class DxlProperty(
    val name: DxlSimpleName,
    val revisedName: DxlOptName,
    val typeRef: DxlOptTypeRef,
    val value: DxlOptExpression
) : DxlItem(name.origin) {

    override fun writeCode(output: CodeWriter) {
        output.write("~ ", name.text)
        if (revisedName is DxlSimpleName) {
            output.write("^", revisedName.text)
        }
        typeRef.writeCode(output)
        if (value !is DxlNoValue) {
            output.write(" = ")
            value.writeCode(output)
        }
    }

}

//---------------------------------------------------------------------------------------------------------------------

