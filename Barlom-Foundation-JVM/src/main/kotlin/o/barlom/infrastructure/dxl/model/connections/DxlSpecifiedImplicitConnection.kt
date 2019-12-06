//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.connectedelements.DxlConnectedElement
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlSpecifiedImplicitConnection(
    origin: DxlOrigin,
    val connectedElement: DxlConnectedElement
) : DxlImplicitConnection(origin) {

    override fun writeCode(output: CodeWriter) {
        output.write(": ")
        connectedElement.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

