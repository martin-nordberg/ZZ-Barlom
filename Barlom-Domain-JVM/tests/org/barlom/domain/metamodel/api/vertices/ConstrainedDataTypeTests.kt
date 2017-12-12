//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EDataType
import org.barlom.infrastructure.platform.DateTime
import org.barlom.infrastructure.revisions.RevisionHistory
import org.barlom.infrastructure.uuids.makeUuid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests of ConstrainedDataType.
 */
class ConstrainedDataTypeTests {

    @Test
    fun `Constrained boolean data types construct as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage()
                makePackageContainment(root, pkg)
                val b = makeConstrainedBoolean {
                    name = "cb"
                    defaultValue = false
                }
                val c = makeConstrainedDataTypeContainment(pkg, b)

                assertTrue(b.dataType == EDataType.BOOLEAN)

                assertTrue(vertices.contains(b))
                assertTrue(edges.contains(c))

                assertEquals("cb", b.name)
                assertEquals(false, b.defaultValue)

                assertTrue(b.hasParent(pkg))
                assertTrue(pkg.constrainedDataTypes.contains(b))
                assertTrue(b.parents[0] === pkg)

            }

            "test"

        }

    }

    @Test
    fun `Constrained date-time data types construct as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage()
                makePackageContainment(root, pkg)
                val dt = makeConstrainedDateTime {
                    name = "cdt"
                    minValue = DateTime.now()
                    maxValue = DateTime.now()
                }
                val c = makeConstrainedDataTypeContainment(pkg, dt)

                assertTrue(dt.dataType == EDataType.DATETIME)

                assertTrue(vertices.contains(dt))
                assertTrue(edges.contains(c))

                assertEquals("cdt", dt.name)
                assertTrue(dt.minValue!! <= dt.maxValue)

                assertTrue(dt.hasParent(pkg))
                assertTrue(pkg.constrainedDataTypes.contains(dt))
                assertTrue(dt.parents[0] === pkg)

            }

            "test"

        }

    }

    @Test
    fun `Constrained float64 data types construct as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage()
                makePackageContainment(root, pkg)
                val f = makeConstrainedFloat64 {
                    name = "cf"
                    defaultValue = 1.0
                    minValue = 0.0
                    maxValue = 100.0
                }
                val c = makeConstrainedDataTypeContainment(pkg, f)

                assertTrue(f.dataType == EDataType.FLOAT64)

                assertTrue(vertices.contains(f))
                assertTrue(edges.contains(c))

                assertEquals("cf", f.name)
                assertEquals(f.defaultValue, 1.0)
                assertEquals(f.minValue, 0.0)
                assertEquals(f.maxValue, 100.0)

                assertTrue(f.hasParent(pkg))
                assertTrue(pkg.constrainedDataTypes.contains(f))
                assertTrue(f.parents[0] === pkg)

            }

            "test"

        }

    }

    @Test
    fun `Constrained int64 data types construct as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage()
                makePackageContainment(root, pkg)
                val i = makeConstrainedInteger32 {
                    name = "ci"
                    defaultValue = 3
                    minValue = 0
                    maxValue = 10
                }
                val c = makeConstrainedDataTypeContainment(pkg, i)

                assertTrue(i.dataType == EDataType.INTEGER32)

                assertTrue(vertices.contains(i))
                assertTrue(edges.contains(c))

                assertEquals("ci", i.name)
                assertEquals(i.defaultValue, 3)
                assertEquals(i.minValue, 0)
                assertEquals(i.maxValue, 10)

                assertTrue(i.hasParent(pkg))
                assertTrue(pkg.constrainedDataTypes.contains(i))
                assertTrue(i.parents[0] === pkg)

            }

            "test"

        }

    }

    @Test
    fun `Constrained string data types construct as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage()
                makePackageContainment(root, pkg)
                val s = makeConstrainedString {
                    name = "cs"
                    defaultValue = "xx"
                    minLength = 2
                    maxLength = 30
                    regexPattern = Regex(".*loopy")
                }
                val c = makeConstrainedDataTypeContainment(pkg, s)

                assertTrue(s.dataType == EDataType.STRING)

                assertTrue(vertices.contains(s))
                assertTrue(edges.contains(c))

                assertEquals("cs", s.name)
                assertEquals(s.defaultValue, "xx")
                assertEquals(s.minLength, 2)
                assertEquals(s.maxLength, 30)
                assertEquals(s.regexPattern.toString(), ".*loopy")

                assertTrue(s.hasParent(pkg))
                assertTrue(pkg.constrainedDataTypes.contains(s))
                assertTrue(s.parents[0] === pkg)

            }

            "test"

        }

    }

    @Test
    fun `Constrained UUID data types construct as expected`() {

        RevisionHistory("test").update {

            Model(::makeUuid) {

                val root = rootPackage
                val pkg = makePackage()
                makePackageContainment(root, pkg)
                val s = makeConstrainedUuid {
                    name = "cu"
                }
                val c = makeConstrainedDataTypeContainment(pkg, s)

                assertTrue(s.dataType == EDataType.UUID)

                assertTrue(vertices.contains(s))
                assertTrue(edges.contains(c))

                assertEquals("cu", s.name)

                assertTrue(s.hasParent(pkg))
                assertTrue(pkg.constrainedDataTypes.contains(s))
                assertTrue(s.parents[0] === pkg)

            }

            "test"

        }

    }

}