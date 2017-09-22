//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.client.views.listitems

import org.barlom.domain.metamodel.api.vertices.Package
import org.katydom.api.katyDomListItemComponent


/** Generates a tree item for a given package [pkg]. */
fun viewPackageTreeItem(pkg: Package) = katyDomListItemComponent {

    li("c-tree__item.c-tree__item--expandable") {

        data("uuid", pkg.id.toString())

        onclick { e ->
            console.log("Tree node clicked: ", e.offsetX)
        }

        viewPackageListItem(pkg)()

    }

}


