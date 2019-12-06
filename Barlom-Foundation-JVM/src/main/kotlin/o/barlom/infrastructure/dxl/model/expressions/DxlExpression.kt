//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.expressions

import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.documentation.DxlDocumentation
import o.barlom.infrastructure.dxl.model.elements.DxlElement

//---------------------------------------------------------------------------------------------------------------------

abstract class DxlExpression(
    origin: DxlOrigin,
    documentation: DxlDocumentation
) : DxlElement(origin, documentation)

//---------------------------------------------------------------------------------------------------------------------

