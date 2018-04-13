package jvm.org.barlom.presentation.client.actions

import jvm.org.barlom.presentation.client.state.initializeAppStateForTesting
import o.org.barlom.infrastructure.revisions.RevisionHistory
import o.org.barlom.presentation.client.actions.GeneralActions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Tests of GeneralActions.
 */
@Suppress("RemoveRedundantBackticks")
class GeneralActionTests {

    @Test
    fun `The focused element can be changed`() {

        RevisionHistory("Testing").update {

            val appState = initializeAppStateForTesting()

            val pkg = appState.model.rootPackage.childPackages[0]

            val action = GeneralActions.focus(pkg)

            val msg = action.invoke(appState.uiState)

            assertEquals(pkg, appState.uiState.focusedElement)
            assertEquals("Selected element pkg1 for review.", msg)

            "update"

        }

    }

}