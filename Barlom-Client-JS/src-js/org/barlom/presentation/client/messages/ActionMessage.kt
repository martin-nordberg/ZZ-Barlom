//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.messages

import org.barlom.presentation.client.ApplicationState

/**
 * A message that carries its own OO-style behavior as an action applying to the application state.
 */
interface ActionMessage : Message {

    /**
     * Performs the work of this message by executing the associated action against given [applicationState].
     */
    fun executeAction(applicationState: ApplicationState): String

}
