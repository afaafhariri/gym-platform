package fitness.fitness.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "PAF collection")
public class Workout {

    @Id
    private String id;
    private String title;
    private String description;
    private String type;
    private int reps;
    private int sets;
    private double weight;
    private LocalDateTime createdAt;

    public Workout() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; } //this is good

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }                      // ✅ Getter
    public void setType(String type) { this.type = type; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}