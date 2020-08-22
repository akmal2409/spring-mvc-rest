package tech.talci.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.talci.api.v1.mapper.CategoryMapper;
import tech.talci.api.v1.model.CategoryDTO;
import tech.talci.domain.Category;
import tech.talci.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    private static final Long ID_VALUE1 = 1L;
    private static final Long ID_VALUE2 = 2L;
    private static final String NAME_VALUE = "Test name";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    public void getAllCategories() {
        //given
        Category category1 = new Category();
        category1.setId(ID_VALUE1);

        Category category2 = new Category();
        category2.setId(ID_VALUE2);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);

        //when
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryDTO> returnedCategories = categoryService.getAllCategories();

        //then
        assertNotNull(returnedCategories);
        assertEquals(2, returnedCategories.size());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void getCategoryByName() {
        // given
        Category category = new Category();
        category.setId(ID_VALUE1);
        category.setName(NAME_VALUE);

        //when
        when(categoryRepository.findByName(anyString())).thenReturn(category);

        CategoryDTO returnedDTO = categoryService.getCategoryByName("test");

        //then
        assertNotNull(returnedDTO);
        assertEquals(ID_VALUE1, returnedDTO.getId());
        assertEquals(NAME_VALUE, returnedDTO.getName());
    }

}