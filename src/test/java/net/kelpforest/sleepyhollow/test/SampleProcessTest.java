package net.kelpforest.sleepyhollow.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.kelpforest.sleepyhollow.SampleProcess;

class SampleProcessTest {

	String C_TESTPREFIX = "\n### Test: ";
	String C_TESTSUFFIX = " ########################################################################################################################";
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		SampleProcess.resetResultingStates();
	}
	
	// no args
	@Test
	void testnoArgs() {
		System.out.println(C_TESTPREFIX + "no args" + C_TESTSUFFIX);
		String [] args = {};
		SampleProcess.main(args);
		assertTrue(SampleProcess.hasResultingState("printed instructions"));
	}
	
	// one args, error out
	@Test
	void testOneArg() {
		System.out.println(C_TESTPREFIX + "one arg" + C_TESTSUFFIX);
		String [] args = {"some arg"};
		SampleProcess.main(args);
		assertTrue(SampleProcess.hasResultingState("not enough arguments"));
	}
	
	// one args, list
	@Test
	void testOneListArg() {
		System.out.println(C_TESTPREFIX + "one LIST arg" + C_TESTSUFFIX);
		String [] args = {"-l"};
		SampleProcess.main(args);
		assertTrue(SampleProcess.hasResultingState("process listing"));
	}
	
	// -l
	@Test
	void testListProcess() {
		System.out.println(C_TESTPREFIX + "-l option" + C_TESTSUFFIX);
		String [] args = {"-l"};
		SampleProcess.main(args);
		assertTrue(SampleProcess.hasResultingState("process listing"));
	}

	// --list-processes
	@Test
	void testListProcess2() {
		System.out.println(C_TESTPREFIX + "--listProcesses option" + C_TESTSUFFIX);
		String [] args = {"--listProcesses"};
		SampleProcess.main(args);
		
		assertTrue(SampleProcess.hasResultingState("process listing"));
	}
	
	// minimum args: 2345 /usr/jun/outputs
	@Test
	void testMinimumValid() {
		System.out.println(C_TESTPREFIX + "--minimum valid args option" + C_TESTSUFFIX);
		String [] args = {"2345", "/usr/jun/outputs/SampleOutput_"};
		SampleProcess.main(args);
		assertTrue(SampleProcess.hasResultingState("validState=true"));
	}
	
	// minimum args, bad process ID: 2345Alpha /usr/jun/outputs
	@Test
	void testMinimumInValid() {
		System.out.println(C_TESTPREFIX + "--minimum Invalid args option" + C_TESTSUFFIX);
		String [] args = {"2345Alpha", "/usr/jun/outputs/SampleOutput_"};
		SampleProcess.main(args);
		assertTrue(SampleProcess.hasResultingState("validState=false"));
	}
	
	// minimum args with list processing, bad process ID: 2345Alpha /usr/jun/outputs
		@Test
	void testMinimumInValidList() {
		System.out.println(C_TESTPREFIX + "--minimum Invalid args + list" + C_TESTSUFFIX);
		String [] args = {"2345Alpha", "/usr/jun/outputs/SampleOutput_", "-l"};
		SampleProcess.main(args);
		assertTrue(SampleProcess.hasResultingState("validState=false"));
	}
	
	/*
	@Test
	void test() {
		fail("Not yet implemented");
	}
	*/
}
