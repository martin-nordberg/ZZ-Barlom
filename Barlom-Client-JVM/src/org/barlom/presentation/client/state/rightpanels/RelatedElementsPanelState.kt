//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.state.rightpanels

import org.barlom.infrastructure.revisions.V

/**
 * The Related Elements panel UI state of the application.
 */
class RelatedElementsPanelState {

    private var _newSupplierPackagePath = V("")

    var newSupplierPackagePath
        get() = _newSupplierPackagePath.get()
        set(value) = _newSupplierPackagePath.set(value)

}
