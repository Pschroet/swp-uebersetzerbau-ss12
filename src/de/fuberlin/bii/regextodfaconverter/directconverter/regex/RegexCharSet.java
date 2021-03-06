/*
 * 
 * Copyright 2012 lexergen.
 * This file is part of lexergen.
 * 
 * lexergen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * lexergen is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with lexergen.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * lexergen:
 * A tool to chunk source code into tokens for further processing in a compiler chain.
 * 
 * Projectgroup: bi, bii
 * 
 * Authors: Johannes Dahlke
 * 
 * Module:  Softwareprojekt Übersetzerbau 2012 
 * 
 * Created: Apr. 2012 
 * Version: 1.0
 *
 */

package de.fuberlin.bii.regextodfaconverter.directconverter.regex;

import java.util.ArrayList;
import java.util.List;

import de.fuberlin.bii.regextodfaconverter.Regex;
import de.fuberlin.bii.regextodfaconverter.directconverter.lrparser.grammar.Terminal;
import de.fuberlin.bii.regextodfaconverter.directconverter.regex.operatortree.RegularExpressionElement;
import de.fuberlin.bii.utils.TriState;


/**
 * Zeichensatz für reguläre Ausdrücke.
 * 
 * @author Johannes Dahlke
 *
 */
public class RegexCharSet {

	/**
	 * Aternative
	 */
	public static final char REGEX_ALTERNATIVE = '|';
	
	/**
	 * Ziechen zum Maskieren von Sonderzeichen.
	 */
	public static final char REGEX_MASK = '\\';
	
	/**
	 * Kennzeichnet den Beginn einer Zeichenklasse.
	 */
	public static final char REGEX_CLASS_BEGIN = '[';
	
	/**
	 * Kennzeichnet das Ende einer Zeichenklasse.
	 */
  public static final char REGEX_CLASS_END = ']';
	
	/**
	 * Kennzeichnet den Beginn einer Wiederholung.
	 */
  public static final char REGEX_REPETITION_BEGIN = '{';

  /**
	 * Kennzeichnet das Ende einer Wiederholung.
	 */
	public static final char REGEX_REPETITION_END = '}';

	/**
	 * Kennzeichnet den Beginn einer Gruppe.
	 */
  public static final char REGEX_GROUP_BEGIN = '(';
	
	/**
	 * Kennzeichnet das Ende einer Gruppe.
	 */
  public static final char REGEX_GROUP_END = ')';
	
	/**
	 * Kleenesche Hülle.
	 */
  public static final char REGEX_KLEENE_CLOSURE = '*';
	
  /**
   * Positive Hülle.
   */
  public static final char REGEX_POSITIVE_CLOSURE = '+';
	
  /**
   * Optionales Vorkommen des davor stehenden Zeichens.
   */
  public static final char REGEX_OPTION = '?';
	
  /**
   * Ein beliebiges Zeichen.
   */
  public static final char REGEX_JOKER = '.';
	
  /**
   * Anfangsmarker.
   */
  public static final char REGEX_START_MARKER = '^';
	
  /**
   * Negation einer Zeichenklasse.
   */
  public static final char REGEX_CLASS_SIGNUM = '^';
	
  /**
   * Endmarker.
   */
  public static final char REGEX_END_MARKER = '$';
	
  /**
   * Zum Notieren eines Wertebereichs innerhalb einer Zeichenklasse. 
   *    
   */
  public static final char REGEX_RANGE = '-';
  
  /**
   * Treener für die Angabe von Wiederholungen.
   */
	public static final char REGEX_QUANTIFIER_SEPARATOR = ',';
	

	/**
	 * Das leere Wort.
	 */
	public static final char EMPTY_STRING = 0x00;
	
	/**
	 * Das Ende der Eingabe.
	 */
	public static final char TERMINATOR = 0x03; // ETX = End Of Text

	
	/**
	 * Definiert das erste Zeichen des Alphabets aus dem ASCII Zeichensatz.	
	 * 
	 *  Zeichen 0x00 - 0x03 sind geschützt.
	 */
	private static char FIRST_ASCII_CHAR = 0x04; 

	/**
	 * Definiert das letzte Zeichen des Alphabets aus dem ASCII Zeichensatz.
	 */
	private static char LAST_ASCII_CHAR = 0xFF;

	
	/**
	 * Liefert das Zeichen mit dem niedrigsten Ordinalwert 
	 * aus der Menge der für reguläre Ausdrücke zugelassenen Zeichen. 
	 * 
	 * @return das niederwertigste zulässige Zeichen.
	 */
	public static char getFirstAsciiChar() {
		return FIRST_ASCII_CHAR;
	}
	
