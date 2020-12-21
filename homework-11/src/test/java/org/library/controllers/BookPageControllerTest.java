package org.library.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.exceptions.*;
import org.library.models.Book;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.library.services.CommentService;
import org.library.services.GenreService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookPageControllerTest {

    @Mock
    BookService bookService;
    @Mock
    AuthorService authorService;
    @Mock
    GenreService genreService;
    @Mock
    CommentService commentService;

    BookPageController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new BookPageController(bookService, authorService, genreService, commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetBook() throws Exception {

        Book book = new Book();
        book.setId("1");

        when(bookService.findById(anyString())).thenReturn(book);

        mockMvc.perform(get("/book/1/"))
                .andExpect(status().isOk())
                .andExpect(view().name("book"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    public void testGetBookNotFound() throws Exception {
        checkViewExceptionPage(new BookNotFoundException(""), status().isNotFound(), "404");
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
        when(bookService.findById(anyString())).thenThrow(e);
        mockMvc.perform(get("/book/1/"))
                .andExpect(resultMatcher)
                .andExpect(view().name(view));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/book/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(bookService, times(1)).deleteById(anyString());
    }
}