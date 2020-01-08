//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.properties

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin
import o.barlom.infrastructure.dxl.model.core.DxlItem

//---------------------------------------------------------------------------------------------------------------------

class DxlProperties(
    val properties: List<DxlProperty>
) : DxlItem(if (properties.isNotEmpty()) properties[0].origin else DxlNullOrigin) {

    fun isEmpty() =
        properties.isEmpty()

    fun isNotEmpty() =
        properties.isNotEmpty()

    override fun writeCode(output: CodeWriter) =
        output.writeBlankSeparated(properties) { p ->
            p.writeCode(this)
        }

}

//---------------------------------------------------------------------------------------------------------------------

