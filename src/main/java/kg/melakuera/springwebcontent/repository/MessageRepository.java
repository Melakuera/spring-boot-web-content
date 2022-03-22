package kg.melakuera.springwebcontent.repository;

import kg.melakuera.springwebcontent.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("SELECT m FROM Message m WHERE m.tag LIKE ?1%")
	List<Message> findByTagLike(String s);

	List<Message> findAllByAppUserId(Long id);
}