package tech.talci.services;

import org.springframework.stereotype.Service;
import tech.talci.api.v1.mapper.VendorMapper;
import tech.talci.api.v1.model.VendorDTO;
import tech.talci.repositories.VendorRepository;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;
    private VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return null;
    }

    @Override
    public VendorDTO findById(Long id) {
        return null;
    }
}
