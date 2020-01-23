//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.elements.DxlElement

//---------------------------------------------------------------------------------------------------------------------

class DxlConnection(
    origin: DxlOrigin,
    val direction: EDxlConnectionDirection,
    val element: DxlElement,
    val connectedConcept: DxlElement
) : DxlItem(origin) {

    override fun writeCode(output: CodeWriter) {

        when (direction) {
            EDxlConnectionDirection.BIDIRECTIONAL,
            EDxlConnectionDirection.DIRECTED_LEFT  -> output.write("<-")
            EDxlConnectionDirection.UNDIRECTED,
            EDxlConnectionDirection.DIRECTED_RIGHT -> output.write("--")
        }

        output.write("-|")

        element.writeCode(output)

        output.write("|-")

        when (direction) {
            EDxlConnectionDirection.UNDIRECTED,
            EDxlConnectionDirection.DIRECTED_LEFT  -> output.write("--")
            EDxlConnectionDirection.BIDIRECTIONAL,
            EDxlConnectionDirection.DIRECTED_RIGHT -> output.write("->")
        }

        output.write("[")

        connectedConcept.writeCode(output)

        output.write("]")

    }

}

//---------------------------------------------------------------------------------------------------------------------

