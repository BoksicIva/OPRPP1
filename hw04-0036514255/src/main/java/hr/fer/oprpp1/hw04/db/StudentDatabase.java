package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDatabase {
	List<String> file;
	List<StudentRecord> students;
	Map<String,StudentRecord> studentsMap;
	
	/**
	 * Constructor for StudentDatabase class
	 * constructor must get a list of String objects (the content of database.txt, 
	 * each string represents one row of the database file). It creates an internal list of student records and
	 *  creates Map  for fast retrieval of student records when jmbag is known . 
	 * @param file
	 */
	public StudentDatabase(List<String> file) {
		List<StudentRecord> students=new ArrayList<>();
		Map<String,StudentRecord> studentsMap=new HashMap<>();
		StudentRecord student = null;
		for(int i=0;i<file.size();i++) {
			String row=file.get(i);
			String[] studentData=row.split("\\s+");
			if(studentData.length==4) {
				int grade=Integer.parseInt(studentData[3]);
				if(grade > 5 || grade < 1) throw new IllegalArgumentException();
				student=new StudentRecord(studentData[0],studentData[1],studentData[2],grade);
				if(forJMBAG(studentData[0])!=null) {
					throw new IllegalStateException("Student with same JMBAG already exists");
				}
			}else if(studentData.length==5) {
				int grade=Integer.parseInt(studentData[4]);
				if(grade > 5 || grade < 1) throw new IllegalArgumentException();
				student=new StudentRecord(studentData[0],studentData[1]+" "+studentData[2],studentData[3],grade);
				if(forJMBAG(studentData[0])!=null) {
					throw new IllegalStateException("Student with same JMBAG already exists");
				}
			}
			students.add(student);
			studentsMap.put(studentData[0], student);
			this.students=students;
			this.studentsMap=studentsMap;
		}
	}
	/**
	 * Method checks if given jmbag is stored in Map of students
	 * @param jmbag value of jmbag that is checked
	 * @return student with given jmbag or null if one does not exist in Map
	 */
	public StudentRecord forJMBAG(String jmbag) {
		try {
		return studentsMap.get(jmbag);
		}catch(NullPointerException e) {
			return null;
		}
	}
	/**
	 * Method filters students by given filter
	 * @param filter Filter that is used
	 * @return List of students that are accepted
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> temporaryList=new ArrayList<StudentRecord>();
		for(var student:students) {
			if(filter.accepts(student)) 
				temporaryList.add(student);
		}
		return temporaryList;
	}
	
}
