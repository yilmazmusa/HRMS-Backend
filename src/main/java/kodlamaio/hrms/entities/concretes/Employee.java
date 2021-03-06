package kodlamaio.hrms.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name="employees")
public class Employee extends User{

	@Column(name = "first_name")
	@NotNull(message="firstname cannot be null")
	@NotBlank
	@Size(min=2,max=25,message="FirstName must be between 2 and 25 characters")
	private String firstName;
	
	@Column(name = "last_name")
	@NotNull(message="LastName cannot be null")
	@NotBlank
	@Size(min=2,max=25,message="FirstName must be between 2 and 25 characters")
	private String lastName;
}