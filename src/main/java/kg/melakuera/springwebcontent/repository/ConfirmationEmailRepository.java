package kg.melakuera.springwebcontent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kg.melakuera.springwebcontent.entity.ConfirmationEmail;

@Repository
public interface ConfirmationEmailRepository extends JpaRepository<ConfirmationEmail, Long> {
	
	Optional<ConfirmationEmail> findByToken(String token);
}
