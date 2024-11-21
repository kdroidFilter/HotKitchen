import com.typesafe.config.ConfigFactory
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import io.ktor.utils.io.*
import org.apache.commons.logging.Log
import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult

class HotKitchenTest2 : StageTest<Any>() {

    private val email = System.currentTimeMillis().toString() + "jmail.com"
    private val password = (System.currentTimeMillis() % 100_000_000).toString()

    private val signedIn = """{"status":"Signed In"}"""
    private val signedUp = """{"status":"Signed Up"}"""
    private val registrationFailed = """{"status":"Registration failed"}"""
    private val authorizationFailed = """{"status":"Authorization failed"}"""
    private val currentCredentials = """{"email":"$email","userType":"testUser","password":"correct$password"}"""
    private val currentWrongSignIn = """{"email":"$email","password":"wrong$password"}"""
    private val currentCorrectSignIn = """{"email":"$email","password":"correct$password"}"""


    @DynamicTest(order = 1)
    fun registerNewUser(): CheckResult {
        var result = CheckResult.correct()
        try {
            testApplication {
                val response = client.post("/signup"){
                    setBody(currentCredentials)
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
                if (response.bodyAsText() != signedUp || response.status != HttpStatusCode.OK)
                    result = CheckResult.wrong(
                        "Cannot register a new user. Wrong response message or status code."
                    )
            }
        } catch (e: Exception) {
            result = CheckResult.wrong(e.message)
        }
        return result
    }


    @DynamicTest(order = 2)
    fun registerExistingUser(): CheckResult {
        var result = CheckResult.correct()
        try {
            testApplication{
                val response = client.post("/signup"){
                    setBody(currentCredentials)
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
                if (response.bodyAsText() != registrationFailed || response.status != HttpStatusCode.Forbidden)
                    result = CheckResult.wrong(
                        "An existing user is registered. Wrong response message or status code."
                    )
            }
        } catch (e: Exception) {
            result = CheckResult.wrong(e.message)
        }
        return result
    }

    @DynamicTest(order = 3)
    fun wrongAuthorization(): CheckResult {
        var result = CheckResult.correct()
        try {
            testApplication{
                val response = client.post("/signin"){
                    setBody(currentWrongSignIn)
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
                if (response.bodyAsText() != authorizationFailed || response.status != HttpStatusCode.Forbidden)
                    result = CheckResult.wrong(
                        "Error when authorizing a user using a wrong password. Wrong response message or status code."
                    )
            }
        } catch (e: Exception) {
            result = CheckResult.wrong(e.message)
        }
        return result
    }

    @DynamicTest(order = 4)
    fun correctAuthorization(): CheckResult {
        var result = CheckResult.correct()
        try {
            testApplication{
                val response = client.post("/signin"){
                    setBody(currentCorrectSignIn)
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
                if (response.bodyAsText() != signedIn || response.status != HttpStatusCode.OK)
                    result = CheckResult.wrong(
                        "Error when authorizing a user using a correct password. Wrong response message or status code."
                    )
            }
        } catch (e: Exception) {
            result = CheckResult.wrong(e.message)
        }
        return result
    }
}

fun main(){
    HotKitchenTest2().registerNewUser()
}