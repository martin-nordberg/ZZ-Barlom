//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.uuids

import o.barlom.infrastructure.dxl.model.core.DxlNullOrigin

//---------------------------------------------------------------------------------------------------------------------

object DxlNullUuid
    : DxlUuid( DxlNullOrigin ) {

    override val text = ""

}

//---------------------------------------------------------------------------------------------------------------------

