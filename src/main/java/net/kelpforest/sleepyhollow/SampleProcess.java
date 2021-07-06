package net.kelpforest.sleepyhollow;

import javax.swing.Action;

public class SampleProcess {

	/*
	 * SampleProcess
	 * @author JunSun Whang
	 */
	
	private int sampleDuration=60;
	private boolean repeat=false;
	private int pauseInterval=900;
	private int maxRepeatcount=-1;
	
	public static void main(String[] args) {
		if (args.length==0) { //print general instructions.
			SampleProcess.printGeneralInstructions();
		} else { //inspect and validate options.
			//possible actions are:
			//1. execute sampling action 
			//2. execute sampling action and process listing.
			//3. execute process listing only, add reminder to specify process ID to undertake Action.
			//4. complete failure to parse arguments; print general instructions.
			
			//check if a request for listing the processes exists and generate that information if so.
			if (SampleProcess.hasListProcessOption(args)) {
				
			}
			
			
			SampleProcess sp = new SampleProcess();
			/*
			if (sp.executeArguments(args)==false) {
				
			} 
			*/
		}
	}
	
	private static void printGeneralInstructions() {
		print("NAME\n\tSampleProcess - sample a Java process on the local host launched by the current user.");
		print("\nSYNOPSIS\n\tSampleProcess [OPTION]... [JAVA PROCESS ID] [FILE PREFIX]");
		print("\nDESCRIPTION\n\tOutput sampler information to one or more files using the FILE PREFIX parameter." + "\n\tThe FILE PREFIX is composed of both the output path and file name prefix." +
		" e.g. /home/jsw/temp/samplerFile_" +
		"\n\tA suffix containing milliseconds past the epoch and an extension of \".nps\" will be added.  " + 
		"\n\tThe .nps file may be opened for viewing using VisualVM.");
		print("\n\t-l, --list-processes\n\t\tlist Java processes running within the same host and launched by the current user.");
		print("\n\t--sampleDuration=SECONDS\n\t\tsample execution for specified number of seconds per sampling phase.  Default is 60 seconds.");
		print("\n\t-r, --repeat \n\t\trepeat execution.  Default behavior is to NOT repeat the sampling phase.");
		print("\n\t--pauseInterval=SECONDS\n\t\twait the specified number of seconds in between sampling of data when repeat execution is in effect.  Default is 900 seconds.");
		print("\n\t--maxRepeatCount=COUNT\n\t\trepeat sampling phases until this count is complete.  Default behavior is to continue indefinitely.");
		print("\nAUTHOR\n\tAdapted from the VisualVM project for console usage by JunSun Whang.");
		print("\nCOPYRIGHT\n\tCopyright @ 2021 JunSun Whang.  Apache License v2.0 <https://www.apache.org/licenses/LICENSE-2.0.txt>.  \n\tThis is free software.  There is NO WARRANTY.");
	}
	
	protected static void print(String message) {
		System.out.println(message);
	}
	
	private static boolean hasListProcessOption(String[] args) {
		String argVal;
		for (int t=0; t<args.length; t++) {
			argVal = args[t].trim();
			if ( (argVal.equals("-l")) || (argVal.equals("--list-processes")) ) {
				return true;
			}
		}
		return false;
	}

}






