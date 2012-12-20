package testFramework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.*;

@RunWith(Suite.class)
@SuiteClasses({ 
	ParserTest.class,
	TypeCheckingTest.class, 
	FeaturesTest.class,
	CleanJavaCheckerTest.class
	})
public class AllTests {

}
