//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.names

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin

//---------------------------------------------------------------------------------------------------------------------

object DxlNoName
    : DxlOptName(DxlNullOrigin) {
    override fun writeCode(output: CodeWriter) {}
}

//---------------------------------------------------------------------------------------------------------------------

