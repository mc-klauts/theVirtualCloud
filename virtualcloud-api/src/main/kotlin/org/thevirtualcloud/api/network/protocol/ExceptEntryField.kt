package org.thevirtualcloud.api.network.protocol

/**
 * java doc created on 02.05.2022
 * @author Alexa
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExceptEntryField(val value: String = "non.value", val enumType: String = "non.value")