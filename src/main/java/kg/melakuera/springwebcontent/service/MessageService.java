package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Transactional
@Log
public class MessageService {

	private final MessageRepository messageRepository;
	private final FileService fileService;

	public Page<Message> findAll(Pageable pageable){
		return messageRepository.findAll(pageable);
	}

	public Page<Message> findByTagLike(String filter, Pageable pageable){
		if (filter != null && !filter.isEmpty()) {
			log.info(String.format("Фильтрация сообщении по тегу - %s",filter));
		}
		return messageRepository.findByTagLike(filter, pageable);
	}

	public void save(Message message, AppUser appUser, MultipartFile file) {
		Message msg = new Message(message.getText(), message.getTag(), appUser);
		if (!file.isEmpty()) {
			msg.setFileName(fileService.upload(file));
		}
		log.info(String.format("Данное сообщение %s сохранен", msg));
		messageRepository.save(msg);
	}

	public Page<Message> findAllById(Long id, Pageable pageable) {
		return messageRepository.findAllByAppUserId(id, pageable);
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
		System.out.println(updatedMessage);
		assert updatedMessage != null;
		updatedMessage.setText(message.getText());
		updatedMessage.setTag(message.getTag());
		if (updatedMessage.getFileName() != null && !file.isEmpty()) {
			fileService.delete(updatedMessage.getFileName());
			updatedMessage.setFileName(fileService.upload(file));
		}
		if (updatedMessage.getFileName() == null && !file.isEmpty()) {
			updatedMessage.setFileName(fileService.upload(file));
		}
	}

	public void like(AppUser appUser, Long id) {
		Message message = messageRepository.findById(id).orElse(null);
		assert message != null;
		message.getLikeUsers().add(appUser);
	}
	public void unlike(AppUser appUser, Long id) {
		Message message = messageRepository.findById(id).orElse(null);
		assert message != null;
		message.getLikeUsers().remove(appUser);
	}
}
