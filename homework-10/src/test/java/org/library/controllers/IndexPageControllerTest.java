package org.library.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.controllers.IndexPageController;
import org.library.models.Book;
import org.library.services.BookService;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexPageControllerTest {
    
    @Mock
    BookService bookService;

    @Mock
    Model model;

    IndexPageController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexPageController(bookService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage(){

        //given
        List<Book> books = new ArrayList<>();
        books.add(new Book());

        Book book = new Book();
        book.setId("1");
        books.add(book);

        when(bookService.findAll()).thenReturn(books);
        ArgumentCaptor<List<Book>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        //when
        String viewName = controller.listPage(model);

        //then
        assertEquals("index", viewName);
        verify(bookService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("books"), argumentCaptor.capture());
        List<Book> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

}
