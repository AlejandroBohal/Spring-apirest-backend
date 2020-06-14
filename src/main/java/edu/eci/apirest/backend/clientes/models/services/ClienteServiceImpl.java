package edu.eci.apirest.backend.clientes.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.apirest.backend.clientes.models.entity.Cliente;
import edu.eci.apirest.backend.clientes.models.persistence.IClienteDAO;

/**
 * @author AlejandroB
 * ClienteService implementation
 */
@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDAO clienteDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDAO.findAll();
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {		
		return clienteDAO.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDAO.deleteById(id);
		
	}
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDAO.findById(id).orElse(null);
	}

}
