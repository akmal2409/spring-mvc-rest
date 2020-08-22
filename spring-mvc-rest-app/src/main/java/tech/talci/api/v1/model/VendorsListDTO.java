package tech.talci.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VendorsListDTO {
    List<VendorDTO> vendors;
}
