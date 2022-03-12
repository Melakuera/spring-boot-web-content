package kg.melakuera.springwebcontent.service;

import java.util.List;

import kg.melakuera.springwebcontent.entity.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.repository.MessageRepository;

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
