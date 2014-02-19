package com.example.rally;

import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;

/**
 * Sometimes the best code, is code you didn't have to write! Apache Velocity is
 * a template library that provides a very powerful and performant templating
 * language. It does have its quirks, which to a certain extent show its age,
 * but it is pretty powerful.
 * 
 * Of course, lots of scripting language interpreters could be used in its
 * place; you can see some of them <A
 * href="http://en.wikipedia.org/wiki/Comparison_of_web_template_engines"
 * >here</A>
 * 
 * @author Chris Coslor
 * 
 */
public class VelocitySubstituter implements Substituter {

	private Logger log = Logger.getLogger(getClass().getName());

	public VelocitySubstituter() {
		// // Throw an exception if an unidentified variable is encountered
		Velocity.setProperty("runtime.references.strict", true);
		Velocity.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Substituter#substituteValues(java.util.Map)
	 */
	@Override
	public String substituteValues(Map<String, String> map, String template)
			throws SubstitutionException {
		
		log.debug("map=" + map + " template=" + template);
		
		StringWriter outWriter = new StringWriter();

		VelocityContext context = new VelocityContext(map);

		try {
			if (Velocity.evaluate(context, outWriter, getClass().getName(),
					template)) {
				String result = outWriter.toString();
				if (result.contains("$")) {
					result = substituteValues(map, result);
				}
				return result;
			}
			// Something horrible happened to Velocity
			throw new SubstitutionException("Velocity failed--check Velocity log");
		} catch (MethodInvocationException me) {
			throw new SubstitutionException("Undefined variable found");
		}
	}
}
