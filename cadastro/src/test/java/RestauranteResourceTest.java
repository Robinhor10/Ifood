
import javax.ws.rs.core.Response.Status;
import com.github.Robinhor10.Ifood.CadastroTestLifecycleManager;
import com.github.Robinhor10.Ifood.Restaurante;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import org.approvaltests.Approvals;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteResourceTest {
    
    @Test
    @DataSet("restaurantes-cenario-1.yml")
       public void testBuscarRestaurantes() {
        String resultado = given()
                .when().get("/restaurantes")
                .then() 
                .statusCode(200)
                .extract().asString();
       Approvals.verifyJson(resultado);
    }

    private RequestSpecification given() {
        return RestAssured.given() .contentType(ContentType.JSON);
    }
    
//    Teste PUT

    public void testAlterarRestaurante() {
        Restaurante dto = new Restaurante();
        dto.nome = "novoNome";
        Long parameterValue = 123L;
        given()
            .with().pathParam("id", parameterValue)
            .body(dto)
            .when().put("/restaurantes/{id}")
            .then()
            .statusCode(Status.NO_CONTENT.getStatusCode())
            .extract().asString();

        Restaurante findById = Restaurante.findById(parameterValue);

        Assert.assertEquals(dto.nome, findById.nome);
    }
}
