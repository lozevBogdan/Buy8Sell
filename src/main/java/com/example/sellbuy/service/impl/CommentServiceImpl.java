package com.example.sellbuy.service.impl;

import com.example.sellbuy.repository.CommentRepository;
import com.example.sellbuy.service.CommentsService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentsService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void deleteByProductId(Long id) {
        this.commentRepository.deleteByProductEntityId(id);
    }
}
