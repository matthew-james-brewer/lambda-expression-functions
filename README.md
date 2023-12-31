# Lambda Expression Functions
Create a list of functions with arguments and return types using a list of list of `Class`es, `String`s, a generic type parameter for the return type, and a lambda expression for the bodies.
## To install java with Linux/GNU
If you don't have java, do `sudo yum install java-1.8.0-openjdk` if you're on RHEL (or similar), or `sudo apt-get install default-jdk` for Debian (or similar)
## To install java with Windows
If you don't have java, go to [Openjdk downloads](https://jdk.java.net).
#### To compile as a `jar` file
First, go to a folder and type `git clone https://github.com/matthew-james-brewer/lambda-expression-functions.git && cd lambda-expression-functions`(Install Git for windows: `winget install git`).\
Either edit `Run.java` and follow the instructions or create a new file with code similar to `Run.java` imlemented.
Then, type `javac Run.java`.\
Finally, type `jar cfm lambda-expression-functions.jar manifest.mf *.class`.\
To run the jar file, type `java -jar lambda-expression-functions.jar`.\
_*I*_ f you want to view docs, type (in a new folder), `javadoc /path/to/lambda-expression-functions/Function.java /path/to/lambda-expression-functions/FunctionRunner.java` where /path/to is the path to it (if you want to view private members, use the `-private` flag).
