//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.documentation

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlDocumentation(
    origin: DxlOrigin,
    val text : String
) : DxlOptDocumentation(origin) {

    override fun writeCode(output: CodeWriter) {
        output.write(text)
        output.writeNewLine()
    }

}

//---------------------------------------------------------------------------------------------------------------------

