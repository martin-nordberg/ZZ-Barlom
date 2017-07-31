//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel

import org.barlom.infrastructure.revisions.RevisionHistory

fun withRevHistory( test: ()->Unit ) {

    RevisionHistory("init").update("test", 0, test )

}

