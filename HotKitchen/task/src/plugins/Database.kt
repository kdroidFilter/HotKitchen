package hotkitchen.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/ktor_db",
        driver = "org.postgresql.Driver",
        user = "ktor_user",
        password = "ktor_password"
    )
}