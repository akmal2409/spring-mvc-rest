package tech.talci.services;

import com.sun.xml.bind.v2.model.core.ID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import tech.talci.api.v1.mapper.VendorMapper;
import tech.talci.api.v1.model.VendorDTO;
import tech.talci.controllers.v1.VendorController;
import tech.talci.domain.Vendor;
import tech.talci.repositories.VendorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    static final Long ID_VALUE = 2L;
    static final String NAME = "Test";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendorList = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendorList);

        //when
        List<VendorDTO> vendorDTOList = vendorService.getAllVendors();

        //then
        assertNotNull(vendorDTOList);
        assertEquals(3, vendorDTOList.size());
        verify(vendorRepository, times(1)).findAll();
    }

    @Test
    public void findById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID_VALUE);
        Optional<Vendor> vendorOptional = Optional.of(vendor);

        when(vendorRepository.findById(anyLong())).thenReturn(vendorOptional);

        //when
        VendorDTO vendorDTO = vendorService.findById(2L);

        //then
        assertNotNull(vendorDTO);
        assertEquals(ID_VALUE, vendorDTO.getId());

        verify(vendorRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCreateNewVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID_VALUE);
        vendor.setName(NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO vendorDTO = vendorService.createNewVendor(new VendorDTO());

        //then
        assertNotNull(vendorDTO);
        assertEquals(VendorController.BASE_URL + "/2", vendorDTO.getVendorUrl());
        assertEquals(NAME, vendorDTO.getName());
        assertEquals(ID_VALUE, vendorDTO.getId());

        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    public void testSaveVendorDTO() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID_VALUE);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(NAME);
        savedVendor.setId(ID_VALUE);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO returnDTO = vendorService.saveVendorDTO(ID_VALUE, vendorDTO);

        //then
        assertNotNull(returnDTO);
        assertEquals(ID_VALUE, returnDTO.getId());
        assertEquals(NAME, returnDTO.getName());
        assertEquals(VendorController.BASE_URL + "/2", returnDTO.getVendorUrl());

        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }
}