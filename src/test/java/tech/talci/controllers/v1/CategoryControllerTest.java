package tech.talci.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.talci.api.v1.model.CategoryDTO;
import tech.talci.services.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    private static final String NAME1 = "Test";
    private static final String NAME2 = "Test name 2";
    public static final long ID1 = 1L;
    public static final long ID2 = 2L;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testListCategories() throws Exception{
        //given
        CategoryDTO categoryDTO1 = new CategoryDTO();
        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO1.setId(ID1);
        categoryDTO2.setId(ID2);
        categoryDTO1.setName(NAME1);
        categoryDTO2.setName(NAME2);

        List<CategoryDTO> categories = Arrays.asList(categoryDTO1, categoryDTO2);

        //when
        when(categoryService.getAllCategories()).thenReturn(categories);

        //then
        mockMvc.perform(get("/api/v1/categories/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));

    }

    @Test
    public void testGetByNameCategory() throws Exception{
        //given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID1);
        categoryDTO.setName(NAME1);

        //when
        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        //then
        mockMvc.perform(get("/api/v1/categories/Test")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", equalTo(NAME1)));
    }
}