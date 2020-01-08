//
// (C) Copyright 2020 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.connections

enum class EDxlConnectionDirection {

    /** "---[..]---" */
    UNDIRECTED,

    /** "<--[..]---" */
    DIRECTED_LEFT,

    /** "---[..]-->" */
    DIRECTED_RIGHT,

    /** "<--[..]-->" */
    BIDIRECTIONAL

}
