//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.org.katydom.infrastructure

import o.org.katydom.infrastructure.Cell
import o.org.katydom.infrastructure.MutableCell
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Tests for Cell and MutableCell.
 */
@Suppress("RemoveRedundantBackticks")
class CellTests {

    @Test
    fun `Cell should initialize to null by default`() {
        val cell = MutableCell<String>()
        assertNull(cell.get())
    }

    @Test
    fun `Cell should initialize to a given value`() {
        val cell = MutableCell("hello")
        assertEquals("hello", cell.get())
        assertTrue(cell.contains("hello"))
    }

    @Test
    fun `Cell can have its value changed`() {
        val cell = MutableCell("hello")
        cell.set("goodbye")
        assertEquals("goodbye", cell.get())
    }

    @Test
    fun `Aliased cell sees a changed value`() {
        val mcell = MutableCell("hello")
        val cell: Cell<String> = mcell
        mcell.set("goodbye")
        assertEquals("goodbye", cell.get())
    }

}
