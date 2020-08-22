package tech.talci.api.v1.mapper;

import org.junit.Test;
import tech.talci.api.v1.model.CustomerDTO;
import tech.talci.domain.Customer;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    static final Long ID_VALUE = 1L;
    static final String FIRST_NAME = "firstName";
    static final String LAST_NAME = "lastName";

    @Test
    public void customerToCustomerDTO() {
        //given
        Customer customer = new Customer();
        customer.setId(ID_VALUE);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertNotNull(customerDTO);
        assertEquals(ID_VALUE, customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }

    @Test
    public void testCustomerDTOToCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID_VALUE);
        customerDTO.setLastName(LAST_NAME);
        customerDTO.setFirstName(FIRST_NAME);

        //when
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        //then
        assertEquals(customer.getId(), ID_VALUE);
        assertEquals(customer.getFirstName(), FIRST_NAME);
        assertEquals(customer.getLastName(), LAST_NAME);
    }
}