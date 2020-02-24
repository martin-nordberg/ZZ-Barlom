//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.declarations

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlAliases(
    private val aliases: List<DxlAlias>
) : DxlItem(if (aliases.isNotEmpty()) aliases[0].origin else DxlNullOrigin),
    Iterable<DxlAlias> by aliases {

    fun isEmpty() =
        aliases.isEmpty()

    fun isNotEmpty() =
        aliases.isNotEmpty()

    override fun writeCode(output: CodeWriter) {

        output.writeNewLineSeparated(aliases) { p ->
            p.writeCode(this)
        }

        if (isNotEmpty()) {
            output.writeNewLine()
            output.writeNewLine()
        }

    }

}

//---------------------------------------------------------------------------------------------------------------------

