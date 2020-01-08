//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.declarations

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem

//---------------------------------------------------------------------------------------------------------------------

class DxlTopLevel(
    // TODO: namespace and aliases
    val declarations: DxlDeclarations
) : DxlItem( declarations.origin ) {

    override fun writeCode(output: CodeWriter) {
        // TODO: namespace and aliases
        declarations.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

