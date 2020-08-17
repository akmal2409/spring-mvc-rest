package tech.talci.api.v1.mapper;

import org.junit.Test;
import tech.talci.api.v1.model.CategoryDTO;
import tech.talci.domain.Category;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
    private static final Long ID_VALUE = 1L;
    private static final String NAME_VALUE = "Tim";

    @Test
    public void categoryToCategoryDTO() {
        //given
        Category category = new Category();
        category.setName(NAME_VALUE);
        category.setId(ID_VALUE);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertNotNull(categoryDTO);
        assertEquals(ID_VALUE, categoryDTO.getId());
        assertEquals(NAME_VALUE, categoryDTO.getName());
    }
}