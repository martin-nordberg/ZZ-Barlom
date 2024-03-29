//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.declarations

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem

//---------------------------------------------------------------------------------------------------------------------

class DxlTopLevel(
    val aliases: DxlAliases,
    val declarations: DxlDeclarations
) : DxlItem( declarations.origin ) {

    override fun writeCode(output: CodeWriter) {
        aliases.writeCode(output)
        declarations.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

