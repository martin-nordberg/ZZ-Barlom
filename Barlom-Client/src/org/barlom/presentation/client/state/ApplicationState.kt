//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.state

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.uuids.makeUuid

class ApplicationState(val revHistory: RevisionHistory) {

    val model = Model({ makeUuid() }, revHistory)

    val uiState = 1

}