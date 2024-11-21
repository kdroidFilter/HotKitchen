package hotkitchen


import hotkitchen.model.SigningTasksRepositoryImpl
import hotkitchen.plugins.configureContentNegotiation
import hotkitchen.plugins.configureDatabases
import hotkitchen.service.configureSigningService
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    val repository = SigningTasksRepositoryImpl()
    configureContentNegotiation()
    configureDatabases()
    configureSigningService(repository)
}