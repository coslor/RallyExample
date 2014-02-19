package com.example.rally;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Do the variable substitutions using regular expression matching. Works pretty
 * well, but it's brittle--changing or extending the expression syntax means
 * rewriting the regex.
 * 
 * @author Chris Coslor
 * 
 */
public class RegexSubstituter implements Substituter {

	private Logger log = Logger.getLogger(getClass().getName());

	// Finds a pattern that starts with a "${" and ends with a "}" that is
	// followed by a character that isn't a "}"
	// Group 1 is the whole "${...}" pattern
	// Group 2 is the name inside the braces
	private Pattern varPattern = Pattern
			.compile("(\\$\\{(.*?)\\}(?=([^}]|$)))");

	/**
	 * It's possible to do nested expression evaluations here; see the unit
	 * tests for examples.
	 */
	@Override
	public String substituteValues(Map<String, String> map, String template)
			throws SubstitutionException {

		log.debug("template=" + template + " map=" + map);

		StringBuffer result = new StringBuffer();
		int lastEnd = 0;

		Matcher varMatcher = varPattern.matcher(template);

		while (varMatcher.find()) {
			String varName = varMatcher.group(2);
			int matchStart = varMatcher.start(1);
			int matchEnd = varMatcher.end(1);

			log.debug("Group 1=" + varMatcher.group(1) + " Group 2="
					+ varMatcher.group(2));
			log.debug("Matcher found match " + varName + " starting at "
					+ matchStart + " ending at " + matchEnd);

			result.append(template.substring(lastEnd, matchStart));

			if (varName.contains("$")) {
				varName = substituteValues(map, varName);
			}
			String varValue = map.get(varName);
			if (varValue == null) {
				throw new SubstitutionException(
						"Unable to find variable named " + varName);
			}

			result.append(varValue);

			lastEnd = matchEnd;
		}

		// No matches found
		if (lastEnd < template.length()) {
			result.append(template.substring(lastEnd));
		}

		log.debug("Result:" + result + "\n");

		return result.toString();
	}

}
