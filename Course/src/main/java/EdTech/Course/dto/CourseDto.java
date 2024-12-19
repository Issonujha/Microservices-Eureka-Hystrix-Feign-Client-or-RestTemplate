package EdTech.Course.dto;

import java.util.List;

import EdTech.Course.model.CourseMaterial;
import EdTech.Course.model.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
    private String name;
    private String description;
    private String instructor;
    private Long amount;
    private List<CourseMaterial> courseMaterial;
    private List<Enrollment> enrollments;
}
