//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.graphschema.api

import o.barlom.domain.graphschema.api.model.Model
import o.barlom.infrastructure.graphs.IWritableGraph
import x.barlom.infrastructure.uuids.makeUuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * Tests of graphs containing schema concepts and connections.
 */
@Suppress("RemoveRedundantBackticks")
abstract class SchemaGraphTests {

    protected fun runWriteCheckTest(check: (Model) -> Unit, write: (Model, IWritableGraph) -> Unit) =
        runWriteCheckTest(Model(::makeUuid), check, write)

    protected fun runWriteCheckTest(
        model: Model,
        check: (Model) -> Unit,
        write: (Model, IWritableGraph) -> Unit
    ): Model {

        model.update(write)

        check(model)

        return model

    }

}

//---------------------------------------------------------------------------------------------------------------------

