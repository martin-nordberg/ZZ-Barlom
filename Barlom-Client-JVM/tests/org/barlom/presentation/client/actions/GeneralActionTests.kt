package org.barlom.presentation.client.actions

import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.presentation.client.state.initializeAppStateForTesting
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Tests of GeneralActions.
 */
class GeneralActionTests {

    @Test
    fun `The focused element can be changed`() {

        RevisionHistory("Testing").update {

            val appState = initializeAppStateForTesting()

            val pkg = appState.model.rootPackage.children[0]

            val action = GeneralActions.focus(pkg)

            val msg = action.invoke(appState.uiState)

            assertEquals(pkg, appState.uiState.focusedElement)
            assertEquals("Select element pkg1 for review.", msg)

            "update"

        }

    }

}