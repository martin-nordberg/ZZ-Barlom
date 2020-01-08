//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.declarations

import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.documentation.DxlOptDocumentation

//---------------------------------------------------------------------------------------------------------------------

abstract class DxlDeclaration(
    origin: DxlOrigin,
    val documentation: DxlOptDocumentation
) : DxlItem(origin)

//---------------------------------------------------------------------------------------------------------------------

