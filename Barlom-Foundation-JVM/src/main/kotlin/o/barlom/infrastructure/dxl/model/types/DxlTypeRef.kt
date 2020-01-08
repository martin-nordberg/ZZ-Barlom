//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.types

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.names.DxlName

//---------------------------------------------------------------------------------------------------------------------

class DxlTypeRef(
    origin: DxlOrigin,
    val typeName: DxlName
) : DxlOptTypeRef(origin) {

    override fun writeCode(output: CodeWriter) {
        output.write(": ")
        typeName.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

