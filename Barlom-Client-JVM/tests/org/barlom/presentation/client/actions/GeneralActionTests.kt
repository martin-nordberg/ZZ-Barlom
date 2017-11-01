package org.barlom.presentation.client.actions

import org.barlom.domain.metamodel.api.actions.PackageActions
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.presentation.client.state.initializeAppState
import state.initializeAppStateForTesting
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Tests of GeneralActions.
 */
class GeneralActionTests {

    @Test
    fun `The focused element can be changed`() {

        val revHistory = RevisionHistory("Testing")
        val appState = initializeAppStateForTesting(revHistory)

        revHistory.update {

            val pkg = appState.model.rootPackage.children[0]

            val action = GeneralActions.focus(pkg)

            val msg = action.invoke( appState.uiState )

            assertEquals( pkg, appState.uiState.focusedElement )
            assertEquals( "Select element pkg1 for review.", msg )

            "update"

        }

    }

}