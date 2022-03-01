package kg.melakuera.springwebcontent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kg.melakuera.springwebcontent.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	@Query("SELECT m FROM Message m WHERE m.tag LIKE ?1%")
	List<Message> findByTagLike(String s);
}
