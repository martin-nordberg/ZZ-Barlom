//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.documentation

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin

//---------------------------------------------------------------------------------------------------------------------

/**
 * Null object implementation when optional documentation is not present.
 */
object DxlNoDocumentation
    : DxlOptDocumentation(DxlNullOrigin) {

    override fun writeCode(output: CodeWriter) {
    }

}

//---------------------------------------------------------------------------------------------------------------------

