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
	private final FileService fileService;

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
			msg.setFileName(fileService.upload(file));
		}
		log.info(String.format("Данное сообщение %s сохранен", msg));
		messageRepository.save(msg);
	}

	public List<Message> findAllById(Long id) {
		return messageRepository.findAllByAppUserId(id);
	}

	public void deleteById(Long id) {
		Message message = messageRepository.findById(id).orElse(null);
		fileService.delete(message.getFileName());
		messageRepository.deleteById(id);

		log.info(String.format("Уделено сообщение с id: %s", id));
	}

	public Message findById(Long id) {
		return messageRepository.findById(id).orElse(null);
	}

	public void update(Message message, MultipartFile file, Long id) {
		Message updatedMessage = messageRepository.findById(id).orElse(null);
		assert updatedMessage != null;
		updatedMessage.setText(message.getText());
		updatedMessage.setTag(message.getTag());
		if (!updatedMessage.getFileName().isBlank() && !file.isEmpty()) {
			fileService.delete(updatedMessage.getFileName());
			updatedMessage.setFileName(fileService.upload(file));
		}
		if (updatedMessage.getFileName().isBlank() && !file.isEmpty()) {
			updatedMessage.setFileName(fileService.upload(file));
		}
	}
}
