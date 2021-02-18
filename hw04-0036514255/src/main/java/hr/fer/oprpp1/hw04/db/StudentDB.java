package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class StudentDB {
	
	public static void main(String[] args) throws IOException {
		
		List<String> lines=Files.readAllLines(Paths.get("C:/FER/OPRPP1/workspace/hw04-0036514255/database.txt"),StandardCharsets.UTF_8);
		
		StudentDatabase database=new StudentDatabase(lines);
		 Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.print("> ");
			String line=scan.nextLine();
			if(line.startsWith("query")) {
				try {
					String toDo=line.substring(line.indexOf("query")+6);
					QueryParser parser=new QueryParser(toDo);
					List<StudentRecord> students=new ArrayList<>();
					if(parser.isDirectQuery()) {
						StudentRecord student=database.forJMBAG(parser.getQueriedJMBAG());
						students.add(student);
					
						System.out.println("Using index for record retrieval.");
						
					}else {
						List<StudentRecord> databaseFiltered=database.filter(new QueryFilter(parser.getQuery()));
						for (StudentRecord student: databaseFiltered) {
							students.add(student);
						}
					}
					table(students);
					System.out.println("Records selected: "+students.size()+"\n");
					
				}catch(Exception e) {
					System.out.println(e.getMessage()+"\n");
					continue;
				}
			}else if(line.toLowerCase().equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}else if(!line.startsWith("query")) {
				System.out.println("Command must start with \"query\"");
			}
		}
		
		scan.close();
	}
	/**
	 * Method prints table of students
	 * @param students list of accepted students
	 */
	public static void table(List<StudentRecord> students) {
		if(students.size()==0) return;
		int maxJmbag=students.stream().mapToInt(s -> s.getJmbag().length()).max().getAsInt();
		int maxName=students.stream().mapToInt(s -> s.getFirstName().length()).max().getAsInt();
		int maxLastName=students.stream().mapToInt(s -> s.getLastName().length()).max().getAsInt();
		int sum=maxJmbag+maxName+maxLastName;
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<sum+14;i++) {
			if(i==0 || i==2+maxJmbag+1 || i==2+maxJmbag+3+maxLastName+1 || i==2+maxJmbag+3+maxLastName+3+maxName+1  || i==sum+13) {
				sb.append("+");
			}else {
				sb.append("=");
			}
		}
		System.out.println(sb.toString());
		for(int i=0;i<students.size();i++) {
			StudentRecord student=students.get(i);
			StringBuilder studentSB=new StringBuilder();
			studentSB.append("| ").append(student.getJmbag());
			addSpaces(studentSB,maxJmbag-student.getJmbag().length());
			studentSB.append(" | ").append(student.getLastName());
			addSpaces(studentSB,maxLastName-student.getLastName().length());
			studentSB.append(" | ").append(student.getFirstName());
			addSpaces(studentSB,maxLastName-student.getFirstName().length());
			studentSB.append(" | ").append(student.getGrade()).append(" |");
			System.out.println(studentSB.toString());
		}
		
		System.out.println(sb.toString());
	}
	
	public static void addSpaces(StringBuilder sb,int length) {
		for(int i=0;i<length;i++) {
			sb.append(" ");
		}
	}

}
