package tech.talci.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.talci.api.v1.model.CustomerDTO;
import tech.talci.api.v1.model.VendorDTO;
import tech.talci.services.VendorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

    static final String NAME = "Test";
    static final Long ID = 1L;
    static final String VENDOR_URL = VendorController.BASE_URL + "/2";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    public void getAllVendors() throws Exception{
        //given
        List<VendorDTO> vendorDTOList = Arrays.asList(new VendorDTO(), new VendorDTO());

        when(vendorService.getAllVendors()).thenReturn(vendorDTOList);

        //when&then
        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));

        verify(vendorService, times(1)).getAllVendors();
    }

    @Test
    public void testGetVendorById() throws Exception{
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorUrl(VENDOR_URL);
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        when(vendorService.findById(anyLong())).thenReturn(vendorDTO);

        //when&then
        mockMvc.perform(get(VENDOR_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.id", equalTo(ID)));

        verify(vendorService, times(1)).findById(anyLong());
    }

    @Test
    public void testCreateVendor() throws Exception{
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setId(ID);
        vendor.setName(NAME);


    }
}