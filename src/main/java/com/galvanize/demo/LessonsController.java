package com.galvanize.demo;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }
    @GetMapping("/{id}")
    public Lesson getId(@PathVariable String id, HttpServletResponse response) {
        Optional<Lesson> retVal = this.repository.findById(Long.valueOf(id));
        if (retVal.isPresent()) {
            return retVal.get();
        }
        else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public String deleteId(@PathVariable String id, HttpServletResponse response) {
        try {
            this.repository.deleteById(Long.valueOf(id));
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "Done";
    }

    @PatchMapping("/{id}")
    public Lesson updateId(@PathVariable String id, @RequestBody Lesson l, HttpServletResponse response) {
        try {
            Optional<Lesson> rec = this.repository.findById(Long.valueOf(id));
            if (rec.isPresent()) {
                Lesson update = rec.get();
                update.setTitle(l.getTitle());
                update.setDeliveredOn(l.getDeliveredOn());
                return this.repository.save(update);
            }
            else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

}
