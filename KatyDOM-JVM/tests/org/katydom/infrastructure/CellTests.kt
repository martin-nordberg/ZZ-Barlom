//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.infrastructure

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.katydom.infrastructure.Cell
import org.katydom.infrastructure.MutableCell

/**
 * Tests for Cell and MutableCell.
 */
class CellTests {

    @Test
    fun `Cell should initialize to null by default`() {
        val cell = MutableCell<String>()
        assertNull( cell.get() )
    }

    @Test
    fun `Cell should initialize to a given value`() {
        val cell = MutableCell( "hello" )
        assertEquals( "hello", cell.get() )
        assertTrue( cell.contains("hello"))
    }

    @Test
    fun `Cell can have its value changed`() {
        val cell = MutableCell( "hello" )
        cell.set( "goodbye" )
        assertEquals( "goodbye", cell.get() )
    }

    @Test
    fun `Aliased cell sees a changed value`() {
        val mcell = MutableCell( "hello" )
        val cell : Cell<String> = mcell
        mcell.set( "goodbye" )
        assertEquals( "goodbye", cell.get() )
    }

}
