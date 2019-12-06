//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.codegen.chunks

//---------------------------------------------------------------------------------------------------------------------

/** An enumeration of possible code densities for formatted output of a composite chunk of code. */
internal enum class ECodeDensity {

    /**
     * The sub-chunks and their opening and closing delimiters are all output on a single line.
     */
    ALL_ONE_LINE,

    /**
     * The sub-chunks are all output on one additional line indented one level from the starting line. The opening
     * delimiter (if any) goes on the original line; the closing delimiter goes on a third line.
     */
    EXTRA_LINE,

    /**
     * Every sub-chunk goes on its own line, indented one level from the starting line. The opening
     * delimiter (if any) goes on the original line; the closing delimiter goes on an extra final line.
     */
    NEW_LINE_PER_ITEM

}

//---------------------------------------------------------------------------------------------------------------------

