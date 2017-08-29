//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.model

import org.barlom.domain.metamodel.api.edges2.PackageContainment
import org.barlom.domain.metamodel.api.edges2.PackageDependency
import org.barlom.domain.metamodel.api.vertices2.Package
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.uuids.Uuid
import org.barlom.infrastructure.uuids.makeUuid

class Model(

    rootPackageId: Uuid = Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283")

) {

    val revHistory = RevisionHistory("Initial empty model.")

    val rootPackage: Package


    init {

        var rootPkg: Package? = null

        revHistory.update("Root elements added.", 0) {
            rootPkg = Package(rootPackageId, "", true) {}
        }

        rootPackage = rootPkg!!

    }


    fun makePackage(
        id: Uuid = makeUuid(),
        name: String = "newpackage",
        initialize: Package.() -> Unit = {}
    ): Package {
        return Package(id, name, false, initialize)
    }

    fun makePackageContainment(
        id: Uuid = makeUuid(),
        parent: Package,
        child: Package
    ): PackageContainment {
        return PackageContainment(id, parent, child)
    }

    fun makePackageDependency(
        id: Uuid = makeUuid(),
        client: Package,
        supplier: Package
    ): PackageDependency {
        return PackageDependency(id, client, supplier)
    }

}