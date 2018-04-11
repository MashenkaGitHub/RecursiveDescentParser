package package1;
/**
 * File: ParserException.java 
 * Author: Maria Tkacheva 
 * Date: April 7, 2018
 * Assignment:Project 1 
 * UMUC CMSC 330 
 * ParserException class to trow parse exeption when unexpected token appear
 */
public class ParserException extends RuntimeException {
	public ParserException(String errorMessage) {
		super(errorMessage);
	}
}