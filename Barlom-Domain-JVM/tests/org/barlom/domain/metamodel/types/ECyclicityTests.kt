//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.types

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Simple test of ECyclicity.
 */
class ECyclicityTests {

    @Test
    fun `Cyclicities know whether they are acyclic`() {

        assertTrue{ ECyclicity.ACYCLIC.isAcyclic() ?: false }
        assertFalse{ ECyclicity.POTENTIALLY_CYCLIC.isAcyclic() ?: true }
        assertNull( ECyclicity.UNCONSTRAINED.isAcyclic() )

    }

    @Test
    fun `Cyclicities can be constructed from booleans`() {

        assertEquals( ECyclicity.ACYCLIC, ECyclicity.fromBoolean(true))
        assertEquals( ECyclicity.POTENTIALLY_CYCLIC, ECyclicity.fromBoolean(false))
        assertEquals( ECyclicity.UNCONSTRAINED, ECyclicity.fromBoolean(null))

    }

}
