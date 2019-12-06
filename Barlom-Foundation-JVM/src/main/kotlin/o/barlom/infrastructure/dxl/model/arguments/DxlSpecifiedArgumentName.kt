//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.arguments

import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlSpecifiedArgumentName(
    origin: DxlOrigin,
    override val text: String
) : DxlArgumentName(origin) {

}

//---------------------------------------------------------------------------------------------------------------------

