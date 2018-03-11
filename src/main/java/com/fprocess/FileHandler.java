package com.fprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Set;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Srinivas Reddy Karra
 * This class provides implementation for Problem 1.
 * Assuming, same CUSIP can repeat mutiple times in the file, this process starts printing after going through the input file completely, due to which it needs to store.
 * If this is not the case, then we don't need to store, and can start printing right after first CUSIP data is read completely.
 * 
 * Think of it as a file of price ticks for a set of bonds identified by their CUSIPs.
You can assume a CUSIP is just an 8-character alphanumeric string.
Each CUSIP may have any number of prices (e.g., 95.752, 101.255) following it in
sequence, one per line.
The prices can be considered to be ordered by time in ascending order, earliest to latest.

Write me a Java program that will print the closing (or latest) price for each CUSIP in the file.
DO NOT assume the entire file can fit in memory!

 */
public class FileHandler {

	private final int CUSIP_SIZE = 8;
	public static Logger logger = LoggerFactory.getLogger(FileHandler.class);
	
	private String fileName;
	private Hashtable<String, BigDecimal> hashTable;
	
	public FileHandler (String fileName){
		this.fileName = fileName;
		hashTable = new Hashtable<String, BigDecimal>();
	}
	
	public void processFile(){
		logger.debug("processFile()");
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {	
			String currentCusip = "";
			String line;
			while((line = reader.readLine()) != null){
				BigDecimal price = BigDecimal.ZERO;
				if (StringUtils.isAlphanumeric(line.trim()) && (line.trim().length()==CUSIP_SIZE)){
					//CUSIP
					currentCusip = line.trim();
					logger.debug("currentCusip = " + currentCusip);
				}
				else if (NumberUtils.isParsable(line)){
					//Price
					price = new BigDecimal(line.trim());
					hashTable.put(currentCusip, price);
					logger.debug("currentCusip = " + currentCusip + " price = " + price);
				}
				else {
					currentCusip = "INVALID";
					logger.debug("currentCusip = " + currentCusip);
				}
			}
			printData();
		}
		catch (Exception e){
			logger.error(e.getClass() + ": " +  e.getMessage(), e);
		}
	}
	
	private void printData(){
		logger.debug("printData");
        Set<String> keys = hashTable.keySet();
        for(String key: keys){
            System.out.println("CUSIP - "+key+" : "+hashTable.get(key));
        }
	}
}
