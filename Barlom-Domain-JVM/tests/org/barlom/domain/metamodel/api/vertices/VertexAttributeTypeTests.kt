//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EAttributeOptionality
import org.barlom.domain.metamodel.api.types.ELabelDefaulting
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests of VertexAttributeType.
 */
class VertexAttributeTypeTests {

    @Test
    fun `Vertex attribute types are constructed as expected`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage() {
                name = "pkg"
            }
            model.makePackageContainment(root, pkg)
            val vt = model.makeVertexType() {
                name = "vt"
            }
            val c = model.makeVertexTypeContainment(pkg, vt)
            val at = model.makeVertexAttributeType {
                name = "at"
                labelDefaulting = ELabelDefaulting.DEFAULT_LABEL
                optionality = EAttributeOptionality.REQUIRED
            }
            val ca = model.makeVertexAttributeTypeContainment(vt, at)
            val dt = model.makeConstrainedInteger32 {
                name = "dt"
                maxValue = 100
            }
            val u = model.makeAttributeDataTypeUsage(at, dt)

            assertTrue(model.vertices.contains(at))
            assertTrue(model.edges.contains(c))

            assertEquals("at", at.name)
            assertEquals("pkg.vt#at", at.path)
            assertEquals(ELabelDefaulting.DEFAULT_LABEL, at.labelDefaulting)
            assertEquals(EAttributeOptionality.REQUIRED, at.optionality)

            assertTrue(at.vertexAttributeTypeContainments.contains(ca))
            assertTrue(at.vertexTypes.contains(vt))

            assertTrue(vt.attributeTypes.contains(at))
            assertTrue(vt.vertexAttributeTypeContainments.contains(ca))

            assertTrue(dt.attributeTypes.contains(at))
            assertTrue(at.dataTypes.contains(dt))
        }

    }

}