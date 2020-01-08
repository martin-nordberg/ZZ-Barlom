//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.core

import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

abstract class DxlItem(
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

