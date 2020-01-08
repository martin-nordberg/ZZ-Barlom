//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.declarations.DxlDeclarations

//---------------------------------------------------------------------------------------------------------------------

class DxlContent(
    origin: DxlOrigin,
    val declarations: DxlDeclarations
) : DxlOptContent(origin) {

    override fun writeCode(output: CodeWriter) {

        if (declarations.isNotEmpty()) {

            output.write(" ")
            output.writeBraceBlankBlock(declarations) { d ->
                d.writeCode(this)
            }

        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

