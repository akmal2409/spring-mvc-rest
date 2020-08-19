package tech.talci.api.v1.mapper;

import org.junit.Before;
import org.junit.Test;
import tech.talci.api.v1.model.VendorDTO;
import tech.talci.domain.Vendor;

import static org.junit.Assert.*;

public class VendorMapperTest {

    VendorMapper vendorMapper = VendorMapper.INSTANCE;
    static final String NAME = "Name";
    static final Long ID = 1L;


    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertNotNull(vendorDTO);
        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
    }
}