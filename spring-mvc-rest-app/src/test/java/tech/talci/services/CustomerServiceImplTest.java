package tech.talci.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.talci.api.v1.mapper.CustomerMapper;
import tech.talci.api.v1.model.CustomerDTO;
import tech.talci.controllers.v1.CustomerController;
import tech.talci.domain.Customer;
import tech.talci.repositories.CustomerRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    final static long ID_VALUE = 1L;
    final static String FIRST_NAME = "Test";
    final static String LAST_NAME = "TEsst";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        //when
        when(customerRepository.findAll()).thenReturn(customers);

        //then
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        assertEquals(3, customerDTOS.size());

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void findById() {
        //given
        Customer customer = new Customer();
        customer.setId(ID_VALUE);

         //when
        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer));

        //then
        CustomerDTO returnedDTO = customerService.findById(1L);
        assertNotNull(returnedDTO);
        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCreateNewCustomer() throws Exception{
        //given
        Customer customer = new Customer();
        customer.setId(ID_VALUE);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);


        //when
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        //then
        CustomerDTO customerDTO = customerService.createNewCustomer(new CustomerDTO());

        assertNotNull(customerDTO);
        assertEquals(CustomerController.BASE_URL + "/1", customerDTO.getCustomerUrl());
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    public void testSaveCustomerDTO() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(FIRST_NAME);
        savedCustomer.setLastName(LAST_NAME);
        savedCustomer.setId(ID_VALUE);

        //when
        when(customerRepository.save(any())).thenReturn(savedCustomer);

        CustomerDTO savedDto = customerService.saveCustomerDTO(ID_VALUE, customerDTO);

        //then
        assertEquals(FIRST_NAME, savedDto.getFirstName());
        assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
    }
}