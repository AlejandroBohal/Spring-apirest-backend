package edu.eci.apirest.backend.clientes.models.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.apirest.backend.clientes.models.entity.Cliente;
import edu.eci.apirest.backend.clientes.models.services.IClienteService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	@Autowired
	private IClienteService clienteService;
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();
	}
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show (@PathVariable Long id) {
		Cliente cliente = null;
		Map<String,Object> response = new HashMap<>();
		try {
			cliente = clienteService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje","Error al consultar en base de datos!");
			response.put("error",e.getMessage() + " "+ e.getMostSpecificCause());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);			
		}		
		if (cliente == null) {
			response.put("mensaje","El cliente Id:"+id.toString()+"no existe en la base de datos!");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
		
	}
	
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create (@RequestBody Cliente cliente) {
		return clienteService.save(cliente);
	}
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update (@RequestBody Cliente cliente,@PathVariable Long id) {
		Cliente clienteActual = clienteService.findById(id);
		
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setEmail(cliente.getEmail());
		return clienteService.save(clienteActual);
	}
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}
}
