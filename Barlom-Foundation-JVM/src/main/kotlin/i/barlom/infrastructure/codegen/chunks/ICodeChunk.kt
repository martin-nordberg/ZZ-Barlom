//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.codegen.chunks

import i.barlom.infrastructure.codegen.builders.CodeStringBuilder

//---------------------------------------------------------------------------------------------------------------------

/**
 * A code chunk represents a snippet of code or a composite punctuated hierarchy of sub-chunks subject to
 * formatting according to content and line lengths.
 */
internal interface ICodeChunk {

    /** Writes out this code chunk as an s-expression. */
    fun writeDebugString(output: CodeStringBuilder)

    /** Writes this chunk of code (recursively through the hierarchy) to the given [output] code string builder. */
    fun writeCode(output: CodeStringBuilder)

}

//---------------------------------------------------------------------------------------------------------------------
