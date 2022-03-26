package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    public int[] getPagination(Page<Message> page) {
        int totalPages = page.getTotalPages();
        if (totalPages > 7) {
            Integer pageNumber = page.getNumber() + 1;
            Integer[] head = pageNumber > 4 ?
                    new Integer[]{1, -1} : new Integer[]{1, 2, 3};
            Integer[] tail = pageNumber < (totalPages - 3) ?
                    new Integer[]{-1, totalPages} : new Integer[]{totalPages - 2, totalPages - 1, totalPages};
            Integer[] bodyBefore = (pageNumber > 4 && pageNumber < (totalPages - 1)) ?
                    new Integer[]{pageNumber - 2, pageNumber - 1} : new Integer[]{};
            Integer[] bodyAfter = (pageNumber > 2 && pageNumber < (totalPages - 3)) ?
                    new Integer[]{pageNumber + 1, pageNumber + 2} : new Integer[]{};

            List<Integer> list = new ArrayList<>();
            Collections.addAll(list, head);
            Collections.addAll(list, bodyBefore);
            Collections.addAll(list, (pageNumber > 3 && pageNumber < totalPages - 2) ?
                    new Integer[]{pageNumber} : new Integer[]{});
            Collections.addAll(list, bodyAfter);
            Collections.addAll(list, tail);
            Integer[] arr= list.toArray(new Integer[0]);

            return Arrays.stream(arr).mapToInt(Integer::intValue).toArray();
        } else {
            return IntStream.rangeClosed(1, totalPages).toArray();
        }
    }
}