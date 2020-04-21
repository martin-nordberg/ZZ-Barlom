//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.concepts

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.declarations.DxlDeclaration
import o.barlom.infrastructure.dxl.model.documentation.DxlOptDocumentation
import o.barlom.infrastructure.dxl.model.elements.DxlElement

//---------------------------------------------------------------------------------------------------------------------

class DxlConceptDeletion(
    origin: DxlOrigin,
    documentation: DxlOptDocumentation,
    val element: DxlElement
) : DxlDeclaration(origin, documentation) {

    override fun writeCode(output: CodeWriter) {

        documentation.writeCode(output)

        output.write("!")
        output.writeSqBracketBlankBlock(listOf(element)) { e ->
            e.writeCode(this)
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

