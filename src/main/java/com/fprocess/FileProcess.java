package com.fprocess;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Srinivas Reddy Karra
 * Main Class for file processing
 * 
 */
public class FileProcess 
{
	public static Logger logger = LoggerFactory.getLogger(FileProcess.class);
	
	private static int PROBLEM_1_ARGS_COUNT = 1;	
	private static int PROBLEM_2_ARGS_COUNT = 3;
	
	private static String cusipFileName;
	private static String inputFileName1;
	private static String inputFileName2;
	private static String outputFileName;

	
    public static void main( String[] args )
    {
    	logger.debug("main()");
    	if (!checkArgs(args)) {
    		printUsage();
    		return;
    	}
    	else {
    		long startTime = System.nanoTime();
    		long totalBefore = Runtime.getRuntime().totalMemory();
    		long freeBefore = Runtime.getRuntime().freeMemory();
    		long memBefore = totalBefore - freeBefore;
    		if (args.length==PROBLEM_1_ARGS_COUNT){
    			logger.debug("Problem 1 Execution");
	    		FileHandler fileHandler = new FileHandler(cusipFileName);
	    		fileHandler.processFile();
    		}
    		else if (args.length==PROBLEM_2_ARGS_COUNT){
    			logger.debug("Problem 2 Execution");
    			FileMerger fileMerger = new FileMerger(inputFileName1, inputFileName2, outputFileName);
    			fileMerger.merge();
    		}
    		long endTime = System.nanoTime();
    		long totalAfter = Runtime.getRuntime().totalMemory();
    		long freeAfter = Runtime.getRuntime().freeMemory();
    		long memAfter = totalAfter - freeAfter;
    		long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            logger.debug("Total elapsed time: " + elapsedTimeInMillis + " ms");
            logger.debug("Memory Before = " + (memBefore/1024) + "KB   Memory After = " + (memAfter/1024) +"KB");
    	}
    }
    
    private static boolean checkArgs(String [] args){
    	logger.debug("checkArgs()");
    	boolean retVal = false ;
    	if (args!=null && (args.length==PROBLEM_1_ARGS_COUNT || args.length==PROBLEM_2_ARGS_COUNT)) retVal = true;
    	if (args.length==PROBLEM_1_ARGS_COUNT) {
    		cusipFileName = args[0];
    		logger.debug("cusipFileName - " + cusipFileName);
    	}
    	else if (args.length==PROBLEM_2_ARGS_COUNT){
    		inputFileName1 = args[0];
    		inputFileName2 = args[1];
    		outputFileName = args[2];
    		logger.debug("inputFileName1 - " + inputFileName1);
    		logger.debug("inputFileName2 - " + inputFileName2);
    		logger.debug("outputFileName - " + outputFileName);
    	}
    	logger.debug("checkArgs() - returns " + retVal);
    	return retVal;
    }
    
    private static void printUsage(){
    	logger.debug("printUsage()");
    	System.out.println("Usage: java -jar fprocess-1.0.jar CusipFileName");
    	System.out.println("Usage: java -jar fprocess-1.0.jar InputFileName1 InputFileName2 OutputFileName");
    }
    
}
