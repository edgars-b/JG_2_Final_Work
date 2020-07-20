package com.javaguru.onlineshop.comments;

import com.javaguru.onlineshop.product.Product;
import com.javaguru.onlineshop.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService victim;

    @MockBean
    private CommentRepository repo;
    @MockBean
    private ProductRepository productRepo;

    @Test
    void shouldSaveComment() {
        Comment comment = createdComment();
        CommentDTO dto = new CommentDTO(comment.getId(),
                comment.getPostedDate(),
                comment.getMessage(),
                comment.getProductID());
        when(repo.save(comment)).thenReturn(comment);
        assertThat(victim.save(dto)).isEqualToComparingFieldByField(comment);
        verify(repo, times(1)).save(comment);
    }

    @Test
    void shouldFindAllComments() {
        List<Comment> commentList = new ArrayList<>();
        List<CommentDTO> dtoList = new ArrayList<>();

        Comment comment = createdComment();
        CommentDTO dto = new CommentDTO(comment.getId(),
                comment.getPostedDate(),
                comment.getMessage(),
                comment.getProductID());

        Comment comment2 = new Comment();
        comment2.setPostedDate(new Timestamp(new Date().getTime()));
        comment2.setMessage("test message 2");
        comment2.setProductID(1L);

        CommentDTO dto2 = new CommentDTO(comment2.getId(),
                comment2.getPostedDate(),
                comment2.getMessage(),
                comment2.getProductID());

        commentList.add(comment);
        commentList.add(comment2);
        dtoList.add(dto);
        dtoList.add(dto2);

        when(repo.findAll()).thenReturn(commentList);
        assertThat(victim.findAll()).isEqualToComparingOnlyGivenFields(dtoList);
    }

    @Test
    void shouldFindCommentByID() {
        Comment comment = createdComment();
        when(repo.findById(1L)).thenReturn(Optional.of(comment));
        victim.findById(1L);
        assertThat(victim.findById(1L)).isEqualToComparingFieldByField(comment);
        verify(repo, times(2)).findById(1L);
    }

    @Test
    void shouldEditComment() {
        Comment comment = createdComment();
        when(repo.findById(1L)).thenReturn(Optional.of(comment));

        comment.setMessage("edited");
        when(repo.save(comment)).thenReturn(comment);

        CommentDTO dto = new CommentDTO(comment.getId(),
                comment.getPostedDate(),
                comment.getMessage(),
                comment.getProductID());

        assertThat(victim.update(1L, dto)).isEqualToComparingOnlyGivenFields(comment);
    }

    @Test
    void shouldDeleteComment() {
        Comment comment = createdComment();
        when(repo.findById(1L)).thenReturn(Optional.of(comment));
        victim.delete(1L);

        when(repo.existsById(1L)).thenReturn(false);
        assertFalse(repo.existsById(comment.getId()));
        verify(repo).deleteById(1L);
    }

    private Comment createdComment() {
        Comment comment = new Comment();
        comment.setPostedDate(new Timestamp(new Date().getTime()));
        comment.setMessage("test message");
        comment.setProductID(1L);
        return comment;
    }

    private Product createdProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Testing product and comment");
        product.setRegularPrice(new BigDecimal("1.00"));
        product.setDescription("test des");
        product.setCategory("test cat");
        product.setDiscount(new BigDecimal("0"));
        product.setActualPrice(new BigDecimal("1.00"));
        return product;
    }
}