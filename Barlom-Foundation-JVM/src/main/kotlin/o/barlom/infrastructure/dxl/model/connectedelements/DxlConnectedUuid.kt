//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connectedelements

import o.barlom.infrastructure.dxl.model.uuids.DxlKnownUuid
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlConnectedUuid(
    val uuid: DxlKnownUuid
) : DxlConnectedElement(uuid.origin) {

    override fun writeCode(output: CodeWriter) {
        output.write(uuid.text)
    }

}

//---------------------------------------------------------------------------------------------------------------------

