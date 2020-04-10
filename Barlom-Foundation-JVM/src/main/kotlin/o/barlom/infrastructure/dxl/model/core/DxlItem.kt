//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.core

import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

/**
 * An abstract base class for all items in the parse tree.
 */
abstract class DxlItem(
    /** Where the item originated. */
    val origin: DxlOrigin
) {

    /**
     * Returns the code for this item in isolation from any parent item.
     */
    val code: String
        get() {
            val output = CodeWriter()
            writeCode(output)
            print(output.toDebugString())
            return output.toString()
        }

    /**
     * Writes the code of this item to the given [output].
     */
    abstract fun writeCode(output: CodeWriter)

}

//---------------------------------------------------------------------------------------------------------------------

