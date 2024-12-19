package EdTech.Course.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EdTech.Course.dto.CourseDto;
import EdTech.Course.dto.Payment;
import EdTech.Course.dto.ResponseMessage;
import EdTech.Course.feign.PaymentService;
import EdTech.Course.feign.UserService;
import EdTech.Course.model.Course;
import EdTech.Course.model.CourseMaterial;
import EdTech.Course.model.Enrollment;
import EdTech.Course.repository.CourseRepository;

@Service
public class CourseService {
	
	
	private CourseRepository courseRepository;
//	private RestTemplate restTemplate;
	private UserService userService;
	private PaymentService paymentService;
	
	
	
	@Autowired
	public CourseService(CourseRepository courseRepository,
			// RestTemplate restTemplate
			UserService userService, PaymentService paymentService) {
		this.courseRepository = courseRepository;
		this.userService = userService;
		this.paymentService = paymentService;
//		this.restTemplate = restTemplate;
	}
	

	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Course getCourseById(Long id) {
		Optional<Course> course = courseRepository.findById(id);
		return course.isPresent() ? course.get() : null;
	}

	public Course getCourseByName(String name) {
		return courseRepository.findByName(name);
	}

	public List<CourseMaterial> getCourseMaterialByCourseId(Long id) {
		Optional<Course> course = courseRepository.findById(id);
		return course != null ? course.get().getCourseMaterial() : null;
	}

	public Course getCourseByInstructor(String instructor) {
		return courseRepository.findByInstructor(instructor);
	}

	public ResponseMessage createCourse(CourseDto courseDto) {
		Course course = Course.builder().amount(courseDto.getAmount()).description(courseDto.getDescription())
				.instructor(courseDto.getInstructor()).name(courseDto.getName()).build();
		List<CourseMaterial> courseMaterials = courseDto.getCourseMaterial();
		if (courseMaterials != null) {
			for (CourseMaterial material : courseMaterials) {
				material.setCourse(course); // Set the course reference
			}
			course.setCourseMaterial(courseMaterials); // Assign materials to the course
		}

		List<Enrollment> enrollments = courseDto.getEnrollments();
		if (enrollments != null) {
			for (Enrollment enrollment : enrollments) {
				enrollment.setCourse(course); // Set the course reference
			}
			course.setEnrollment(enrollments); // Assign materials to the course
		}
		courseRepository.save(course);
		return new ResponseMessage("Course Added Successfully");
	}

	public ResponseMessage updateCourse(Long id, CourseDto updatedCourseDto) {
		Course course = getCourseById(id);
		if (course != null) {
			course = Course.builder().id(id).amount(updatedCourseDto.getAmount())
					.description(updatedCourseDto.getDescription()).instructor(updatedCourseDto.getInstructor())
					.name(updatedCourseDto.getName()).build();
			List<CourseMaterial> courseMaterials = updatedCourseDto.getCourseMaterial();
			if (courseMaterials != null) {
				for (CourseMaterial material : courseMaterials) {
					material.setCourse(course); // Set the course reference
				}
				course.setCourseMaterial(courseMaterials); // Assign materials to the course
			}

			List<Enrollment> enrollmentls = updatedCourseDto.getEnrollments();
			if (enrollmentls != null) {
				for (Enrollment enrollment : enrollmentls) {
					enrollment.setCourse(course); // Set the course reference
				}
				course.setEnrollment(enrollmentls); // Assign materials to the course
			}
			courseRepository.save(course);
		}
		return new ResponseMessage("Course Updated Successfully");
	}

	public ResponseMessage deleteCourse(Long id) {
		Course course = getCourseById(id);
		if (course != null) {
			courseRepository.delete(course);
		}
		return new ResponseMessage("Course Deleted Successfully");
	}

	public ResponseMessage createEnrollmentForCourse(String authorization, Long courseId, Long userId) {
		Course course = getCourseById(courseId);
		if (course != null && authorization != null && !authorization.isEmpty()) {
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", authorization);
//			HttpEntity<Void> entity = new HttpEntity<>(headers);
//			ResponseEntity<UserDetailsResponse> userDetailsResponse = restTemplate
//					.exchange("http://localhost:8083/user/" + userId, HttpMethod.GET, entity, UserDetailsResponse.class);
			Object user = userService.getUserById(
					"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzamhhOTU2M0BnbWFpbC5jb20iLCJpYXQiOjE3MzQ2Mzg1NjcsImV4cCI6MTczNDcyNDk2N30.HGKax0WgYWhcltB-L9NopusOarjsUgibd-u1UCkZ4sb4gNZORucuZU1VXauHUMRYtH4n8WZuCHJJ53yDz7TTlQ",
					userId);
			if (user == null)
				throw new RuntimeException("User not found");
			if (user != null) {
				Enrollment enrollment = Enrollment.builder().userId(userId).course(course).build();
				if (course.getEnrollment() != null) {
					course.getEnrollment().add(enrollment);
				} else {
					course.setEnrollment(Arrays.asList(enrollment));
				}
				courseRepository.save(course);
				// creating payment
//		        String paymentServiceUrl = "http://payment-service/payment";
				Payment payment = new Payment();
				payment.setCourseId(courseId);
				payment.setUserId(userId);
				payment.setAmount(enrollment.getCourse().getAmount());
//		        restTemplate.postForObject(paymentServiceUrl, payment, Payment.class);
				paymentService.createPayment(payment);
				return new ResponseMessage("Student Enrolled Successfully");
			}
		}
		return new ResponseMessage("Error");
	}
	
	

}
