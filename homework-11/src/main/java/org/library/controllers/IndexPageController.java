package org.library.controllers;

import lombok.AllArgsConstructor;
import org.library.models.Book;
import org.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class IndexPageController {
    private final BookService bookService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "index";
    }

}
