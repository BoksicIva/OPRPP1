package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


public class StudentDBTest {

	    
		@Test
		public void ForJMBAGTest() {
			List<String> records = List.of("0123  Marko  Min  4", "0234  Darko  abc  1", "0444  Mimi  trokut  5");
			StudentDatabase db = new StudentDatabase(records);
			assertEquals("Marko", db.forJMBAG("0123").getLastName());
			assertEquals(5, db.forJMBAG("0444").getGrade());
		}
		
		@Test
		public void ForJMBAGNonExistingTest() {
			List<String> records = List.of("0123  Marko  Min  4", "0234  Darko  abc  1", "0444  Mimi  trokut  5");
			StudentDatabase db = new StudentDatabase(records);
			assertNull(db.forJMBAG("123"));
		}
		
		@Test
		public void DuplicateJMBAGinsertionTest() {
			List<String> records = List.of("0123  Marko  Min  4", "0234  Darko  abc  1", "0123  Mimi  trokut  5");
			assertThrows(IllegalStateException.class, () -> {
				StudentDatabase db = new StudentDatabase(records);
			});
		}
		
		@Test
		public void GradeOutOfBoundInsertionTest() {
			List<String> records = List.of("0123  Marko  Min    4", "0234     Darko   abc     1",
					"0123  Mimi  trokut      8");
			assertThrows(IllegalArgumentException.class, () -> {
				StudentDatabase db = new StudentDatabase(records);
			});
		}
		
		@Test
		public void UnparseableGradeInsertionTest() {
			List<String> records = List.of("0123  Marko  Min  4g", "0234   Darko  abc  1", "0123  Mimi  trokut    3");
			assertThrows(NumberFormatException.class, () -> {
				StudentDatabase db = new StudentDatabase(records);
			});
		}
		
		@Test
		public void ConnectedLastNameTest() {
			List<String> records = List.of("01234  GaÄ�inoviÄ‡-Prskalo  Marija  3");
			StudentDatabase db = new StudentDatabase(records);
			assertEquals("GaÄ�inoviÄ‡-Prskalo", db.forJMBAG("01234").getLastName());
		}
		
		@Test
		public void DoubleLastNameTest() {
			List<String> records = List.of("01234  GaÄ�inoviÄ‡ Prskalo  Marija  3");
			StudentDatabase db = new StudentDatabase(records);
			assertEquals("GaÄ�inoviÄ‡ Prskalo", db.forJMBAG("01234").getLastName());
		}
		
		@Test
		public void FillterAllWithTrueTest() {
			List<String> records = List.of("0123  Marko  Min  4", "0234  Darko  abc  1", "0444  Mimi  trokut  5");
			StudentDatabase db = new StudentDatabase(records);
			List<StudentRecord> allRecords = db.filter(new AlwaysTrue());
			assertEquals(records.size(), allRecords.size());
		}
		
		@Test
		public void FillterAllWithFalseTest() {
			List<String> records = List.of("0123  Marko  Min  4", "0234  Darko  abc  1", "0444  Mimi  trokut  5");
			StudentDatabase db = new StudentDatabase(records);
			List<StudentRecord> noRecords = db.filter(new AlwaysFalse());
			assertEquals(0, noRecords.size());
		}

		private class AlwaysTrue implements IFilter {
			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
		}

		private class AlwaysFalse implements IFilter {
			@Override
			public boolean accepts(StudentRecord record) {
				return false;
			}
		}
	

}
