package com.javaguru.onlineshop.comments;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {


    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<CommentDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CommentDTO findByID(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CommentDTO dto){
        CommentDTO created = service.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public void editByID(@PathVariable Long id, @Valid @RequestBody CommentDTO dto){
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteByID(@PathVariable Long id){
        service.delete(id);
    }

    @PostMapping("/{commentID}/product/{productID}")
    public void addCommentToProduct(@PathVariable Long commentID, @PathVariable Long productID){
        service.addCommentToProduct(commentID, productID);
    }
}
