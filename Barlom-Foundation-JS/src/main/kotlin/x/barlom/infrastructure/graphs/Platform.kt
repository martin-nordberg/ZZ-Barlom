//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package /*jvm*/x.barlom.infrastructure.graphs


//---------------------------------------------------------------------------------------------------------------------

/** Returns the class name for the given object [instance]. */
internal fun getClassName(instance:Any) =
    instance::class.simpleName

//---------------------------------------------------------------------------------------------------------------------

