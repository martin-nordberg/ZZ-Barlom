//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.elements

import o.barlom.infrastructure.dxl.model.names.DxlName
import o.barlom.infrastructure.dxl.model.names.DxlNullName
import o.barlom.infrastructure.dxl.model.parameters.DxlParameterList
import o.barlom.infrastructure.dxl.model.uuids.DxlKnownUuid
import o.barlom.infrastructure.dxl.model.uuids.DxlUuid
import o.barlom.infrastructure.dxl.model.annotations.DxlAnnotationList
import o.barlom.infrastructure.dxl.model.connections.DxlConnectionList
import o.barlom.infrastructure.dxl.model.documentation.DxlDocumentation
import o.barlom.infrastructure.codegen.CodeWriter

//---------------------------------------------------------------------------------------------------------------------

class DxlDeclaration(
    documentation: DxlDocumentation,
    val annotations: DxlAnnotationList,
    val concept: DxlConcept,
    val qualifiedName: DxlName,
    val uuid: DxlUuid,
    val parameters: DxlParameterList,
    val connections: DxlConnectionList
) : DxlElement(concept.origin, documentation) {

    override fun writeCode(output: CodeWriter) {

        if (documentation.text.isNotEmpty()) {
            output.write(documentation.text)
            output.writeNewLine()
        }

        annotations.writeCode(output)

        if (annotations.isNotEmpty()) {
            output.writeNewLine()
        }

        output.write(concept.text)

        if (qualifiedName !is DxlNullName) {
            output.write(" ", qualifiedName.text)
        }

        if (uuid is DxlKnownUuid) {
            output.write(" ", uuid.text)
        }

        parameters.writeCode(output)

        connections.writeCode(output)

    }

}

//---------------------------------------------------------------------------------------------------------------------

