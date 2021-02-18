package hr.fer.oprpp1.hw04.db;
/**
 * Class represent one student
 * @author Iva
 *
 */
public class StudentRecord {
	private String jmbag;
	private String lastName;
	private String firstName;
	private int grade;
	
	public StudentRecord(String jmbag, String lastName, String firstName, int grade) {
		if(jmbag==null) throw new IllegalArgumentException();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.grade = grade;
	}
	

	/**
	 * Getter method for jmbag variable
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Setter method for variable jmbag
	 * @param jmbag value is set to class variable jmbag
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	/**
	 * Getter method for lastName variable
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter method for variable lastName
	 * @param lastName value is set to class variable lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter method for firstName variable
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter method for variable firstName
	 * @param firstName value is set to class variable firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter method for grade variable
	 * @return grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Setter method for variable grade
	 * @param grade value is set to class variable grade
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	

}
