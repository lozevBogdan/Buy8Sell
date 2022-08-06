package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.binding.CommentBindingDto;
import com.example.sellbuy.model.entity.CommentEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.repository.CommentRepository;
import com.example.sellbuy.service.CommentsService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentsService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ProductService productService;

    public CommentServiceImpl(CommentRepository commentRepository, UserService userService,
                              @Lazy ProductService productService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    @Override
    public void deleteByProductId(Long id) {
        this.commentRepository.deleteByProductEntityId(id);
    }

    @Override
    public CommentEntity saveComment(CommentBindingDto commentBindingDto,
                                     Long productId, Long authorId) {
        ProductEntity product = this.productService.findById(productId);
        UserEntity author = this.userService.findById(authorId);
        CommentEntity newComment = new CommentEntity();
        newComment.
                setTextContent(commentBindingDto.getTextContent()).
                setAuthor(author).
                setProduct(product);


        return this.commentRepository.save(newComment);
    }
}
