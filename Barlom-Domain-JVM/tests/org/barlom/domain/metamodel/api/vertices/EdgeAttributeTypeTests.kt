//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests of EdgeAttributeType.
 */
class EdgeAttributeTypeTests {

    @Test
    fun `Edge attribute types are constructed as expected`() {

        val model = Model( ::makeUuid )

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage() {
                name = "pkg"
            }
            model.makePackageContainment(root, pkg)
            val et = model.makeUndirectedEdgeType() {
                name = "et"
            }
            val c = model.makeUndirectedEdgeTypeContainment(pkg, et)
            val at = model.makeEdgeAttributeType {
                name = "at"
                optionality = EAttributeOptionality.REQUIRED
            }
            val ca = model.makeEdgeAttributeTypeContainment(et, at)
            val dt = model.makeConstrainedInteger32 {
                name = "dt"
                maxValue = 100
            }
            val u = model.makeAttributeDataTypeUsage(at, dt)

            assertTrue(model.vertices.contains(at))
            assertTrue(model.edges.contains(c))

            assertEquals("at", at.name)
            assertEquals("pkg.et#at", at.path)
            assertEquals(EAttributeOptionality.REQUIRED, at.optionality)

            assertTrue(at.edgeAttributeTypeContainments.contains(ca))
            assertTrue(at.edgeTypes.contains(et))

            assertTrue(et.attributeTypes.contains(at))

            assertTrue(dt.attributeTypes.contains(at))
            assertTrue(at.dataTypes.contains(dt))
        }

    }

}