	/**
	 * Liefert das Zeichen mit dem höchsten Ordinalwert 
	 * aus der Menge der für reguläre Ausdrücke zugelassenen Zeichen. 
	 * 
	 * @return das höchstwertigste zulässige Zeichen.
	 */
	public static char getLastAsciiChar() {
		return LAST_ASCII_CHAR;
	}
	
	/**
	 * Liefert den gesamten Wertebereich.
	 * @return
	 */
	public static List<Character> getCompleteDomain() {
		List<Character> result = new ArrayList<Character>();
		for ( int c = getFirstAsciiChar(); c <= getLastAsciiChar(); c++) {
			result.add( (char) c);
		}
		return result;
	}
	
	/**
	 * Liefert eine Liste der Metazeichen in Abhängigkeit des Kontext.
	 * @param context
	 * @return
	 */
	public static List<Character> getMetaCharsOfContext( RegexSection context) {
		List<Character> result = new ArrayList<Character>();
		for ( int c = getFirstAsciiChar(); c <= getLastAsciiChar(); c++) {
			if ( isSpecialChar( (char) c, context).isntFalse())
			  result.add( (char) c);
		}
		return result;
	}
	
	/**
	 * Liefert eine Liste der Ungeschützten Zeichen bezüglich des angegebenen Kontextes.
	 * @param context
	 * @return
	 */
	public static List<Character> getUnguardedCharsOfContext( RegexSection context) {
		List<Character> result = new ArrayList<Character>();
		for ( int c = getFirstAsciiChar(); c <= getLastAsciiChar(); c++) {
			if ( isSpecialChar( (char) c, context).isFalse())
			  result.add( (char) c);
		}
		return result;
	}
	
	
	/**
	 * Prüft, ob ein Zeichen ein Zeichen mit besonderer Bedeutung bezüglich regulärer Ausdrücke ist.
	 * @param theCharacter
	 * @return
	 */
	public static TriState isSpecialChar( char theCharacter, RegexSection context) {
		switch ( theCharacter) {
			case REGEX_MASK: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.TRUE);
			case REGEX_GROUP_BEGIN: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_GROUP_END: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_CLASS_BEGIN: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_CLASS_END: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.TRUE);
			case REGEX_ALTERNATIVE: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_REPETITION_BEGIN: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_REPETITION_END: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_KLEENE_CLOSURE: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_POSITIVE_CLOSURE: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_OPTION: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_JOKER: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			//case REGEX_CLASS_SIGNUM: 
			case REGEX_START_MARKER: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.AMBIGUOUS);
			case REGEX_END_MARKER: return defineAndGetStateByContext( context, TriState.TRUE, TriState.FALSE, TriState.FALSE);
			case REGEX_RANGE: return defineAndGetStateByContext( context, TriState.FALSE, TriState.FALSE, TriState.AMBIGUOUS);
			default:
				return defineAndGetStateByContext( context, TriState.FALSE, TriState.FALSE, TriState.FALSE);
		}
	}
		
	private static TriState defineAndGetStateByContext( RegexSection context, TriState stateOfMainContext, TriState stateOfQuantifierContext, TriState stateOfClassContext) {
		if ( context == RegexSection.MAIN)
			return stateOfMainContext;
		if ( context == RegexSection.QUANTIFIER)
			return stateOfQuantifierContext;
		// default case
		// if ( context == RegexSection.CHARACTER_CLASS)
			return stateOfClassContext;
	}
	
	/**
	 * Gibt an, ob es sich bei dem gegebenen Zeichen um ein Sonderzeichen bezüglich des {@link RegexSection#MAIN} Kontextes handelt.
	 * @param theCharacter
	 * @return
	 */
  public static TriState isSpecialChar( char theCharacter) {
	  return isSpecialChar( theCharacter, RegexSection.MAIN);	
	}
	
	
	/**
	 * Ermittelt, ob ein Zeichen zu dem grundlegenden Regex Zeichensatz gehört.
	 * @param theCharacter
	 * @return
	 */
	public static boolean isElementOfBasicCharset( char theCharacter) {
		switch ( theCharacter) {
			case REGEX_MASK:
			case REGEX_ALTERNATIVE:
			case REGEX_KLEENE_CLOSURE:
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Prüft, ob es sich um einen Regex-Operator der nicht erweiterten regulären Ausdrücke handelt.
	 * @param theChar
	 * @return
	 */
	public static boolean isBasicOperator( char theChar) {
		switch ( theChar) {
			case REGEX_ALTERNATIVE:
			case REGEX_KLEENE_CLOSURE:
				return true;
			default:
				return false;
		}
	}



	/**
	 * Prüft, ob es sich um ein leeres Wort handelt.
	 * @param theCharacter
	 * @return
	 */
	public static boolean isEmptyString( char theCharacter) {
		return EMPTY_STRING == theCharacter;
	}

}
