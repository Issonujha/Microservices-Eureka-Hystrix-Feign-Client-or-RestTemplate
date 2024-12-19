package EdTech.Course.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import EdTech.Course.dto.CourseDto;
import EdTech.Course.dto.ResponseMessage;
import EdTech.Course.model.Course;
import EdTech.Course.model.CourseMaterial;
import EdTech.Course.service.CourseService;

@RestController
@RequestMapping("/courses")
public class courseController {

	@Autowired
	private CourseService courseService;

	@GetMapping
	public List<Course> getAllCourses() {
		return courseService.getAllCourses();
	}

	@GetMapping("/{id}")
	public Course getCourseById(@PathVariable Long id) {
		return courseService.getCourseById(id);
	}

	@GetMapping("/name")
	public Course getCourseByName(@RequestParam("name") String name) {
		return courseService.getCourseByName(name);
	}

	@GetMapping("/courseMaterial/")
	public List<CourseMaterial> getCourseMaterialByCourseId(@RequestParam("id") Long id) {
		return courseService.getCourseMaterialByCourseId(id);
	}

	@GetMapping("/instructor")
	public Course getCourseByInstructor(@RequestParam("instructor") String instructor) {
		return courseService.getCourseByInstructor(instructor);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseMessage createCourse(@RequestBody CourseDto courseDto) {
		courseService.createCourse(courseDto);
		return new ResponseMessage("Course Added Successfully");
	}

	@PutMapping("/{id}")
	public ResponseMessage updateCourse(@PathVariable Long id, @RequestBody CourseDto updatedCourseDto) {
		courseService.updateCourse(id, updatedCourseDto);
		return new ResponseMessage("Course Updated Successfully");
	}

	@DeleteMapping("/{id}")
	public ResponseMessage deleteCourse(@PathVariable Long id) {
		courseService.deleteCourse(id);
		return new ResponseMessage("Course Deleted Successfully");
	}

	@PostMapping("/course/{courseId}/register/{userId}")
	@HystrixCommand(fallbackMethod = "Demo_fallback")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseMessage registerForCourse(@RequestHeader("Authorization") String authorization,
			@PathVariable("courseId") Long courseId, @PathVariable("userId") Long userId) {
		courseService.createEnrollmentForCourse(authorization, courseId, userId);
		return new ResponseMessage("Student Enrolled Successfully");
	}

	public ResponseMessage Demo_fallback(@RequestHeader("Authorization") String authorization,
			@PathVariable("courseId") Long courseId, @PathVariable("userId") Long userId) {
		return new ResponseMessage("Services not available");
	}

}
