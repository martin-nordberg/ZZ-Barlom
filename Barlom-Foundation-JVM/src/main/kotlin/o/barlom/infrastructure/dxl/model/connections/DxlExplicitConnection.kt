//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.dxl.model.connectedelements.DxlConnectedElement
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlExplicitConnection(
    val connector: DxlConnector,
    val connectedElement: DxlConnectedElement
) : DxlConnection(connector.origin) {

    override fun writeCode(output: CodeWriter) {
        connector.writeCode(output)
        connectedElement.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

