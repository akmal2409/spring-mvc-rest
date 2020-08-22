package tech.talci.api.v1.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
}
