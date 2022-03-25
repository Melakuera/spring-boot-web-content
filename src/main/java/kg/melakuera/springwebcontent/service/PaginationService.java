package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationService {

    public List<Integer> getPage(Page<Message> page) {
        List<Integer> list = List.of(page.getNumberOfElements(), page.getNumber(), page.getSize(), page.getTotalPages());
        return list;
    }
}