package tech.talci.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.talci.api.v1.model.CustomerDTO;
import tech.talci.exceptions.ResourceNotFoundException;
import tech.talci.services.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    static final Long ID_VALUE = 2L;
    static final String FIRST_NAME_VALUE = "Test Name";
    static final String LAST_NAME_VALUE = "Test Last Name";
    public static final String CUSTOMER_URL = "/api/v1/customers/2";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllCustomers() throws Exception{
        //given
        List<CustomerDTO> customerDTOList = Arrays.asList(new CustomerDTO(), new CustomerDTO(), new CustomerDTO());

        //when
        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

        //then
        mockMvc.perform(get(CustomerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));
    }

    @Test
    public void testGetCustomerById() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID_VALUE);
        customerDTO.setFirstName(FIRST_NAME_VALUE);
        customerDTO.setLastName(LAST_NAME_VALUE);

        //when
        when(customerService.findById(anyLong())).thenReturn(customerDTO);

        //then
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME_VALUE)));

    }

    @Test
    public void testCreateCustomer() throws Exception{
        //given
        CustomerDTO toSendCustomer = new CustomerDTO();
        toSendCustomer.setFirstName(FIRST_NAME_VALUE);
        toSendCustomer.setLastName(LAST_NAME_VALUE);

        CustomerDTO returnCustomerDto = new CustomerDTO();
        returnCustomerDto.setId(ID_VALUE);
        returnCustomerDto.setFirstName(FIRST_NAME_VALUE);
        returnCustomerDto.setLastName(LAST_NAME_VALUE);
        returnCustomerDto.setCustomerUrl(CustomerController.BASE_URL + "/2");

        //when
        when(customerService.createNewCustomer(toSendCustomer)).thenReturn(returnCustomerDto);

        //then
        mockMvc.perform(post(CustomerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(toSendCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME_VALUE)))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/2")));
    }

    @Test
    public void testUpdateCustomer() throws Exception{
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRST_NAME_VALUE);
        customer.setLastName(LAST_NAME_VALUE);

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setLastName(LAST_NAME_VALUE);
        returnDto.setFirstName(FIRST_NAME_VALUE);
        returnDto.setCustomerUrl(CUSTOMER_URL);

        //when
        when(customerService.saveCustomerDTO(ID_VALUE, customer)).thenReturn(returnDto);

        //then
        mockMvc.perform(put(CustomerController.BASE_URL + "/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME_VALUE)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME_VALUE)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void testPatchCustomer() throws Exception{
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName("Bruck");
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        //when
        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //then
        mockMvc.perform(patch(CustomerController.BASE_URL+ "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Bruck")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception{

        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }

    @Test
    public void testCustomerNotFound() throws Exception{

        when(customerService.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/99")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}