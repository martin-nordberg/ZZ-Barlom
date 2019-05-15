//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.presentation.client

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.infrastructure.revisions.RevisionHistory
import x.barlom.infrastructure.uuids.makeUuid
import o.barlom.presentation.client.state.ApplicationUiState

class ApplicationState {

    val model = Model(::makeUuid)

    val uiState = ApplicationUiState()

    val revHistory = RevisionHistory.currentlyInUse

}
