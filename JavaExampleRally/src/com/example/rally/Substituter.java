package com.example.rally;
import java.util.Map;

/**
 * A Substituter replaces variable expressions in a String, with the values
 * 		associated with the variable names.
 *  
 * @author Chris Coslor
 *
 */
public interface Substituter {

	/**
	 * 
	 * @param map		Names and values of the variables to be used
	 * 					in producing the final text
	 * @param template	Some text that may contain variable expressions 
	 * 					of the form ${<name>}. Expressions can be escaped
	 * 					by preceding them with a backslash.
	 * @return			The "expanded" text that results from replacing the
	 * 					variable expressions with the corresponding values
	 * 					from the map.
	 * @throws SubstitutionException
	 */
	public String substituteValues(Map<String, String> map, String template)
			throws SubstitutionException;

}