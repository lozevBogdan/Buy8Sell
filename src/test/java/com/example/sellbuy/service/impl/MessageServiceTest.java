package com.example.sellbuy.service.impl;


import com.example.sellbuy.init.TestDataInit;
import com.example.sellbuy.model.binding.MessageBindingModel;
import com.example.sellbuy.model.entity.MessageEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.model.view.messages.MessageChatViewModel;
import com.example.sellbuy.repository.MessageRepository;
import com.example.sellbuy.service.CommentsService;
import com.example.sellbuy.service.MessageService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Autowired
    private TestDataInit testDataInit;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private ProductService productService;

   private MessageService messageServiceToTest;

// todo: check this case!!!!!!!!!!
//    private UserEntity sender;
//
//    private UserEntity receiver;
//    private ProductEntity product;
//
//    private MessageEntity messageBeforeSave;
//
//    private MessageEntity messageAfterSave;

//    @BeforeEach
//    void setUp(){
//
//        messageServiceToTest = new MessageServiceImpl(messageRepository,userService,modelMapper,productService);
//
//        sender = this.testDataInit.createTestUser("sender@abv.bg");
//
//        receiver = this.testDataInit.createTestUser("receiver@abv.bg");
//
//        product = this.testDataInit.createTestProduct(
//                "Test product 1,for testing purpose",
//                "Test Product 1",
//                BigDecimal.valueOf(20L),
//                LocationEnum.SOFIA_GRAD,
//                receiver,
//                CategoryEnum.ELECTRONICS,
//                true
//        );
//
//        messageBeforeSave =
//                this.testDataInit.createMessage("Test message",sender,receiver,product);
//        messageBeforeSave.setId(null);
//
//        messageAfterSave =
//                this.testDataInit.createMessage("Test message",sender,receiver,product);
//
//    }
//
//    @AfterEach
//    void tearDown(){
//        this.testDataInit.cleanUpDatabase();
//    }


    @Test
    void createAndSave_Message(){

        messageServiceToTest = new MessageServiceImpl(messageRepository,userService,modelMapper,productService);

       UserEntity sender = this.testDataInit.createTestUser("sender@abv.bg");

        UserEntity  receiver = this.testDataInit.createTestUser("receiver@abv.bg");

      ProductEntity  product = this.testDataInit.createTestProduct(
                "Test product 1,for testing purpose",
                "Test Product 1",
                BigDecimal.valueOf(20L),
                LocationEnum.SOFIA_GRAD,
                receiver,
                CategoryEnum.ELECTRONICS,
                true
        );

      MessageEntity messageBeforeSave =
                this.testDataInit.createMessage("Test message",sender,receiver,product);
        messageBeforeSave.setId(null);

        MessageEntity messageAfterSave =
                this.testDataInit.createMessage("Test message",sender,receiver,product);


        MessageBindingModel messageBindingModel =
                new MessageBindingModel().setMessage(messageAfterSave.getMessage());

        when(userService.findById(receiver.getId())).
                thenReturn(receiver);

        when(userService.findById(sender.getId())).
                thenReturn(sender);

        when(productService.findById(product.getId())).
                thenReturn(product);

        when(messageRepository.save(messageBeforeSave)).
                thenReturn(messageAfterSave);

        MessageEntity testMessage =
                messageServiceToTest.createAndSave(
                        messageBindingModel, product.getId(), receiver.getId(), sender.getId()
                );


        Assertions.assertEquals(testMessage.getMessage(),messageAfterSave.getMessage());
        Assertions.assertEquals(testMessage.getProduct().getTitle(),messageAfterSave.getProduct().getTitle());
        Assertions.assertEquals(testMessage.getSender().getEmail(),messageAfterSave.getSender().getEmail());
        Assertions.assertEquals(testMessage.getReceiver().getEmail(),messageAfterSave.getReceiver().getEmail());

    }

    @Test
    void getMessageBySenderId_AndReceiverId(){

        messageServiceToTest = new MessageServiceImpl(messageRepository,userService,modelMapper,productService);

        UserEntity sender = this.testDataInit.createTestUser("sender2@abv.bg");

        UserEntity  receiver = this.testDataInit.createTestUser("receiver2@abv.bg");

        ProductEntity  product = this.testDataInit.createTestProduct(
                "Test product 1,for testing purpose",
                "Test Product 1",
                BigDecimal.valueOf(20L),
                LocationEnum.SOFIA_GRAD,
                receiver,
                CategoryEnum.ELECTRONICS,
                true
        );

        MessageEntity messageBeforeSave =
                this.testDataInit.createMessage("Test message",sender,receiver,product);
        messageBeforeSave.setId(null);

        MessageEntity messageAfterSave =
                this.testDataInit.createMessage("Test message",sender,receiver,product);


        MessageBindingModel messageBindingModel =
                new MessageBindingModel().setMessage(messageAfterSave.getMessage());


        Set<MessageEntity> expectedSetOfMessageEntity = Set.of(messageAfterSave);

        when(messageRepository.findBySenderIdAndReceiverId(sender.getId(),receiver.getId())).
                thenReturn(Set.of(messageAfterSave));

        Set<MessageEntity> actualSetOfMessageEntity =
                messageServiceToTest.getMessageBySenderAndReceiver(sender.getId(),receiver.getId());

        Assertions.assertEquals(actualSetOfMessageEntity.size(),expectedSetOfMessageEntity.size());
    }

    @Test
    void findChatsMessagesByProductIdSenderIdReceiverId(){


        messageServiceToTest = new MessageServiceImpl(messageRepository,userService,modelMapper,productService);

        UserEntity sender = this.testDataInit.createTestUser("sender3@abv.bg");

        UserEntity  receiver = this.testDataInit.createTestUser("receiver3@abv.bg");

        ProductEntity  product = this.testDataInit.createTestProduct(
                "Test product 1,for testing purpose",
                "Test Product 1",
                BigDecimal.valueOf(20L),
                LocationEnum.SOFIA_GRAD,
                receiver,
                CategoryEnum.ELECTRONICS,
                true
        );

        MessageEntity messageBeforeSave =
                this.testDataInit.createMessage("Test message",sender,receiver,product);
        messageBeforeSave.setId(null);

        MessageEntity messageAfterSave =
                this.testDataInit.createMessage("Test message",sender,receiver,product);


        MessageBindingModel messageBindingModel =
                new MessageBindingModel().setMessage(messageAfterSave.getMessage());

        when(productService.findById(product.getId())).
                thenReturn(product);

        Set<MessageChatViewModel> expectedProductMessages =
                product.getMessages().stream().map(this::mapToMessageChatViewModel).
                        collect(Collectors.toSet());

        Set<MessageChatViewModel> actualProductMessages = this.messageServiceToTest.
                findChatsMessagesByProductIdSenderIdReceiverId(product.getId(),sender.getId(),receiver.getId());

        Assertions.assertEquals(actualProductMessages.size(),expectedProductMessages.size());
    }

    private MessageChatViewModel mapToMessageChatViewModel(MessageEntity messageEntity){
        return this.modelMapper.map(messageEntity,MessageChatViewModel.class);
    }



}
