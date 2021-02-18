package hr.fer.oprpp1.hw04.db;
/**
 * Class FieldValueGetter contains static final variables of IFieldValueGetter interface
 * @author Iva
 *
 */
public class FieldValueGetter {
	/**
	 * variables are representing implementation of interface 
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) ->{
		return record.getFirstName();
	};
	public static final IFieldValueGetter LAST_NAME = (record) ->{
		return record.getLastName();
	};
	public static final IFieldValueGetter JMBAG = (record) ->{
		return record.getJmbag();
	};
}
