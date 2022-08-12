package com.zee.zee5app.dto;

import java.time.LocalDate;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "userId")
@ToString
public class User{
	
	@Override
	public int hashCode() {
		return Objects.hash(active, dob, doj, firstName, lastName, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return active == other.active && Objects.equals(dob, other.dob) && Objects.equals(doj, other.doj)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(userId, other.userId);
	}
//	@Setter(value = AccessLevel.NONE)
	private String userId;
	private String firstName;
	private String lastName;
	private LocalDate doj;
	private LocalDate dob;	
	private boolean active;
	
}
