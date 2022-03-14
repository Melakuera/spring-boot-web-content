package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class MessageService {
	
	private final MessageRepository messageRepository;

	public List<Message> findAll(){
		return messageRepository.findAll();
	}
	
	public void save(Message message){
		messageRepository.save(message);
	}
	
	public List<Message> findByTagLike(String filter){
		return messageRepository.findByTagLike(filter);
	}
}
