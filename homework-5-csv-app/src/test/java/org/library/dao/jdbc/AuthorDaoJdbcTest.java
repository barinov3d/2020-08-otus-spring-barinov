package org.library.dao.jdbc;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.domain.Author;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorDaoJdbcTest {

    private static final String EXISTING_AUTHOR_NAME = "Zed A. Shaw";

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void shouldNotAddDuplicatedAuthorName() {
        assertThrows(DuplicateKeyException.class, () -> authorDaoJdbc.add(new Author(0, EXISTING_AUTHOR_NAME)));
    }
}