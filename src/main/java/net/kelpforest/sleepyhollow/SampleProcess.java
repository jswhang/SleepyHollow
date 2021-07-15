package net.kelpforest.sleepyhollow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

public class SampleProcess {

	/*
	 * SampleProcess
	 * @author JunSun Whang
	 */
	
	final static Logger log = Logger.getLogger(SampleProcess.class);
	
	//parameter contstants
	private static String C_LIST_SHORT = "-l";
	private static String C_LIST = "--listProcesses";
	private static String C_SAMPLE_DURATION = "--sampleDuration";
	private static String C_REPEAT_SHORT = "-r";
	private static String C_REPEAT = "--repeat";
	private static String C_PAUSE_INTERVAL = "--pauseInterval";
	private static String C_MAX_REPEAT_COUNT = "--maxRepeatCount";

	private static String [] validArgs  = {C_LIST_SHORT, C_LIST, C_SAMPLE_DURATION, C_REPEAT_SHORT, C_REPEAT, 
			C_PAUSE_INTERVAL, C_MAX_REPEAT_COUNT};	
	private static String [] valueArgs = {C_SAMPLE_DURATION, C_PAUSE_INTERVAL, C_MAX_REPEAT_COUNT};
	
	//runtime parameters
	private static int sampleDuration=60;
	private static boolean repeat=false;
	private static int pauseInterval=900;
	private static int maxRepeatcount=-1;
		
	private static SamplerCore samplerCore = null;
	private static Set<String> resultingStates=new HashSet<String>();
	
	public static void main(String[] args) {
		if (args.length==0) { //print general instructions.
			SampleProcess.printGeneralInstructions();
		} else { //inspect and validate options.
			//possible actions are:
			//1. execute sampling action 
			//2. execute process listing and sampling action.
			//3. execute process listing only, add reminder to specify process ID to undertake Action.
			//4. complete failure to parse arguments; print general instructions.
			
			//check if a request for listing the processes exists and generate that information if so.
			boolean hasListProcessingOption = SampleProcess.hasListProcessOption(args);
			if (hasListProcessingOption) {
				samplerCore = new SamplerCore();
				print(samplerCore.retrieveProcessInfoString());
				addResultingState("process listing");
			}
			
			//validate all arguments are valid and execute processing if so.
			if (SampleProcess.checkValidArgs(args, hasListProcessingOption)) {
				print("Sampling...");
			} 
		}
	}
	
	public static void addResultingState(String state) {
		resultingStates.add(state);
	}
	
	public static boolean hasResultingState(String state) {
		return resultingStates.contains(state);
	}
	
	public static void resetResultingStates() {
		resultingStates.clear();
	}
	
	private static void printGeneralInstructions() {
		print("\nNAME\n\tSampleProcess - sample a Java process on the local host launched by the current user.");
		print("\nSYNOPSIS\n\tSampleProcess [OPTION]... [JAVA PROCESS ID] [FILE PREFIX]");
		print("\nDESCRIPTION\n\tOutput sampler information to one or more files using the FILE PREFIX parameter." + "\n\tThe FILE PREFIX is composed of both the output path and file name prefix." +
		" e.g. /home/jsw/temp/samplerFile_" +
		"\n\tA suffix containing milliseconds past the epoch and an extension of \".nps\" will be added.  " + 
		"\n\tThe .nps file may be opened for viewing using VisualVM.");
		print("\n\t-l, --listProcesses\n\t\tlist Java processes running within the same host and launched by the current user.");
		print("\n\t--sampleDuration=SECONDS\n\t\tsample execution for specified number of seconds per sampling phase.  Default is 60 seconds.");
		print("\n\t-r, --repeat \n\t\trepeat execution.  Default behavior is to NOT repeat the sampling phase.");
		print("\n\t--pauseInterval=SECONDS\n\t\twait the specified number of seconds in between sampling of data when repeat execution is in effect.  Default is 900 seconds.");
		print("\n\t--maxRepeatCount=COUNT\n\t\trepeat sampling phases until this count is complete.  Default behavior is to continue indefinitely.");
		print("\nAUTHOR\n\tAdapted from the VisualVM project for console usage by JunSun Whang.");
		print("\nCOPYRIGHT\n\tCopyright @ 2021 JunSun Whang.  Apache License v2.0 <https://www.apache.org/licenses/LICENSE-2.0.txt>.  \n\tThis is free software.  There is NO WARRANTY.");
		
		addResultingState("printed instructions");
	}
	
	private static void printAccessHelp() {
		print("Execute this program without arguments to see extended help.");
	}
	
	protected static void print(String message) {
		System.out.println(message);
	}
	
	protected static void error(String message) {
		System.out.println("*** ERROR: " + message);
	}
	
	private static boolean checkValidArgs(String [] args, boolean hasListProcessingOption) {
		boolean validArgs = true;
		if (args.length >=2) {
			
			//check next to last arg = integer pid
			String processIdArgStr = args[args.length-2];
			int processId = 0;
			try {
				processId = Integer.parseInt(processIdArgStr);
				print ("Process Id: " + processId);
				
			} catch (NumberFormatException nfe) {
				validArgs= false;
				addResultingState("validState=" + String.valueOf(validArgs));
				
				String message = "Process ID cannot be parsed as an integer.";
				//log.debug(message, nfe);
				error(message);
				printAccessHelp();
				
				return false;
			} 
			
			//check last arg = file prefix
			String filePrefixArg = args[args.length-1];
			print ("File Prefix: " + filePrefixArg);
			
			if (args.length>2) {
				//check remaining options are within allowed option types
				boolean allOptionsValid=true;
				for (int z=0;z<args.length-2;z++) {
					String curParam = args[z];
					if (validArgStart(curParam)) {
						
						String checkVal = checkValueParam(curParam);
						if (checkVal!=null) { //for option types with values, validate format and value type
							if (curParam.contains("=")) { //these value params should have an equals sign.
							
							} else { //if this is a value type param, but doesn't contain an equals sign, thrown an error.
								//TODO: throw an error here.
								allOptionsValid=false;
							}
						}
					} else {
						allOptionsValid=false;
					}
					
				}
				//TODO: if all options are not valid, throw an error.
			   
			}
			
			addResultingState("validState=" + String.valueOf(validArgs));
			return validArgs;
		} else {
			validArgs=false;
			addResultingState("validState=" + String.valueOf(validArgs));
			
			if (!(hasListProcessingOption)) {
				error("Not enough arguments to collect a performance snapshot.");
				printAccessHelp();
			}
			addResultingState("not enough arguments");
			
			return false;
		}
	}
	
	private static boolean validArgStart(String prefix) {
		boolean foundMatch = false;
		for (int t=0; t<validArgs.length; t++) {
			if (validArgs[t].startsWith(prefix)) {
				foundMatch=true;
			}
		}
		return foundMatch;
	}
	
	private static String checkValueParam(String paramVal) {
		String valueParam = null;
		for (int t=0; t<valueArgs.length; t++) {
			if (paramVal.startsWith(valueArgs[t])) {
				return valueArgs[t];
			}
		}
		return valueParam;
	}
	
	private static boolean hasListProcessOption(String[] args) {
		String argVal;
		for (int t=0; t<args.length; t++) {
			argVal = args[t].trim();
			if ( (argVal.equals(C_LIST_SHORT)) || (argVal.equals(C_LIST)) ) {
				return true;
			}
		}
		return false;
	}

}






