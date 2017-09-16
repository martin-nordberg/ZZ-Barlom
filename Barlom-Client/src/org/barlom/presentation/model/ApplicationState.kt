//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.model

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.infrastructure.uuids.makeUuid

class ApplicationState {

    val uiState = 1

    val model = Model( { makeUuid() } )

}