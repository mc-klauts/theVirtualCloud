package eu.thevirtualcloud.api.network.connection

import io.netty.channel.epoll.Epoll

/**
 * java doc created on 01.05.2022
 * @author Alexa
 */

interface IConnectionComponent {

    fun host(): String

    fun port(): Int

}