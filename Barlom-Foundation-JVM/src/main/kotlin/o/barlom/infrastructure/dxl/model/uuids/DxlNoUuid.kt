//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.uuids

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin

//---------------------------------------------------------------------------------------------------------------------

object DxlNoUuid
    : DxlOptUuid( DxlNullOrigin ) {

    override fun writeCode(output: CodeWriter) {
    }

}

//---------------------------------------------------------------------------------------------------------------------

