package com.javaguru.onlineshop.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CommentControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private CommentRepository victim;

}