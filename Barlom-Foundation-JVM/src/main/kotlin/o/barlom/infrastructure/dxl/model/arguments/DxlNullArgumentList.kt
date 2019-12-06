//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.arguments

import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

object DxlNullArgumentList
    : DxlArgumentList(DxlNullOrigin) {

    override fun writeCode(output: CodeWriter) {}

}

//---------------------------------------------------------------------------------------------------------------------

