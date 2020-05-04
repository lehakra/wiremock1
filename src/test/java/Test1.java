import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Test1 {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080); // No-args constructor defaults to port 8080

    @Test
    public void exampleTest() {
        // this is wiremock. it creates an endpoint to be called
        stubFor(get(urlEqualTo("/my/resource"))
                .withHeader("Accept", equalTo("text/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/json")
                        .withBody("{\"name\":5}")));

        // this is rest assured. it calls the endpoint from wiremock
        // while the actual api endpoint is still under development.
        // this allows testing to not be blocked while devs create the endpoint.
        io.restassured.RestAssured.given().proxy(8080)
                .header("Accept", "text/json")
                .get("/my/resource")
                .then()
                .body("name", equalTo(5));

    }
}
