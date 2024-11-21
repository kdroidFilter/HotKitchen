package hotkitchen


import hotkitchen.plugins.configureContentNegotiation
import hotkitchen.plugins.configureDatabases
import hotkitchen.service.configureSigningService
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    configureContentNegotiation()
    configureDatabases()
    configureSigningService()
}