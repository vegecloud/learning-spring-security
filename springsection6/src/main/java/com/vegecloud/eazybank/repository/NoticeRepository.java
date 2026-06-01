package com.vegecloud.eazybank.repository;

import com.vegecloud.eazybank.model.Notice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE CURRENT_DATE BETWEEN n.noticeBegDt AND n.noticeEndDt")
    List<Notice> findAllActiveNotices();
}
