//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.annotations

import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlAnnotationList(
    val annotations: List<DxlAnnotation>
) {

    val code: String
        get() {
            val output = CodeWriter()
            writeCode(output)
            return output.toString()
        }

    fun isEmpty() =
        annotations.isEmpty()

    fun isNotEmpty() =
        annotations.isNotEmpty()

    fun writeCode(output: CodeWriter) =
        output.writeBlankSeparated(annotations) { a ->
            a.writeCode(this)
        }

    val origin =
        if (annotations.isNotEmpty()) annotations[0].origin else DxlNullOrigin

}

//---------------------------------------------------------------------------------------------------------------------

