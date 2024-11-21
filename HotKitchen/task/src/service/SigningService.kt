package hotkitchen.service

import hotkitchen.model.SigningTasksRepositoryImpl
import hotkitchen.model.Status
import hotkitchen.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSigningService() {
    val repository = SigningTasksRepositoryImpl()

    routing {
        post("/signup") {
            val user = call.receive<User>()
            if (repository.userExist(User(user.email, user.userType, user.password))) {
                call.respond(HttpStatusCode.Forbidden, Status("Registration failed"))
            } else {
                repository.signup(user = user)
                call.respond(HttpStatusCode.OK, Status("Signed Up"))
            }
        }
        post("/signin") {
            val user = call.receive<User>()
            if (repository.signin(User(email = user.email, password = user.password))) {
                call.respond(HttpStatusCode.OK, Status("Signed In"))
            } else {
                call.respond(HttpStatusCode.Forbidden, Status("Authorization failed"))
            }

        }
    }
}