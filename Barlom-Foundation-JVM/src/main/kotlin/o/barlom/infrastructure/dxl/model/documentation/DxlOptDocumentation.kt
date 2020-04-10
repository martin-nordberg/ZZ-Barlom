//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.documentation

import o.barlom.infrastructure.dxl.model.core.DxlItem
import o.barlom.infrastructure.dxl.model.core.DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

/**
 * Base class for documentation that is optional.
 */
abstract class DxlOptDocumentation(
    /** Where the documentation originated. */
    origin: DxlOrigin
) : DxlItem(origin)

//---------------------------------------------------------------------------------------------------------------------

