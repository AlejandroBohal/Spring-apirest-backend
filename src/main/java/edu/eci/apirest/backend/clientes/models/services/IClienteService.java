package edu.eci.apirest.backend.clientes.models.services;

import java.util.List;

import edu.eci.apirest.backend.clientes.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {

	/**
	 * List all clients.
	 * @return Clients
	 */
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	public Cliente save(Cliente cliente);
	public void delete(Long id);
	public Cliente findById(Long id);
}
