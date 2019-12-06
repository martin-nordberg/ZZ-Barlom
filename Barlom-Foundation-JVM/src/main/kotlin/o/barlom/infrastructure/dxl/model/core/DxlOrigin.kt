//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.core

//---------------------------------------------------------------------------------------------------------------------

sealed class DxlOrigin

//---------------------------------------------------------------------------------------------------------------------

object DxlNullOrigin
    : DxlOrigin() {

    override fun toString() =
        "[no origin]"

}

//---------------------------------------------------------------------------------------------------------------------

data class DxlFileOrigin(
    val fileName: String,
    val line: Int,
    val column: Int
) : DxlOrigin() {

    override fun toString() =
        "$fileName($line,$column)"

}

//---------------------------------------------------------------------------------------------------------------------

