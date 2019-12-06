//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.parameters

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.connections.DxlImplicitConnection
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.names.DxlSimpleName

//---------------------------------------------------------------------------------------------------------------------

class DxlParameter(
    val origin: DxlOrigin,
    val name: DxlSimpleName,
    val connection: DxlImplicitConnection
) {

    val code: String
        get() {
            val output = CodeWriter()
            writeCode(output)
            return output.toString()
        }

    fun writeCode(output: CodeWriter) {
        output.write(name.text)
        connection.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

