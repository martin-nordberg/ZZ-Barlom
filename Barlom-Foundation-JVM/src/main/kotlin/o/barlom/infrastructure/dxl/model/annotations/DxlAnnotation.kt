//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.annotations

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.names.DxlName
import o.barlom.infrastructure.dxl.model.arguments.DxlArgumentList
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlAnnotation(
    val origin: DxlOrigin,
    val qualifiedName: DxlName,
    val arguments: DxlArgumentList
) {

    val code: String
        get() {
            val output = CodeWriter()
            writeCode(output)
            return output.toString()
        }

    fun writeCode(output: CodeWriter) {
        output.write( "@", qualifiedName.text )
        arguments.writeCode(output)
    }

}

//---------------------------------------------------------------------------------------------------------------------

