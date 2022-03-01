package kg.melakuera.springwebcontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kg.melakuera.springwebcontent.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
}
