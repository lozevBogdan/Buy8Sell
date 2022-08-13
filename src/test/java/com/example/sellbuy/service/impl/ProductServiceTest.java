package com.example.sellbuy.service.impl;

import com.example.sellbuy.init.TestDataInit;
import com.example.sellbuy.model.binding.CommentBindingDto;
import com.example.sellbuy.model.entity.CommentEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.repository.CommentRepository;
import com.example.sellbuy.repository.UserRepository;
import com.example.sellbuy.service.CommentsService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    @Autowired
    private TestDataInit testDataInit;

    private CommentsService commentsServiceToTest;

    @BeforeEach
    void setUp(){
        commentsServiceToTest = new CommentServiceImpl(commentRepository,userService,productService);
    }


    @Test
    void save_Comment(){

        UserEntity user = this.testDataInit.createTestUser("user@abv.bg");

        ProductEntity product = this.testDataInit.createTestProduct(
                "Test product 1,for testing purpose",
                "Test Product 1",
                BigDecimal.valueOf(20L),
                LocationEnum.SOFIA_GRAD,
                user,
                CategoryEnum.ELECTRONICS,
                true
        );

        CommentEntity newCommentForReturnWithId = testDataInit.createComment(user,product);

        CommentEntity newCommentForSaveWithOutId = testDataInit.createComment(user,product);
        newCommentForSaveWithOutId.setId(null);

        CommentBindingDto commentBindingDto = new CommentBindingDto().setTextContent("Test comment content.");




        when(productService.findById(product.getId())).
                thenReturn(product);

        when(userService.findById(user.getId())).
                thenReturn(user);

        when(commentRepository.save(newCommentForSaveWithOutId)).
                thenReturn(newCommentForReturnWithId);

        CommentEntity testComment = commentsServiceToTest.
                saveComment(commentBindingDto, product.getId(), user.getId());





        Assertions.assertEquals(user.getEmail(),testComment.getAuthor().getEmail());
        Assertions.assertEquals(user.getFirstName(),testComment.getAuthor().getFirstName());
        Assertions.assertEquals(user.getLastName(),testComment.getAuthor().getLastName());
        Assertions.assertEquals(newCommentForReturnWithId.getProduct().getTitle(),testComment.getProduct().getTitle());


    }


}
