package com.javaguru.onlineshop.comments;

import com.javaguru.onlineshop.exceptions.NotFoundException;
import com.javaguru.onlineshop.product.Product;
import com.javaguru.onlineshop.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository repo;
    private final ProductRepository productRepo;

    public CommentService(CommentRepository repo, ProductRepository productRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
    }

    public List<CommentDTO> findAll() {
        return repo.findAll()
                .stream()
                .map(comment -> new CommentDTO(comment.getId(),
                        comment.getPostedDate(),
                        comment.getMessage(),
                        comment.getProductID()))
                .collect(Collectors.toList());
    }

    public CommentDTO findById(Long id) {
        Comment comment = repo.findById(id).orElseThrow(() -> new NotFoundException("No such comment found: ID - " + id));
        return new CommentDTO(comment.getId(),
                comment.getPostedDate(),
                comment.getMessage(),
                comment.getProductID());
    }

    public CommentDTO save(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setMessage(dto.getMessage());
        comment.setPostedDate(dto.getPostedDate());
        comment.setProductID(dto.getProductID());
        repo.save(comment);
        return new CommentDTO(comment.getId(),
                comment.getPostedDate(),
                comment.getMessage(),
                comment.getProductID());
    }

    public CommentDTO update(Long id, CommentDTO dto) {
        Comment comment = repo.findById(id).orElseThrow(() -> new NotFoundException("No such comment found: ID - " + id));
        comment.setMessage(dto.getMessage());
        comment.setPostedDate(dto.getPostedDate());
        comment.setProductID(dto.getProductID());
        repo.save(comment);
        return new CommentDTO(comment.getId(),
                comment.getPostedDate(),
                comment.getMessage(),
                comment.getProductID());
    }

    public void delete(Long id) {
        repo.findById(id).orElseThrow(() -> new NotFoundException("No such comment found: ID - " + id));
        repo.deleteById(id);
    }

    public void addCommentToProduct(Long commentID, Long productID) {
        Comment comment = repo.findById(commentID).orElseThrow(() -> new NotFoundException("No such comment found: ID - " + commentID));
        Product product = productRepo.findById(productID).orElseThrow(() -> new NotFoundException("No such product found: ID - " + productID));
        product.getComments().add(comment);
        repo.save(comment);
    }

    public void deleteCommentFromProduct(Long commentID, Long productID) {
        Comment comment = repo.findById(commentID).orElseThrow(() -> new NotFoundException("No such comment found: ID - " + commentID));
        Product product = productRepo.findById(productID).orElseThrow(() -> new NotFoundException("No such product found: ID - " + productID));
        product.getComments().remove(comment);
        repo.deleteById(commentID);
    }
}
