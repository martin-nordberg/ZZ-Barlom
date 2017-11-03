//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.uuids.makeUuid
import org.barlom.presentation.client.state.ApplicationUiState

class ApplicationState {

    val model = Model(::makeUuid)

    val uiState = ApplicationUiState()

    val revHistory = RevisionHistory.currentlyInUse

}
