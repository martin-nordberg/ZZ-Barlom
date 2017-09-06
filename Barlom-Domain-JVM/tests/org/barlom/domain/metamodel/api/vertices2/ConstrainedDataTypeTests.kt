package org.barlom.domain.metamodel.api.vertices2

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.types.EDataType
import org.barlom.infrastructure.platform.DateTime
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests of ConstrainedDataType.
 */
class ConstrainedDataTypeTests {

    @Test
    fun `Constrained boolean data types construct as expected`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage()
            model.makePackageContainment(root, pkg)
            val b = model.makeConstrainedBoolean {
                name = "cb"
                defaultValue = false
            }
            val c = model.makeConstrainedDataTypeContainment(pkg, b)

            assertTrue(b.dataType == EDataType.BOOLEAN)

            assertTrue(model.vertices.contains(b))
            assertTrue(model.edges.contains(c))

            assertEquals("cb", b.name)
            assertEquals(false,b.defaultValue)

            assertTrue( b.hasParent(pkg) )
            assertTrue( pkg.constrainedDataTypes.contains(b) )
            assertTrue( b.parents[0] === pkg)
        }

    }

    @Test
    fun `Constrained date-time data types construct as expected`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage()
            model.makePackageContainment(root, pkg)
            val dt = model.makeConstrainedDateTime {
                name = "cdt"
                minValue = DateTime.now()
                maxValue = DateTime.now()
            }
            val c = model.makeConstrainedDataTypeContainment(pkg, dt)

            assertTrue(dt.dataType == EDataType.DATETIME)

            assertTrue(model.vertices.contains(dt))
            assertTrue(model.edges.contains(c))

            assertEquals("cdt", dt.name)
            assertTrue(dt.minValue!! <= dt.maxValue)

            assertTrue( dt.hasParent(pkg) )
            assertTrue( pkg.constrainedDataTypes.contains(dt) )
            assertTrue( dt.parents[0] === pkg)
        }

    }

    @Test
    fun `Constrained float64 data types construct as expected`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage()
            model.makePackageContainment(root, pkg)
            val f = model.makeConstrainedFloat64 {
                name = "cf"
                defaultValue = 1.0
                minValue = 0.0
                maxValue = 100.0
            }
            val c = model.makeConstrainedDataTypeContainment(pkg, f)

            assertTrue(f.dataType == EDataType.FLOAT64)

            assertTrue(model.vertices.contains(f))
            assertTrue(model.edges.contains(c))

            assertEquals("cf", f.name)
            assertEquals(f.defaultValue,1.0)
            assertEquals(f.minValue,0.0)
            assertEquals(f.maxValue,100.0)

            assertTrue( f.hasParent(pkg) )
            assertTrue( pkg.constrainedDataTypes.contains(f) )
            assertTrue( f.parents[0] === pkg)
        }

    }

    @Test
    fun `Constrained int64 data types construct as expected`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage()
            model.makePackageContainment(root, pkg)
            val i = model.makeConstrainedInteger32 {
                name = "ci"
                defaultValue = 3
                minValue = 0
                maxValue = 10
            }
            val c = model.makeConstrainedDataTypeContainment(pkg, i)

            assertTrue(i.dataType == EDataType.INTEGER32)

            assertTrue(model.vertices.contains(i))
            assertTrue(model.edges.contains(c))

            assertEquals("ci", i.name)
            assertEquals(i.defaultValue,3)
            assertEquals(i.minValue,0)
            assertEquals(i.maxValue,10)

            assertTrue( i.hasParent(pkg) )
            assertTrue( pkg.constrainedDataTypes.contains(i) )
            assertTrue( i.parents[0] === pkg)
        }

    }

    @Test
    fun `Constrained string data types construct as expected`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage()
            model.makePackageContainment(root, pkg)
            val s = model.makeConstrainedString {
                name = "cs"
                defaultValue = "xx"
                minLength = 2
                maxLength = 30
            }
            val c = model.makeConstrainedDataTypeContainment(pkg, s)

            assertTrue(s.dataType == EDataType.STRING)

            assertTrue(model.vertices.contains(s))
            assertTrue(model.edges.contains(c))

            assertEquals("cs", s.name)
            assertEquals(s.defaultValue,"xx")
            assertEquals(s.minLength,2)
            assertEquals(s.maxLength,30)

            assertTrue( s.hasParent(pkg) )
            assertTrue( pkg.constrainedDataTypes.contains(s) )
            assertTrue( s.parents[0] === pkg)
        }

    }

    @Test
    fun `Constrained UUID data types construct as expected`() {

        val model = Model()

        model.revHistory.update("test") {
            val root = model.rootPackage
            val pkg = model.makePackage()
            model.makePackageContainment(root, pkg)
            val s = model.makeConstrainedUuid {
                name = "cu"
            }
            val c = model.makeConstrainedDataTypeContainment(pkg, s)

            assertTrue(s.dataType == EDataType.UUID)

            assertTrue(model.vertices.contains(s))
            assertTrue(model.edges.contains(c))

            assertEquals("cu", s.name)

            assertTrue( s.hasParent(pkg) )
            assertTrue( pkg.constrainedDataTypes.contains(s) )
            assertTrue( s.parents[0] === pkg)
        }

    }

}