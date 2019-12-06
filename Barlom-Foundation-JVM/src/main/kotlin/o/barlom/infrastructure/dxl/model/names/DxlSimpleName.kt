//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.names

import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlSimpleName(
    origin: DxlOrigin,
    override val text : String
) : DxlName(origin) {

}

//---------------------------------------------------------------------------------------------------------------------

