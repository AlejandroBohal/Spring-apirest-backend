package edu.eci.apirest.backend.clientes;


import edu.eci.apirest.backend.clientes.models.services.IClienteService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={ ApirestBackendClientesApplication.class })
@AutoConfigureMockMvc
public class ClientRestControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private IClienteService clienteService;
    @Test
    public void shouldLoadClients() throws Exception{
        mvc.perform(
                MockMvcRequestBuilders.get("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].nombre",is("Jonas")))
                .andExpect(status().isOk());
    }
}
