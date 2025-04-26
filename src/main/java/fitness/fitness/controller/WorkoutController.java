package fitness.fitness.controller;


import fitness.fitness.model.Workout;
import fitness.fitness.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin(origins = "*")
public class WorkoutController {

    @Autowired
    private WorkoutService service;

    @GetMapping
    public List<Workout> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Workout getById(@PathVariable String id) {
        return service.getById(id);
    }

//    @PostMapping
//    public Workout create(@RequestBody Workout workout) {
//        return service.create(workout);
//    }

    @PutMapping("/{id}")
    public Workout update(@PathVariable String id, @RequestBody Workout workout) {
        return service.update(id, workout);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}