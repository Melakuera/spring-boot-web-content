package kg.melakuera.springwebcontent.repository;

import kg.melakuera.springwebcontent.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("SELECT m FROM Message m WHERE m.tag LIKE ?1%")
	Page<Message> findByTagLike(String s, Pageable pageable);

	Page<Message> findAll(Pageable pageable);

	Page<Message> findAllByAppUserId(Long id, Pageable pageable);

	void deleteById(Long id);
}