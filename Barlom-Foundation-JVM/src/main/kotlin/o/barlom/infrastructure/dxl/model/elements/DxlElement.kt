//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.elements

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.names.DxlNoName
import o.barlom.infrastructure.dxl.model.names.DxlOptName
import o.barlom.infrastructure.dxl.model.parameters.DxlOptParameters
import o.barlom.infrastructure.dxl.model.properties.DxlProperties
import o.barlom.infrastructure.dxl.model.types.DxlOptTypeRef
import o.barlom.infrastructure.dxl.model.uuids.DxlOptUuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A named or UUID-identified container of properties - generally a concept or a connection.
 */
class DxlElement(
    origin: DxlOrigin,
    val uuid: DxlOptUuid,
    val name: DxlOptName,
    val revisedName: DxlOptName,
    val parameters: DxlOptParameters,
    val typeRef: DxlOptTypeRef,
    val properties: DxlProperties
) : DxlItem(origin) {

    override fun writeCode(output: CodeWriter) {

        output.writeBlankSeparated(listOf(uuid, name)) { e ->
            e.writeCode(this)
        }

        if (revisedName !is DxlNoName) {
            output.write("^")
            revisedName.writeCode(output)
        }

        parameters.writeCode(output)

        typeRef.writeCode(output)

        if ( properties.isNotEmpty()) {
            output.write(" ")
        }

        properties.writeCode(output)

    }

}

//---------------------------------------------------------------------------------------------------------------------

