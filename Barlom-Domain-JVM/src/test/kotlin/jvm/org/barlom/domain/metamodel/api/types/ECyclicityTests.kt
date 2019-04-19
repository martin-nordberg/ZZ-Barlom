//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.org.barlom.domain.metamodel.api.types

import o.barlom.domain.metamodel.api.types.ECyclicity
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Simple test of ECyclicity.
 */
@Suppress("RemoveRedundantBackticks")
class ECyclicityTests {

    @Test
    fun `Cyclicities know whether they are acyclic`() {

        assertTrue { ECyclicity.ACYCLIC.isAcyclic() ?: false }
        assertFalse { ECyclicity.POTENTIALLY_CYCLIC.isAcyclic() ?: true }
        assertNull(ECyclicity.UNCONSTRAINED.isAcyclic())

    }

    @Test
    fun `Cyclicities can be constructed from booleans`() {

        assertEquals(ECyclicity.ACYCLIC, ECyclicity.fromBoolean(true))
        assertEquals(ECyclicity.POTENTIALLY_CYCLIC, ECyclicity.fromBoolean(false))
        assertEquals(ECyclicity.UNCONSTRAINED, ECyclicity.fromBoolean(null))

    }

}
