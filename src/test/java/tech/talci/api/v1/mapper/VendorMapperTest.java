package tech.talci.api.v1.mapper;

import org.junit.Before;
import org.junit.Test;
import tech.talci.api.v1.model.VendorDTO;
import tech.talci.domain.Vendor;

import javax.print.attribute.standard.MediaSize;

import static org.junit.Assert.*;

public class VendorMapperTest {

    VendorMapper vendorMapper = VendorMapper.INSTANCE;
    static final String NAME = "Name";


    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertNotNull(vendorDTO);
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        //then
        assertNotNull(vendor);
        assertEquals(NAME, vendor.getName());
    }
}