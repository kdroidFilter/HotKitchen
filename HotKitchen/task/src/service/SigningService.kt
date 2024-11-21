package hotkitchen.service

import hotkitchen.model.SigningTasksRepository
import hotkitchen.model.Status
import hotkitchen.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSigningService(repository: SigningTasksRepository) {

    routing {
        post("/signup") {
            val user = call.receive<User>()
            if (repository.userExist(user)) {
                call.respond(HttpStatusCode.Forbidden, Status("Registration failed"))
            } else {
                repository.createUser(user)
                call.respond(HttpStatusCode.OK, Status("Signed Up"))
            }
        }
        post("/signin") {
            val user = call.receive<User>()
            if (repository.passwordIsCorrect(user)) {
                call.respond(HttpStatusCode.OK, Status("Signed In"))
            } else {
                call.respond(HttpStatusCode.Forbidden, Status("Authorization failed"))
            }
        }
    }
}