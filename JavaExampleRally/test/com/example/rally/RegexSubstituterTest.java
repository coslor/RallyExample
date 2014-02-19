package com.example.rally;

public class RegexSubstituterTest extends SubstituterTest {

	@Override
	protected Substituter getSubstituter() {
		return new RegexSubstituter();
	}

}
