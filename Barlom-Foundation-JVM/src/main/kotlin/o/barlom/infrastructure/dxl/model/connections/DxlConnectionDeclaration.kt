//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.declarations.DxlDeclaration
import o.barlom.infrastructure.dxl.model.documentation.DxlOptDocumentation
import o.barlom.infrastructure.dxl.model.elements.DxlElement

//---------------------------------------------------------------------------------------------------------------------

class DxlConnectionDeclaration(
    origin: DxlOrigin,
    documentation: DxlOptDocumentation,
    val element: DxlElement
) : DxlDeclaration(origin, documentation) {

    override fun writeCode(output: CodeWriter) {
        documentation.writeCode(output)
        output.write("-[")
        element.writeCode(output)
        output.write("]-")
    }

}

//---------------------------------------------------------------------------------------------------------------------

