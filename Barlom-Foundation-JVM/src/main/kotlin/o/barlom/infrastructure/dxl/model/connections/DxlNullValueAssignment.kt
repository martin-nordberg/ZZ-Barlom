//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

object DxlNullValueAssignment :
    DxlValueAssignment(DxlNullOrigin) {

    override fun writeCode(output: CodeWriter) {}

}

//---------------------------------------------------------------------------------------------------------------------

