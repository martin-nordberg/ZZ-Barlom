package org.barlom.presentation.client.views.rightpanels.forms

import org.barlom.domain.metamodel.api.actions.IModelAction
import org.barlom.domain.metamodel.api.vertices.AbstractPackagedElement
import org.barlom.presentation.client.actions.IUiAction
import org.katydom.api.katyDomComponent
import org.katydom.builders.KatyDomFlowContentBuilder

fun viewPropertiesForm(
    builder: KatyDomFlowContentBuilder,
    revDispatchModel: (makeModelAction: () -> IModelAction) -> Unit,
    focusedElement: AbstractPackagedElement,
    revDispatchUi: (makeUiAction: () -> IUiAction) -> Unit
) = katyDomComponent(builder) {

    span {
        text( "Form is TBD for " + focusedElement.name)
    }

}