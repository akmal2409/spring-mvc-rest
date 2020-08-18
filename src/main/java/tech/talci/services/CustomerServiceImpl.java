package tech.talci.services;

import org.springframework.stereotype.Service;
import tech.talci.api.v1.mapper.CustomerMapper;
import tech.talci.api.v1.model.CustomerDTO;
import tech.talci.controllers.v1.CustomerController;
import tech.talci.domain.Customer;
import tech.talci.exceptions.ResourceNotFoundException;
import tech.talci.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(Long id) {

        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
    }

    private CustomerDTO saveAndReturnDTO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customer);

        returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

        return returnDTO;
    }

    @Override
    public CustomerDTO saveCustomerDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {

        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstName() != null){
                customer.setFirstName(customerDTO.getFirstName());
            }

            if(customerDTO.getLastName() != null){
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
            returnDTO.setCustomerUrl(getCustomerUrl(id));

            return returnDTO;
        }).orElseThrow(RuntimeException::new);
    }

    private String getCustomerUrl(Long id){
        return CustomerController.BASE_URL + "/" + id;
    }

    @Override
    public void deleteCustomerById(Long id) {

        Optional<Customer> customerOptional = customerRepository.findById(id);

        if(customerOptional.isPresent()){
            customerRepository.deleteById(id);
        } else{
            throw new ResourceNotFoundException("Customer with ID: " + id + " was not found!");
        }
    }
}
