package tech.talci.services;

import tech.talci.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO findById(Long id);
}
