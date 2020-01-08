//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.expressions.literals

import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlIntegerLiteral(
    origin: DxlOrigin,
    override val text: String
) : DxlLiteralExpression(origin)

//---------------------------------------------------------------------------------------------------------------------

