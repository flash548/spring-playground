package com.galvanize.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface LessonRepository extends CrudRepository<Lesson, Long> {


    @Query(value="select * from lessons where title = :title", nativeQuery = true)
    public Lesson[] findByTitle(String title);

    @Query(value="select * from lessons where delivered_on < :date2 and delivered_on > :date1", nativeQuery = true)
    public Lesson[] findBetweenDates(String date1, String date2);
}
