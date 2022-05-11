package org.thevirtualcloud.api.network.impl

import org.thevirtualcloud.api.network.connection.IConnectionComponent

/**
 * java doc created on 01.05.2022
 * @author Alexa
 */

class SimpleConnectionComponent(private val port: Int, private val host: String): IConnectionComponent {

    override fun host(): String {
        return host
    }

    override fun port(): Int {
        return port
    }

}