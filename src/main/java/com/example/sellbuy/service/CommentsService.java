package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.CommentBindingDto;
import com.example.sellbuy.model.entity.CommentEntity;
import com.example.sellbuy.model.entity.UserEntity;

public interface CommentsService {


    void deleteByProductId(Long id);

    CommentEntity saveComment(CommentBindingDto commentBindingDto, Long productId, Long AuthorId);
}
