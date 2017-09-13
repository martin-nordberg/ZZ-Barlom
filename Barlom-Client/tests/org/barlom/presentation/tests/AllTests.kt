package org.barlom.presentation.tests

import org.barlom.infrastructure.uuids.testUuids
import org.w3c.dom.get
import kotlin.browser.window

fun runTests() : Boolean {

    val qunit: dynamic = window["QUnit"]

    if ( qunit == undefined ) {
        return false
    }

    qunit.test( "hello test")  { assert ->
        val t: Any = "1"
        assert.ok("1" == t, "Passed!")
    }

    qunit.test( "hello test")  { assert->
        assert.ok(1 == 1, "Passed!")
    }

    testUuids( qunit )

    return true

}
