//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlConnections(
    private val connections: List<DxlConnection> = listOf()
) : DxlItem(if (connections.isNotEmpty()) connections[0].origin else DxlNullOrigin),
    Iterable<DxlConnection> by connections {

    fun isEmpty() =
        connections.isEmpty()

    fun isNotEmpty() =
        connections.isNotEmpty()

    override fun writeCode(output: CodeWriter) {

        if ( !isEmpty()) {
            output.writeNewLineSeparatedIndented(connections) { c ->
                c.writeCode(this)
            }
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

