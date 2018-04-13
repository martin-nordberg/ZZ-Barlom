//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.presentation.client

import o.org.barlom.domain.metamodel.api.model.Model
import o.org.barlom.infrastructure.revisions.RevisionHistory
import js.org.barlom.infrastructure.uuids.makeUuid
import o.org.barlom.presentation.client.state.ApplicationUiState

class ApplicationState {

    val model = Model(::makeUuid)

    val uiState = ApplicationUiState()

    val revHistory = RevisionHistory.currentlyInUse

}
