//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.elements

import o.barlom.infrastructure.dxl.model.names.DxlName
import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

class DxlConcept(
    val origin: DxlOrigin,
    val qualifiedName: DxlName
) {

    val text =
        "#" + qualifiedName.text

}

//---------------------------------------------------------------------------------------------------------------------

