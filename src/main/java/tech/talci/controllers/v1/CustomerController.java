package tech.talci.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.talci.api.v1.model.CustomerDTO;
import tech.talci.api.v1.model.CustomerListDTO;
import tech.talci.services.CustomerService;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO listAllCustomers(){
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id){
        return customerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id){
        return customerService.saveCustomerDTO(id, customerDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id){
        return customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id){

        customerService.deleteCustomerById(id);
    }

    /*
    @GetMapping
    public ResponseEntity<CustomerListDTO> listAllCustomers(){
        return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(customerService.getAllCustomers()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<CustomerDTO>(customerService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<CustomerDTO>(customerService.createNewCustomer(customerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id){
        return new ResponseEntity<CustomerDTO>(customerService.saveCustomerDTO(id, customerDTO),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id){
        return new ResponseEntity<CustomerDTO>(customerService.patchCustomer(id, customerDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){

        customerService.deleteCustomerById(id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
     */

}
