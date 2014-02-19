package com.example.rally;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Test;

/** Run a Substituter through its paces. This class may need to be extended for new features of our expression language.
 *  
 * @author Chris Coslor
 *
 */
public abstract class SubstituterTest {

	private Logger log = Logger.getLogger(getClass().getName());

	public SubstituterTest() {
		super();
		// Set up log4j with sane defaults
		BasicConfigurator.configure();
	}

	/** Override this factory method for each type of Substituter **/
	protected abstract Substituter getSubstituter();

	@Test
	public void testCorrectSub() throws SubstitutionException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("day", "Thursday");
		map.put("name", "Billy");

		Substituter sub = getSubstituter();

		String nameResult = sub.substituteValues(map,
				"${name} has an appointment on ${day}");

		log.info("nameResult=" + nameResult);

		assertEquals("Billy has an appointment on Thursday", nameResult);
	}

	@Test(expected = SubstitutionException.class)
	public void testIncorrectSub() throws SubstitutionException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("day", "Thursday");

		Substituter sub = getSubstituter();

		String nameResult = sub.substituteValues(map,
				"${name} has an appointment on ${day}");
		log.info("nameResult=" + nameResult);
	}

	@Test
	public void testNoSub() throws SubstitutionException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "Billy");

		Substituter sub = getSubstituter();

		String nameResult = sub.substituteValues(map,
				"Billy has an appointment on Thursday");
		log.info("nameResult=" + nameResult + "\n");
		assertEquals("Billy has an appointment on Thursday", nameResult);
	}

	@Test
	public void testLastSub() throws SubstitutionException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("day", "Thursday");

		Substituter sub = getSubstituter();

		String nameResult = sub.substituteValues(map,
				"Billy has an appointment on ${day}");
		log.info("nameResult=" + nameResult + "\n");
		assertEquals("Billy has an appointment on Thursday", nameResult);
	}

	@Test
	public void testFirstSub() throws SubstitutionException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "Billy");

		Substituter sub = getSubstituter();

		String nameResult = sub.substituteValues(map,
				"${name} has an appointment on Thursday");
		log.info("nameResult=" + nameResult + "\n");
		assertEquals("Billy has an appointment on Thursday", nameResult);
	}
	
	/**
	 * It's not clear just what the expected behavior for nested expressions should be. The requirement lists this:
	 * 		("hello ${${name}}" -> "hello ${Billy}")
	 * ...but that's not a valid expression in our template language, unless "Billy" is the name of a variable.
	 * 
	 * So, I'm assuming that we actually want to evaluate all the way down.
	 * @throws Exception
	 */
	@Test
	public void testNestedExpressions() throws SubstitutionException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "Billy");
		map.put("Billy", "Jack");
		map.put("Jack", "Jill");

		Substituter sub = getSubstituter();

		String nameResult = sub.substituteValues(map,
				"${${${name}}} has an appointment on Thursday");
		log.info("nameResult=" + nameResult + "\n");
		assertEquals("Jill has an appointment on Thursday", nameResult);
	}

}