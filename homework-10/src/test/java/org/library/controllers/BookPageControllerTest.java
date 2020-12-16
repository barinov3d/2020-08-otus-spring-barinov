package org.library.controllers;

import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.models.Book;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.library.services.CommentService;
import org.library.services.GenreService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

/*
    @Test
    public void testGetBookNotFound() throws Exception {

        when(bookService.findById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/book/1/"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @Test
    public void testGetNewBookForm() throws Exception {
        Book book = new Book();

        mockMvc.perform(get("/book/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    public void testPostNewBookForm() throws Exception {
        Book book = new Book();
        book.setId("2");

        when(bookService.save(any())).thenReturn(book);

        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some directions")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/2/show"));
    }

    @Test
    public void testPostNewBookFormValidationFail() throws Exception {
        Book book = new Book();
        book.setId("2");

        when(bookService.save(any())).thenReturn(book);

        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("cookTime", "3000")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book/bookform"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        Book book = new Book();
        book.setId("2");

        when(bookService.findById(anyString())).thenReturn(book);

        mockMvc.perform(get("/book/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"))
                .andExpect(model().attributeExists("book"));
    }
*/

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/book/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(bookService, times(1)).deleteById(anyString());
    }
}