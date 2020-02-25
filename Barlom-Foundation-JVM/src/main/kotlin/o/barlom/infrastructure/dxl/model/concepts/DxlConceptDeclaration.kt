//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.concepts

import o.barlom.infrastructure.codegen.CodeWriter
import o.barlom.infrastructure.dxl.model.connections.*
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.declarations.DxlDeclaration
import o.barlom.infrastructure.dxl.model.documentation.DxlOptDocumentation
import o.barlom.infrastructure.dxl.model.elements.DxlElement

//---------------------------------------------------------------------------------------------------------------------

class DxlConceptDeclaration : DxlDeclaration {

    val element: DxlElement
    val connection: DxlOptConnection
    val content: DxlOptContent

    constructor(
        origin: DxlOrigin,
        documentation: DxlOptDocumentation,
        element: DxlElement
    ) : super(origin, documentation) {
        this.element = element
        this.connection = DxlNoConnection
        this.content = DxlNoContent
    }

    constructor(
        origin: DxlOrigin,
        documentation: DxlOptDocumentation,
        element: DxlElement,
        connection: DxlConnection
    ) : super(origin, documentation) {
        this.element = element
        this.connection = connection
        this.content = DxlNoContent
    }

    constructor(
        origin: DxlOrigin,
        documentation: DxlOptDocumentation,
        element: DxlElement,
        content: DxlOptContent
    ) : super(origin, documentation) {
        this.element = element
        this.connection = DxlNoConnection
        this.content = content
    }

    override fun writeCode(output: CodeWriter) {

        documentation.writeCode(output)

        output.writeSqBracketBlankBlock(listOf(element)) { e ->
            e.writeCode(this)
        }

        connection.writeCode(output)

        content.writeCode(output)

    }

}

//---------------------------------------------------------------------------------------------------------------------

