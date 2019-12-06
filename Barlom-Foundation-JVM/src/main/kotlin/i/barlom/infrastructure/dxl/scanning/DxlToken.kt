//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.dxl.scanning

//---------------------------------------------------------------------------------------------------------------------

internal class DxlToken(
    val type: EDxlTokenType,
    val text: String,
    val line: Int,
    val column: Int,
    val value: Any = text
) {

    val length: Int
        get() = text.length

}

//---------------------------------------------------------------------------------------------------------------------

