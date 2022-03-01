package kg.melakuera.springwebcontent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.repository.MessageRepository;

@Service
@Transactional
public class MessageService {
	
	private final MessageRepository messageRepository;

	@Autowired
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public List<Message> findAll(){
		return messageRepository.findAll();
	}
	
	public void save(Message message){
		messageRepository.save(message);
	}
	
	

}
