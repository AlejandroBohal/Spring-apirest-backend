package edu.eci.apirest.backend.clientes;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.apirest.backend.clientes.models.entity.Cliente;
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

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
    @Test
    public void shouldShowClient() throws Exception{
        mvc.perform(
                MockMvcRequestBuilders.get("/api/clientes/{id}",2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(jsonPath("$.nombre",is("Kass")))
         .andExpect(jsonPath("$.apellido",is("Klausewitz")));
    }
    @Test
    public void shouldCreateClient() throws Exception{
        Cliente testClient = new Cliente();
        testClient.setNombre("Juanor");
        testClient.setApellido("Mejia");
        testClient.setEmail("Jmejia@gmail.com");

        mvc.perform(
                MockMvcRequestBuilders
                .post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testClient))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    public void shouldDeleteClient() throws Exception{

        mvc.perform(
                MockMvcRequestBuilders
                .delete("/api/clientes/{id}",23L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"mensaje\":\"Cliente eliminado con Ã©xito!\"}")
        );
    }
    @Test
    public void shouldUpdateClient() throws Exception{
        Cliente updatedClient = new Cliente();
        updatedClient.setNombre("Lamberd");
        updatedClient.setApellido("Fyldes H");
        updatedClient.setEmail("lfyldes4@naver.com");
        updatedClient.setCreateAt(new Date(1592256429));
        mvc.perform(
                MockMvcRequestBuilders
                .put("/api/clientes/{id}",5L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedClient))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()
        );
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
