package com.galvanize.demo;

import org.springframework.web.bind.annotation.*;

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
    public Lesson getId(@PathVariable String id) {
        Optional<Lesson> retVal = this.repository.findById(Long.valueOf(id));
        if (retVal.isPresent()) {
            return retVal.get();
        }
        else {
            return new Lesson();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteId(@PathVariable String id) {
        try {
            this.repository.deleteById(Long.valueOf(id));
        }
        catch (Exception e) {}

        return "Done";
    }

}
