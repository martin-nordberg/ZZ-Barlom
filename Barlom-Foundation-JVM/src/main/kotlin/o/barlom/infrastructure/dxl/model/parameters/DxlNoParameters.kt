//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.parameters

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin

//---------------------------------------------------------------------------------------------------------------------

object DxlNoParameters
    : DxlOptParameters(DxlNullOrigin) {

    override fun writeCode(output: CodeWriter) {}

}

//---------------------------------------------------------------------------------------------------------------------

