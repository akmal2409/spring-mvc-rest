package tech.talci.services;

import tech.talci.api.v1.model.CustomerDTO;
import tech.talci.domain.Customer;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO findById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerDTO(Long id, CustomerDTO customerDTO);

    CustomerDTO pathCustomer(Long id, CustomerDTO customerDTO);
}
