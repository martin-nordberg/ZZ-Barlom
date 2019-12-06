//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connectedelements

import o.barlom.infrastructure.dxl.model.names.DxlName
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlConnectedQualifiedName(
    val qualifiedName: DxlName
) : DxlConnectedElement(qualifiedName.origin) {

    override fun writeCode(output: CodeWriter) {
        output.write(qualifiedName.text)
    }

}

//---------------------------------------------------------------------------------------------------------------------

