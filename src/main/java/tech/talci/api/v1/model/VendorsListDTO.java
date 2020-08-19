package tech.talci.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.talci.domain.Vendor;

import java.util.List;

@Data
@AllArgsConstructor
public class VendorsListDTO {
    List<Vendor> vendors;
}
