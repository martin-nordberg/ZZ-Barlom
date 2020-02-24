//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.declarations

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.names.DxlName
import o.barlom.infrastructure.dxl.model.names.DxlSimpleName

//---------------------------------------------------------------------------------------------------------------------

class DxlAlias(
    origin: DxlOrigin,
    val name: DxlSimpleName,
    val qualifiedName: DxlName
) : DxlItem(origin) {

    override fun writeCode(output: CodeWriter) {
        output.write( "alias ")
        name.writeCode(output)
        output.write(" = ")
        qualifiedName.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

