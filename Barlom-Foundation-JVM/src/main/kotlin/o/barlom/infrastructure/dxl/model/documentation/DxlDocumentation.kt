//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.documentation

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

/**
 * Documentation for a parsed item.
 */
class DxlDocumentation(
    /** Where the documentation starts in the source file. */
    origin: DxlOrigin,
    /** The text of the documentation including "/*" and "*/". */
    val text : String
) : DxlOptDocumentation(origin) {

    /** Writes the documentation followed by a new line. */
    override fun writeCode(output: CodeWriter) {
        output.write(text)
        output.writeNewLine()
    }

}

//---------------------------------------------------------------------------------------------------------------------

