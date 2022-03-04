package kg.melakuera.springwebcontent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kg.melakuera.springwebcontent.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	Optional<AppUser> findByEmail(String email);
}
