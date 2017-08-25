//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.model

import org.barlom.domain.metamodel.api.vertices2.Package
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.uuids.Uuid
import org.barlom.infrastructure.uuids.makeUuid

class Model(

    val rootPackageId: Uuid = Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283")

) {

    val revHistory = RevisionHistory( "Initial empty model." )

    val rootPackage = Package( rootPackageId, "" ) {
        // TODO: freeze the instance in some way
    }


    fun makePackage(
        id: Uuid = makeUuid(),
        name: String = "newpackage",
        initialize: Package.() -> Unit = {}
    ) : Package {
        return Package( id, name, initialize )
    }

}