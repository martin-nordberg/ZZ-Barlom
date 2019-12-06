//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.expressions.references

import o.barlom.infrastructure.dxl.model.arguments.DxlArgumentList
import o.barlom.infrastructure.dxl.model.connections.DxlValueAssignment
import o.barlom.infrastructure.dxl.model.documentation.DxlDocumentation
import o.barlom.infrastructure.dxl.model.expressions.DxlExpression
import o.barlom.infrastructure.dxl.model.names.DxlName
import o.barlom.infrastructure.dxl.model.names.DxlNullName
import o.barlom.infrastructure.dxl.model.uuids.DxlUuid
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlReferenceExpression(
    documentation: DxlDocumentation,
    val qualifiedName: DxlName,
    val uuid: DxlUuid,
    val arguments: DxlArgumentList,
    val valueAssignment: DxlValueAssignment
) : DxlExpression(
    if (qualifiedName is DxlNullName) uuid.origin else qualifiedName.origin,
    documentation
) {

    override fun writeCode(output: CodeWriter) {
        output.write(documentation.text)

        output.write(if (qualifiedName is DxlNullName) uuid.text else qualifiedName.text)

        arguments.writeCode(output)

        valueAssignment.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

