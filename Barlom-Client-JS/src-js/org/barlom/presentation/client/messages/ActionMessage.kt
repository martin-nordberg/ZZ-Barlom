//
// (C) Copyright 2018 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.messages

import org.barlom.presentation.client.ApplicationState

interface ActionMessage : Message {

    fun executeAction(applicationState: ApplicationState)

}