//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.listitems

import org.barlom.domain.metamodel.api.vertices.Package
import org.katydom.api.katyDomComponent


/** Generates the icon and clickable name for a package [pkg]. */
fun viewPackageListItem(pkg: Package) = katyDomComponent {

    span(".c-link") {

        val msg = "Package " + pkg.name + " clicked."
        onclick { e ->
            console.log(msg)
            e.stopPropagation()
        }

        span(".mdi.mdi-folder.package-icon") {}

        text(" " + pkg.name)

    }

}
