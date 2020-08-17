package tech.talci.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.talci.api.v1.mapper.CustomerMapper;
import tech.talci.api.v1.model.CustomerDTO;
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
}