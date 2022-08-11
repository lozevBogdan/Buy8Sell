package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.binding.MessageBindingModel;
import com.example.sellbuy.model.entity.MessageEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.view.messages.MessageChatViewModel;
import com.example.sellbuy.repository.MessageRepository;
import com.example.sellbuy.service.MessageService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl  implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService, ModelMapper modelMapper, @Lazy ProductService productService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @Override
    public MessageEntity addInDb(MessageEntity message) {
        return this.messageRepository.save(message);
    }

    @Override
    public MessageEntity createAndSave(MessageBindingModel messageBindingModel, Long productId, Long receiverId, Long currentUserId) {

        UserEntity sender = this.userService.findById(currentUserId);
        UserEntity receiver = this.userService.findById(receiverId);
        ProductEntity product = this.productService.findById(productId);
        MessageEntity message = new MessageEntity();
        message.
                setMessage(messageBindingModel.getMessage()).
                setReceiver(receiver).setSender(sender).
                setProduct(product);

        message = this.messageRepository.save(message);
        return message;
    }

    @Override
    public Set<MessageEntity> getMessageBySenderAndReceiver(Long senderId, Long receiverId) {

        return this.messageRepository.findBySenderIdAndReceiverId(senderId,receiverId);

    }

    @Transactional
    @Override
    public void deleteByProductId(Long id) {
        this.messageRepository.deleteByProductId(id);
    }

    @Override
    public Set<MessageChatViewModel> findChatsMessagesByProductIdSenderIdReceiverId(Long productId, Long senderId, Long currentUsedId) {

        Set<MessageEntity> productMessages = this.productService.
                findById(productId).
                getMessages();

        Set<MessageEntity> receivedMessages =
                productMessages.
                        stream().
                        filter(m->((m.getSender().getId()==senderId)&& (m.getReceiver().getId()==currentUsedId))).
                        collect(Collectors.toSet());

        Set<MessageEntity> sendedMessages  =
                productMessages.
                        stream().
                        filter(m->((m.getReceiver().getId()==senderId) && (m.getSender().getId()==currentUsedId))).
                        collect(Collectors.toSet());

        Set<MessageEntity> allMessages = new HashSet<>();
        allMessages.addAll(receivedMessages);
        allMessages.addAll(sendedMessages);

        return  allMessages.
                stream().
                sorted((a,b)-> {
                            if (b.getCreated().isBefore(a.getCreated())) {
                                return 1;
                            }
                            else if (a.getCreated().isBefore(b.getCreated())) {
                                return -1;
                            }
                            else  {
                                return 0;
                            }
                        }
                ).map(this::mapToMessageChatViewModel).
                collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private MessageChatViewModel mapToMessageChatViewModel(MessageEntity messageEntity){
        return this.modelMapper.map(messageEntity,MessageChatViewModel.class);
    }

}
