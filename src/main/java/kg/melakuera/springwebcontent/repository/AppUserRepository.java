package kg.melakuera.springwebcontent.repository;

import kg.melakuera.springwebcontent.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	Optional<AppUser> findByEmail(String email);

	Optional<AppUser> findByResetPasswordCode(String code);

	@Modifying
	@Query("UPDATE AppUser a SET a.enabled = true WHERE a.email = ?1")
	void updateEnabled(String email);
}
