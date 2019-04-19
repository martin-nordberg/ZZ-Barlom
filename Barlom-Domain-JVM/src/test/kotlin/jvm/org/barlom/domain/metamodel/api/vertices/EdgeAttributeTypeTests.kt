//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.org.barlom.domain.metamodel.api.vertices

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.domain.metamodel.api.types.EAttributeOptionality
import o.barlom.infrastructure.revisions.RevisionHistory
import x.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests of EdgeAttributeType.
 */
@Suppress("RemoveRedundantBackticks")
class EdgeAttributeTypeTests {

    @Test
    fun `Edge attribute types are constructed as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {
                val root = rootPackage
                val pkg = makePackage() {
                    name = "pkg"
                }
                makePackageContainment(root, pkg)
                val et = makeUndirectedEdgeType() {
                    name = "et"
                }
                val c = makeUndirectedEdgeTypeContainment(pkg, et)
                val at = makeEdgeAttributeType {
                    name = "at"
                    optionality = EAttributeOptionality.REQUIRED
                }
                val ca = makeEdgeAttributeTypeContainment(et, at)
                val dt = makeConstrainedInteger32 {
                    name = "dt"
                    maxValue = 100
                }
                makeAttributeDataTypeUsage(at, dt)

                assertTrue(vertices.contains(at))
                assertTrue(edges.contains(c))

                assertEquals("at", at.name)
                assertEquals("pkg.et#at", at.path)
                assertEquals(EAttributeOptionality.REQUIRED, at.optionality)

                assertTrue(at.edgeAttributeTypeContainments.contains(ca))
                assertTrue(at.edgeTypes.contains(et))

                assertTrue(et.attributeTypes.contains(at))

                assertTrue(dt.attributeTypes.contains(at))
                assertTrue(at.dataTypes.contains(dt))

            }

            "test"

        }

    }

}
