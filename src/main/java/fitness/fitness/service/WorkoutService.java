

package fitness.fitness.service;

import fitness.fitness.model.Workout;
import fitness.fitness.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository repo;

    public List<Workout> getAll() {
        return repo.findAll();
    }

    public Workout getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Workout create(Workout workout) {
        return repo.save(workout);
    }

    public Workout update(String id, Workout workoutDetails) {
        Workout workout = repo.findById(id).orElse(null);
        if (workout != null) {
            workout.setTitle(workoutDetails.getTitle());
            workout.setDescription(workoutDetails.getDescription());
            workout.setReps(workoutDetails.getReps());
            workout.setSets(workoutDetails.getSets());
            workout.setWeight(workoutDetails.getWeight());
            return repo.save(workout);
        }
        return null;
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}