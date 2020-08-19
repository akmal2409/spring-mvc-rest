package tech.talci.services;

import org.springframework.stereotype.Service;
import tech.talci.api.v1.mapper.VendorMapper;
import tech.talci.api.v1.model.VendorDTO;
import tech.talci.controllers.v1.VendorController;
import tech.talci.exceptions.ResourceNotFoundException;
import tech.talci.repositories.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        return vendorRepository.findAll()
                                .stream()
                                .map(vendor -> {
                                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                                    return vendorDTO;
                                })
                                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO findById(Long id) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found ID: " + id));
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public VendorDTO saveVendorDTO(Long id, VendorDTO vendorDTO) {
        return null;
    }

    public String getVendorUrl(Long id){
        return VendorController.BASE_URL + "/" + id;
    }
}
