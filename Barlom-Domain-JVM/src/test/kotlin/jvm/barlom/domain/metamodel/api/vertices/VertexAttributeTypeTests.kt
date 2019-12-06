//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.domain.metamodel.api.vertices

import o.barlom.domain.metamodel.api.model.Model
import o.barlom.domain.metamodel.api.types.EAttributeOptionality
import o.barlom.domain.metamodel.api.types.ELabelDefaulting
import o.barlom.infrastructure.revisions.RevisionHistory
import x.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests of VertexAttributeType.
 */
@Suppress("RemoveRedundantBackticks")
class VertexAttributeTypeTests {

    @Test
    fun `Vertex attribute types are constructed as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage {
                    name = "pkg"
                }
                makePackageContainment(root, pkg)
                val vt = makeVertexType {
                    name = "vt"
                }
                val c = makeVertexTypeContainment(pkg, vt)
                val at = makeVertexAttributeType {
                    name = "at"
                    labelDefaulting = ELabelDefaulting.DEFAULT_LABEL
                    optionality = EAttributeOptionality.REQUIRED
                }
                val ca = makeVertexAttributeTypeContainment(vt, at)
                val dt = makeConstrainedInteger32 {
                    name = "dt"
                    maxValue = 100
                }
                makeAttributeDataTypeUsage(at, dt)

                assertTrue(vertices.contains(at))
                assertTrue(edges.contains(c))

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

            "test"

        }

    }

}
