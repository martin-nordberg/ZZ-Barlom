//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.presentation.client.actions

import jvm.barlom.presentation.client.state.initializeAppStateForTesting
import o.barlom.infrastructure.revisions.RevisionHistory
import o.barlom.presentation.client.actions.GeneralActions
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
