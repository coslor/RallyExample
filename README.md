RallyExample
============
This repository contains code and tests to solve exercise #5 of the example exercises for Rally Software, 
which reads as follows:

------------
-Exercise 5-
------------
Write some code that can be used in a templating engine.
This should take a map of variables ("day" => "Thursday", "name"
=> "Billy") as well as a string
template ("${name} has an appointment on ${Thursday}") and
perform substitution as needed.
This needs to be somewhat robust, throwing some kind of error if
a template uses a variable that has not
been assigned, as well as provide a way to escape the strings
("hello ${${name}}" -> "hello ${Billy}")

The code takes two approaches to solving the problem:
1) Don't write a new template engine, use an existing one! The VelocitySubstituter uses
  the Apache Velocity template engine. It's a little logn in the tooth, but it's quite 
  powerful and is fairly well-documented and supported.
  
2) Use regular expressions. The RegexSubstituter searches for strings that look like
  "${variable-name}" and replaces the expression with the value of the variable in
  the output string. 
  
Both Substituters handle recursive lookups, and both are implementations of the "Substituter" interface.
This allows consumers of the templating library choose which implementation best suits their needs.

There are unit tests for both implementations, whcih cover the typical cases.

The project is hosted as an Eclipse project, but it should be trivial to import into just about
any Java environment, assuming that you make sure to include the "lib" folder into your CLASSPATH.

Please feel free to contact me with any questions at:
chris.coslor@gmail.com
