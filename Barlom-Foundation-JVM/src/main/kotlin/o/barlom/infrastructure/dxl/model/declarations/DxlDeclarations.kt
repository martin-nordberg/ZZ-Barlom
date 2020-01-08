//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.declarations

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlDeclarations(
    private val declarations: List<DxlDeclaration> = listOf()
) : DxlItem(if (declarations.isNotEmpty()) declarations[0].origin else DxlNullOrigin),
    Iterable<DxlDeclaration> by declarations {

    fun isEmpty() =
        declarations.isEmpty()

    fun isNotEmpty() =
        declarations.isNotEmpty()

    override fun writeCode(output: CodeWriter) {

        output.writeNewLineSeparated(declarations) { c ->
            c.writeCode(this)
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

