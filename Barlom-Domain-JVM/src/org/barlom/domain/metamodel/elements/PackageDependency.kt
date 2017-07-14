//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements

/**
 * Implementation class for package dependencies.
 */
class PackageDependency(

    id: String,

    val clientPackage: NamedPackage,

    val supplierPackage: NamedPackage

) : DocumentedElement(id) {

    init {

        // Register both ends.
        clientPackage.addSupplierPackageDependency(this)
        supplierPackage.addClientPackageDependency(this)

    }

}
