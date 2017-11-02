//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.uuids.makeUuid
import org.barlom.presentation.client.state.ApplicationUiState

class ApplicationState(

    val revHistory: RevisionHistory

) {

    val model = Model(::makeUuid, revHistory)

    lateinit var uiState: ApplicationUiState

    init {

        revHistory.update {
            uiState = ApplicationUiState()
            "Initialize UI state."
        }

    }
}
