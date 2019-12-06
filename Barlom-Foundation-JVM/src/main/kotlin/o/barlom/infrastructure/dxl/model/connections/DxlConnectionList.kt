//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlConnectionList(
    val connections: List<DxlConnection> = listOf()
) {

    val code: String
        get() {
            val output = CodeWriter()
            writeCode(output)
            return output.toString()
        }

    val origin: DxlOrigin =
        if (connections.isNotEmpty()) connections[0].origin
        else DxlNullOrigin

    fun writeCode(output: CodeWriter) {

        if ( !connections.isEmpty() ) {
            output.write(" ")
        }

        output.writeBlankSeparated(connections) { c ->
            c.writeCode(this)
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

