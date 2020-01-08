//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.names

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

abstract class DxlName(
    origin: DxlOrigin
) : DxlOptName(origin) {

    abstract val text : String

    override fun writeCode(output: CodeWriter) {
        output.write(text)
    }

}

//---------------------------------------------------------------------------------------------------------------------

