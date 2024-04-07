package net.prateekdazz.springsecurityproject.repository;

import net.prateekdazz.springsecurityproject.entity.Notice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoticeRepository  extends CrudRepository<Notice, Long> {


    @Query(value = "from Notice n where CURDATE() BETWEEN noticBegDt AND noticEndDt")
    List<Notice> findAllActiveNotices();
}
