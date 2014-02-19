package com.example.rally;



public class VelocitySubstituterTest extends SubstituterTest {

	@Override
	protected Substituter getSubstituter() {
		return new VelocitySubstituter();
	}

}
