rm *.class
javac *.java
jar -cvmf manifest.txt ../bin/resumaker.jar  *.class 