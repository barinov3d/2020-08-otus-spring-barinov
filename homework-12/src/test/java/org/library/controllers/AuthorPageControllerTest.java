package org.library.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.exceptions.*;
import org.library.models.Author;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthorPageControllerTest {

    @Mock
    BookService bookService;
    @Mock
    AuthorService authorService;

    AuthorPageController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new AuthorPageController(bookService, authorService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAuthor() throws Exception {

        Author author = new Author();
        author.setId("1");

        when(authorService.findById(anyString())).thenReturn(author);

        mockMvc.perform(get("/author/1/"))
                .andExpect(status().isOk())
                .andExpect(view().name("author"))
                .andExpect(model().attributeExists("author"));
    }

    @Test
    public void testGetAuthorNotFound() throws Exception {
        checkViewExceptionPage(new AuthorNotFoundException(""), status().isNotFound(), "404");
    }

    @Test
    public void testDuplicateAuthorBookException() throws Exception {
        checkViewExceptionPage(new DuplicateAuthorBookException(""), status().isConflict(), "409");
    }

    @Test
    public void testDuplicateGenreNameException() throws Exception {
        checkViewExceptionPage(new DuplicateGenreNameException(""), status().isConflict(), "409");
    }

    @Test
    public void testDuplicateAuthorNameException() throws Exception {
        checkViewExceptionPage(new DuplicateAuthorNameException(""), status().isConflict(), "409");
    }

    private void checkViewExceptionPage(RepositoryException e, ResultMatcher resultMatcher, String view) throws Exception {
        when(authorService.findById(anyString())).thenThrow(e);
        mockMvc.perform(get("/author/1/"))
                .andExpect(resultMatcher)
                .andExpect(view().name(view));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/author/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(authorService, times(1)).deleteById(anyString());
    }
}