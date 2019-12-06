//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.model.names

//---------------------------------------------------------------------------------------------------------------------

class DxlQualifiedName(
    val names: List<DxlSimpleName>
) : DxlName(if (names.isNotEmpty()) names[0].origin else throw IllegalArgumentException("")) {

    override val text =
        names.joinToString(".") { n -> n.text }

}


//---------------------------------------------------------------------------------------------------------------------

