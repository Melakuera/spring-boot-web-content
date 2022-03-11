package kg.melakuera.springwebcontent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kg.melakuera.springwebcontent.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	Optional<AppUser> findByEmail(String email);

	@Modifying
	@Query("UPDATE AppUser a SET a.enabled = true WHERE a.email = ?1")
	void updateEnabled(String email);
}
