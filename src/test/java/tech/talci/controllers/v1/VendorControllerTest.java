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
import tech.talci.api.v1.model.VendorDTO;
import tech.talci.domain.Vendor;
import tech.talci.exceptions.ResourceNotFoundException;
import tech.talci.services.VendorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
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
    public void testVendorNotFound() throws Exception{

        when(vendorService.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/33")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());

        verify(vendorService, times(1)).findById(any());
    }

    @Test
    public void testCreateVendor() throws Exception{
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setId(ID);
        vendor.setName(NAME);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setId(ID);
        returnDTO.setName(NAME);
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/2");

        when(vendorService.createNewVendor(vendor)).thenReturn(vendor);

        //when&then
        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.id", equalTo(ID)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/2")));

        verify(vendorService, times(1)).createNewVendor(any());
    }

    @Test
    public void testUpdateVendor() throws Exception{
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setId(ID);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setId(ID);
        returnDTO.setName(NAME);
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/2");

        when(vendorService.saveVendorDTO(ID, vendor)).thenReturn(returnDTO);

        //when&then
        mockMvc.perform(put(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL+"/2")));

        verify(vendorService, times(1)).saveVendorDTO(any(),any());

    }

    @Test
    public void testPathVendor() throws Exception{
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setId(ID);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setId(ID);
        returnDTO.setName(NAME);
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/2");

        when(vendorService.patchVendor(ID, vendor)).thenReturn(returnDTO);

        //when&then
        mockMvc.perform(patch(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));

        verify(vendorService, times(1)).patchVendor(any(), any());
    }

    @Test
    public void testDeleteVendor() throws Exception{

        mockMvc.perform(delete(VendorController.BASE_URL + "/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }

    private String asJson(Object obj){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(obj);
            return json;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}