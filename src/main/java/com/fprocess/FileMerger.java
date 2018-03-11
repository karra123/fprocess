package com.fprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Srinivas Reddy Karra
 * This class provides implementation for Problem 2 (assumes numerical data in input files, one per line and data is in ascending order.
 * Similarly, this can be extended to support other data types.
 * Given two sorted files, write a Java program to merge them to preserve sort order.
 * DO NOT assume either of these files can fit in memory!
 */
public class FileMerger {
	public static Logger logger = LoggerFactory.getLogger(FileMerger.class);
	private String inputFileName1;
	private String inputFileName2;
	private String outputFileName;
	
	public FileMerger(String inputFileName1, String inputFileName2, String outputFileName){
		this.inputFileName1 = inputFileName1;
		this.inputFileName2 = inputFileName2;
		this.outputFileName = outputFileName;
	}
	
	public void merge(){
		logger.debug("merge()");
		try ( BufferedReader reader1 = new BufferedReader(new FileReader(inputFileName1));
				BufferedReader reader2 = new BufferedReader(new FileReader(inputFileName2));			
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, false)); ){
			String line1 = reader1.readLine();
			String line2 = reader2.readLine();
			while(NumberUtils.isParsable(line1) && NumberUtils.isParsable(line2)){
				BigDecimal num1 = new BigDecimal(line1);
				BigDecimal num2 = new BigDecimal(line2);
				if (num1.compareTo(num2)<0){ //num1 is < num2 - write the lowest first
					writer.write(line1);
					line1 = reader1.readLine();
				}
				else {
					writer.write(line2);
					line2 = reader2.readLine();
				}
				writer.newLine();
			}
			while(NumberUtils.isParsable(line1)){
				writer.write(line1);
				writer.newLine();
				line1 = reader1.readLine();
			}
			while(NumberUtils.isParsable(line2)){
				writer.write(line2);
				writer.newLine();
				line2 = reader2.readLine();
			}
			writer.flush();
		}
		catch (Exception e){
			logger.error(e.getClass() + ": " +  e.getMessage(), e);
		}
	}
	
}
