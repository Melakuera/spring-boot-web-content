package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Log
public class MessageService {
	
	private final MessageRepository messageRepository;
	private final FileUploadService fileUploadService;

	public List<Message> findAll(){
		return messageRepository.findAll();
	}
	
	public List<Message> findByTagLike(String filter){
		if (filter != null && !filter.isEmpty()) {
			log.info(String.format("Фильтрация сообщении по тегу - %s",filter));
		}
		return messageRepository.findByTagLike(filter);
	}

	public void save(Message message, AppUser appUser, MultipartFile file) {
		Message msg = new Message(message.getText(), message.getTag(), appUser);
		if (!file.isEmpty()) {
			msg.setFileName(fileUploadService.upload(file));
		}
		log.info(String.format("Данное сообщение %s сохранен", msg));
		messageRepository.save(msg);
	}

	public List<Message> findAllById(Long id) {
		return messageRepository.findAllByAppUserId(id);
	}
}
