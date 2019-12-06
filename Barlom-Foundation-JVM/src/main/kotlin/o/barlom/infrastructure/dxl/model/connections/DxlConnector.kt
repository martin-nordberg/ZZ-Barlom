//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.names.DxlName
import o.barlom.infrastructure.dxl.model.arguments.DxlArgumentList
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlConnector(
    val origin: DxlOrigin,
    val qualifiedName: DxlName,
    val arguments: DxlArgumentList,
    val direction: EDxlConnectionDirection
) {

    val code: String
        get() {
            val output = CodeWriter()
            writeCode(output)
            return output.toString()
        }

    fun writeCode(output: CodeWriter) {

        output.write(
            when (direction) {
                EDxlConnectionDirection.LEFT -> "<~"
                else                         -> "~"
            }
        )

        output.write(qualifiedName.text)

        arguments.writeCode(output)

        output.write(
            when (direction) {
                EDxlConnectionDirection.RIGHT -> "~> "
                else                          -> "~ "
            }
        )

    }

}

//---------------------------------------------------------------------------------------------------------------------

