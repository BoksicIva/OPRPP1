package hr.fer.oprpp1.hw04.db;

import java.util.List;
/**
 * Class QueryFilter is implementation of IFilter interface 
 * @author Iva
 *
 */
public class QueryFilter implements IFilter{
	private List<ConditionalExpression> list;

	public QueryFilter(List<ConditionalExpression> list) {
		this.list=list;
	}
	/**
	 * Method checks if student is accepted by calling all satisfied methods of all ConditionalExpression in query
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(int i=0;i<list.size();i++) {
			ConditionalExpression expression=list.get(i);
			if(!expression.getComperisonOperator().satisfied(expression.getFieldGetter().get(record), expression.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

}
