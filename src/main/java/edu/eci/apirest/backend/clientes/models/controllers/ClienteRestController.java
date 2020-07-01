package edu.eci.apirest.backend.clientes.models.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import edu.eci.apirest.backend.clientes.models.entity.Cliente;
import edu.eci.apirest.backend.clientes.models.services.IClienteService;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin(origins= {"http://localhost:4200"})
//@CrossOrigin(origins= {"https://clientes-appng.web.app"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	@Autowired
	private IClienteService clienteService;
	private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

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
			response.put("error",e.getMessage() + " "+ e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);			
		}		
		if (cliente == null) {
			response.put("mensaje","El cliente Id:"+id.toString()+" "+"no existe en la base de datos!");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
		
	}

	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page,6);
		return clienteService.findAll(pageable);
	}

	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create (@Valid @RequestBody Cliente cliente, BindingResult result) {
		Cliente clienteNuevo = null;
		Map<String,Object> response = new HashMap<>();
		if (result.hasErrors()){

			/*List<String> errors = new ArrayList<>();
			for (FieldError err : result.getFieldErrors()){
				errors.add("El campo '" + err.getField() + "' "+err.getDefaultMessage());
			}* jdk <8*/
			List<String> errors = result.getFieldErrors()
				.stream()
				.map(err -> "El campo '" + err.getField()+"' " +err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		try {
			clienteNuevo = clienteService.save(cliente);
		}
		catch(DataAccessException e) {
			response.put("mensaje","Error al insertar en la base de datos!");
			response.put("error",e.getMessage() + " "+ e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Cliente creado con éxito!");
		response.put("cliente", clienteNuevo);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	private void updateCliente (Cliente clienteActual, Cliente cliente) {
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setEmail(cliente.getEmail());
		clienteActual.setEmail(cliente.getEmail());
	}
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update (@Valid @RequestBody Cliente cliente,BindingResult result,@PathVariable Long id ) {
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteActualizado = null;
		Map<String,Object> response = new HashMap<>();
		if (result.hasErrors()){
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField()+"' " +err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		if (clienteActual == null) {
			response.put("mensaje","Error no se pudo editar el cliente Id:"+id.toString()+" "+"no existe en la base de datos!");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			updateCliente(clienteActual,cliente);
			clienteActualizado = clienteService.save(clienteActual);
		} catch(DataAccessException e) {
			response.put("mensaje","Error al actualizar en la base de datos!");
			response.put("error",e.getMessage() + " "+ e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Cliente actualizado con éxito!");
		response.put("cliente", clienteActualizado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	private void borrarFoto(Cliente cliente) {
		String nombreFotoAnterior = cliente.getFoto();
		if ( nombreFotoAnterior != null && nombreFotoAnterior.length() >0){
			Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
				archivoFotoAnterior.delete();
			}
		}
	}

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String,Object> response = new HashMap<>();
		try {
			Cliente cliente = clienteService.findById(id);
			borrarFoto(cliente);
			clienteService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje","Error al eliminar en la base de datos!");
			response.put("error",e.getMessage() + " "+ e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","Cliente eliminado con éxito!");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

	}
    @PostMapping("/clientes/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id){
	    Map<String,Object> response = new HashMap<>();
	    Cliente cliente = clienteService.findById(id);
	    if (!file.isEmpty()){

	       	String fileName = UUID.randomUUID().toString() +"_"+ Objects.requireNonNull(file.getOriginalFilename()).replace(" ","_");
            Path filePath = Paths.get("uploads").resolve(fileName).toAbsolutePath();
			log.info(filePath.toString());
            try {
                Files.copy(file.getInputStream(),filePath);
            } catch (IOException e) {
                response.put("mensaje","Error al subir la imagen del cliente. " + fileName);
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            borrarFoto(cliente);
            cliente.setFoto(fileName);
            clienteService.save(cliente);
            response.put("cliente",cliente);
            response.put("mensaje","Imagen subida correctamente: "+fileName);
        }
	    return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }
    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto (@PathVariable String nombreFoto){
		Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		log.info(rutaArchivo.toString());
		Resource recurso = null;
		try{
			recurso = new UrlResource(rutaArchivo.toUri());
		}catch (MalformedURLException e){
			e.printStackTrace();
		}
		assert recurso != null;
		if (!recurso.exists() && !recurso.isReadable()){
			throw new RuntimeException("Error no se pudo cargar imagen: "+ nombreFoto);
		}
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ recurso.getFilename() + "\"");
		return new ResponseEntity<Resource>(recurso,header,HttpStatus.OK);
	}
}
