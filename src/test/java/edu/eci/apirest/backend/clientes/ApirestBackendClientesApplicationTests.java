package edu.eci.apirest.backend.clientes;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import org.junit.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.eci.apirest.backend.clientes.models.persistence.IClienteDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApirestBackendClientesApplicationTests {
  @Autowired
  private IClienteDAO clienteDao;

  @Test
  public void testListAll() throws IOException {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/clientes/", String.class);
    assertSame(response.getStatusCode(), HttpStatus.OK);
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode responseJson = objectMapper.readTree(response.getBody());
    System.out.println(responseJson.toString());
    assertTrue(responseJson.toString().contains("{\"id\":1,\"nombre\":\"Jonas\",\"apellido\":\"CourtenayY\",\"email\":\"jcourtenay0@com.com\",\"createAt\":\"2019-11-14\"}"));
  }
  
}