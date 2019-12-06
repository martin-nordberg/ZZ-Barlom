//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connectedelements

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

abstract class DxlConnectedElement(
    val origin: DxlOrigin
) {

    val code: String
        get() {
            val output = CodeWriter()
            writeCode(output)
            return output.toString()
        }

    abstract fun writeCode(output: CodeWriter)

}

//---------------------------------------------------------------------------------------------------------------------

