package edu.eci.apirest.backend.clientes.models.services;

import java.util.List;

import edu.eci.apirest.backend.clientes.models.entity.Cliente;

public interface IClienteService {

	/**
	 * List all clients.
	 * @return Clients
	 */
	public List<Cliente> findAll();
	public Cliente save(Cliente cliente);
	public void delete(Long id);
	public Cliente findById(Long id);
}
