/*
 *  Copyright 2017 TR Software Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package solutions.trsoftware.commons.client.util;

import solutions.trsoftware.commons.client.bridge.util.RandomGen;
import solutions.trsoftware.commons.client.util.iterators.CharSequenceIterator;
import solutions.trsoftware.commons.client.util.stats.MaxComparable;

import java.util.*;

/**
 * Date: Dec 18, 2007 Time: 9:56:00 PM
 *
 * @author Alex
 */
public class StringUtils {

  /** The lowest printable ASCII char (code 32) */
  public static final char MIN_PRINTABLE_ASCII_CHAR = ' ';
  /** The highest printable ASCII char (code 126) */
  public static final char MAX_PRINTABLE_ASCII_CHAR = '~';

  public static String capitalize(String str) {
    if (str == null || str.isEmpty() || isCapitalized(str))
      return str;
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  /** @return true iff {@code str} starts with an uppercase letter */
  public static boolean isCapitalized(String str) {
    return str != null && !str.isEmpty() && Character.isUpperCase(str.charAt(0));
  }

  /** Capitalizes {@code str} iff {@code reference} starts with an uppercase letter */
  public static String maybeCapitalize(String str, String reference) {
    if (isCapitalized(reference))
      return capitalize(str);
    return str;
  }

  /**
   * @return A pretty string matching a name of a (Java code style) constant.  Example: {@code "FOO_BAR" >>> "Foo bar"}
   */
  public static String constantNameToTitleCase(String name) {
    return capitalize(name.replaceAll("_", " ").trim().toLowerCase());
  }

  /**
   * Checks whether given string is null, empty, or consists entirely of
   * whitespace
   */
  public static boolean isBlank(String str) {
    return str == null || str.trim().length() == 0;
  }

  /**
   * Returns true if the given string is not null, empty, or consists entirely of
   * whitespace.
   */
  public static boolean notBlank(String str) {
    return !isBlank(str);
  }

  /**
   * @return the given string if it's not null, otherwise an empty string.
   */
  public static String nonNull(String str) {
    return LogicUtils.firstNonNull(str, "");
  }

  /**
   * @return the given string, trimmed by removing surrounding whitespace, if it's not null, otherwise an empty string.
   */
  public static String trim(String str) {
    return nonNull(str).trim();
  }

//  /**
//   * Converts any special xml char escape sequences in the given string to the
//   * original characters:
//   * & - &amp;
//   * < - &lt;
//   * > - &gt;
//   * " - &quot;
//   * ' - &apos;
//   */
//  public static String unescapeXML() {
//  }

  public static boolean validateUrl(String url) {
    return notBlank(url) && url.trim().startsWith("http");
  }

  /**
   * Abbreviates the input string if it's longer than {@code maxLen} by replacing the last
   * {@code maxLen-suffix.length()} characters with {@code suffix}.
   *
   * @param str the string to abbreviate, may be null
   * @param maxLen maximum length of result, should be a positive integer in order for this method to makes sense.
   * However, if it's not positive, will return an empty string rather than throwing an exception.
   * @param suffix overflow characters will be replaced with this, with a {@code null} value for this arg being equivalent
   * to passing an empty string.
   * @return the abbreviated string if it needs to be abbreviated (in which case the result will contain one or more
   * characters from the original plus as many chars from the suffix as we can fit in while satisfying the
   * {@code maxLen} constraint), the original string if it doesn't need to be abbreviated, {@code null} if the original
   * was {@code null}, or an empty string if {@code maxLen} is not a positive integer.
   */
  public static String abbreviate(String str, int maxLen, String suffix) {
    // TODO: rethink the str.trim() performed by isBlank: is that we we really want? And if so, should we trim the substring as well before adding the suffix?
    // 1) validate the args
    if (isBlank(str) || str.length() <= maxLen)
      return str;
    if (maxLen <= 0)
      return "";
    if (suffix == null)
      suffix = "";
    // at this point we have to abbreviate; to figure out where to cut the string we can solve the equation: newStrLen + suffixLen = maxLen => newStrLen = maxLen - suffixLen
    int newStrLen = Math.max(1, maxLen - suffix.length());  // we want to leave at least 1 char from the original str
    // however, since we're forcing newStrLen to be at least 1, we might have to trim the suffix according to
    // this equation: newStrLen + newSuffixLen = maxLen => newSuffixLen = maxLen - newStrLen
    return str.substring(0, newStrLen) + suffix.substring(0, maxLen - newStrLen);
  }

  /**
   * Same as {@link #abbreviate(String, int, String) abbreviate(str, maxLen, "...")}.
   */
  public static String abbreviate(String str, int maxLen) {
    return abbreviate(str, maxLen, "...");
  }

  /**
   * Truncates the given string to the desired length.
   * @param str The input string, can be null
   * @param length the maximum length of the string to be returned
   * @return a string containing up to length chars from the input string
   */
  public static String truncate(String str, int length) {
    if (str == null)
      return null;
    if (str.length() <= length)
      return str;
    else
      return str.substring(0, length);
  }

  /**
   * @return The substring of s ending before the first occurrence of q; if
   * s doesn't contain q, returns s.
   */
  public static String substringBefore(String s, String q) {
    int i = s.indexOf(q);
    if (i > -1)
      return s.substring(0, i);
    else
      return s;
  }

  /**
   * @return The substring of s starting after the first occurrence of q; if
   * s doesn't contain q, returns s.
   */
  public static String substringAfter(String s, String q) {
    int i = s.indexOf(q);
    if (i > -1)
      return s.substring(i + q.length());
    else
      return s;
  }

  /**
   * @return The substring of s in-between the first occurrences of a and b; if
   * s doesn't contain a, returns substringBefore(s, b); if s doesn't contain b
   * after a, returns substringAfter(s,a);
   */
  public static String substringBetween(String s, String a, String b) {
    int aStart = s.indexOf(a);
    if (aStart < 0)
      return substringBefore(s, b);
    int aEnd = aStart + a.length();
    int bStart = s.indexOf(b, aEnd);
    if (bStart < 0)
      return substringAfter(s, a);
    return s.substring(aEnd, bStart);
  }

//  /**
//   * Provides a very limited form of string templating. The symbols $1...$N
//   * are replaced with the given args.
//   * Warning: Not the fastest possible implementation.
//   */
//  public static String template(String format, Object... args) {
//    String result = format;
//    for (int i = 0; i < args.length; i++) {
//      result = result.replaceAll("\\$"+(i+1), String.valueOf(args[i]));
//    }
//    return result;
//  }

  /**
   * Provides a very limited form of string templating. The symbols $1...$9
   * are replaced with the given args.  Supports at most 9 arguments.
   */
  public static String template(String format, Object... args) {
    if (args.length > 9)
      throw new IllegalArgumentException("template called with more than 9 args");
    // Not using regex here for Java/Javascript compatibility (and also speed)
    StringBuffer result = new StringBuffer(512);
    int nextCopyRegionStart = 0;
    int lastMatch = format.indexOf("$", 0);
    while (lastMatch >= 0 && lastMatch < format.length()-1) {
      char indexChar = format.charAt(lastMatch + 1);
      if (indexChar >= '1' && indexChar <= '9') {
        int index = indexChar - '1';
        result.append(format.substring(nextCopyRegionStart, lastMatch));
        result.append(args[index]);
        nextCopyRegionStart = lastMatch+2;
        lastMatch = format.indexOf("$", lastMatch+2);
      }
      else
        lastMatch = format.indexOf("$", lastMatch+1); // handle strings like "$$1"
    }
    result.append(format.substring(nextCopyRegionStart));  // copy the end of the format string
    return result.toString();
  }

  /** Returns strings like 1st, 2nd, 3rd, 4th, etc. for the English language */
  public static String ordinal(int i) {
    String str = String.valueOf(i);
    String suffix;
    if (str.endsWith("1") && !str.endsWith("11"))
      suffix = "st";
    else if (str.endsWith("2") && !str.endsWith("12"))
      suffix = "nd";
    else if (str.endsWith("3") && !str.endsWith("13"))
      suffix = "rd";
    else
      suffix = "th";
    return str + suffix;
  }

  /**
   * @return A string displaying the given quantity, with the unit value
   * pluralized if needed.  Example: pluralize(2, "second") returns
   * "2 seconds".
   */
  public static String quantity(int value, String unit) {
    return String.valueOf(value) + ' ' + pluralize(unit, value);
  }

  /** Removes the given suffix from the string if it's present */
  public static String stripTrailing(String str, String suffix) {
    return str.endsWith(suffix) ? str.substring(0, str.length() - suffix.length()) : str;
  }

  /** Returns a string that contains #repetitions instances of fillChar */
  public static String repeat(char fillChar, int repetitions) {
    char[] chars = new char[repetitions];
    Arrays.fill(chars, fillChar);
    return new String(chars);
  }

  /** Returns a string that contains #repetitions of fillStr */
  public static String repeat(String fillStr, int repetitions) {
    StringBuilder buf = new StringBuilder(fillStr.length()*repetitions);
    for (int i = 0; i < repetitions; i++) {
      buf.append(fillStr);
    }
    return buf.toString();
  }

  /** Returns a random string over the alphabet [A-Za-z] */
  public static String randString(int length) {
    // can't use org.apache.commons.lang.RandomStringUtils here because this class
    // will be used client-side in GWT unit tests
    StringBuilder buf = new StringBuilder(length);
    RandomGen rnd = RandomGen.getInstance();
    for (int i = 0; i < length; i++) {
      boolean uppercase = rnd.nextBoolean();
      buf.append(uppercase ? (char)rnd.nextIntInRange((int)'A', (int)'Z'+1)
          : (char)rnd.nextIntInRange((int)'a', (int)'z'+1));
    }
    return buf.toString();
  }

  /**
   * Returns a camel hump string for the given string.
   * @return Given MY_STRING, returns myString 
   */
  public static String underscoresToCamelHumps(String str) {
    if (isBlank(str))
      return str;
    str = str.toLowerCase();
    int nextUnderscore = str.indexOf("_");
    if (nextUnderscore < 0)
      return str;
    StringBuilder out = new StringBuilder(str.length());
    int nextSubstringStart = 0;
    while (nextUnderscore >= 0) {
      out.append(str.substring(nextSubstringStart, nextUnderscore));
      if (nextUnderscore == str.length()-1) {
        // trailing underscore, ignore it and finish
        nextSubstringStart = nextUnderscore+1;
        break;
      }
      char nextCharAfterUnderscore = str.charAt(nextUnderscore+1);
      if (Character.isLetter(nextCharAfterUnderscore)) {
        out.append(Character.toUpperCase(nextCharAfterUnderscore));
        nextSubstringStart = nextUnderscore+2;
      } else {
        nextSubstringStart = nextUnderscore+1;
      }
      nextUnderscore = str.indexOf("_", nextUnderscore+1);
    }
    out.append(str.substring(nextSubstringStart));
    return out.toString();
  }

  /** @return the number of the times the given char appears in the given string */
  public static int count(String s, char c) {
    if (isBlank(s))
      return 0;
    int n = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == c)
        n++;
    }
    return n;
  }

  /** A var-arg version of the toString(array, delimiter) method */
  public static <T> String join(String delimiter, T... array) {
    return join(delimiter, Arrays.asList(array));
  }

  public static <T> String join(String delimiter, Iterator<T> iter) {
    StringBuilder str = new StringBuilder(128);
    while (iter.hasNext()) {
      T item = iter.next();
      str.append(item);
      if (iter.hasNext())
        str.append(delimiter);
    }
    return str.toString();
  }

  public static <T> String join(String delimiter, Iterable<T> iterable) {
    return join(delimiter, iterable.iterator());
  }

  /**
   * @return the integer represented by the last sequence of consecutive digits
   * contained by the string, or null if the string does not contain
   * any digits.
   *
   */
  public static Integer lastIntegerInString(String str) {
    int startIndex = -1, endIndex = -1;

    for (int i = str.length()-1; i >= 0; i--) {
      char c = str.charAt(i);
      if (c >= '0' && c <= '9') {
        if (endIndex < 0)
          endIndex = i;
        startIndex = i;
      }
      else if (endIndex >= 0) {
        break;  // stop looking - we found a non-digit character while scanning a run of digits
      }
    }
    if (endIndex >= 0)
      return Integer.parseInt(str.substring(startIndex, endIndex+1)); // let the Integer class do the parsing (to properly handle signs, etc.)
    return null;
  }

  /** Maps some irregular words to their plural forms */
  private static final Map<String, String> pluralDict = Collections.unmodifiableMap(MapUtils.stringMap(
      "is", "are",
      "its", "their"
  ));

  /**
   * @return the plural form of the given word if it needs to be pluralized (number > 1).  If the given word is in
   * {@link #pluralDict}, its mapped counterpart will be used for the plural form, otherwise will simply append
   * the letter "s" to make the plural form.
   */
  public static String pluralize(String singular, int number) {
    if (number == 1)
      return singular;
    String singularLowerCase = singular.toLowerCase();
    if (pluralDict.containsKey(singularLowerCase)) {
      String plural = pluralDict.get(singularLowerCase);
      return maybeCapitalize(plural, singular);
    }
    return singular + "s";
  }

  /**
   * @return the longest common prefix shared by the two given strings,
   * which could be the empty string.
   */
  public static String commonPrefix(String s, String t) {
    int prefixLast = -1;
    int limit = Math.min(s.length(), t.length());  // will throw NPE if either string is null (as expected)
    for (int i = 0; i < limit; i++) {
      if (s.charAt(i) == t.charAt(i))
        prefixLast = i;
      else
        break;
    }
    return s.substring(0, prefixLast+1);
  }

  /**
   * @return the longest common suffix (at the end) shared by the two given strings,
   * which could be the empty string.
   */
  public static String commonSuffix(String s, String t) {
    int sNext = s.length();
    int tNext = t.length();
    while (--sNext >= 0 && --tNext >= 0) {
      if (s.charAt(sNext) != t.charAt(tNext))
        break;
    }
    return s.substring(sNext+1, s.length());
  }

  /**
   * Useful for printing debug info.
   * @return a string that looks like "(arg1, arg2, arg3)"
   */
  public static String tupleToString(Object... args) {
    StringBuilder buf = new StringBuilder(64).append('(');
    appendArgs(buf, args);
    return buf.append(')').toString();
  }

  /**
   * @return the given string surrounded by parentheses
   */
  public static String parenthesize(String str) {
    return "(" + str + ')';
  }

  /**
   * Useful for printing debug info.
   * @return a string that looks like "name(arg1, arg2, arg3)"
   */
  public static String methodCallToString(String methodName, Object... args) {
    return methodName + tupleToString(args);
  }

  /**
   * Useful for printing debug info.
   * @return a string that looks like "name(arg1, arg2, arg3)"
   */
  public static String methodCallToStringWithResult(String methodName, Object result, Object... args) {
    StringBuilder buf = new StringBuilder(64).append(methodCallToString(methodName, args)).append(" = ");
    appendValue(buf, result);
    return buf.toString();
  }

  /**
   * Useful for printing debug info.
   * @return a string that looks like "arg1, arg2, arg3", appropriately quoting
   * and expanding the arg types as needed.  Arrays are printed using Arrays.toString
   */
  public static StringBuilder appendArgs(StringBuilder buf, Object... args) {
    for (Object arg : args) {
      appendValue(buf, arg);
      buf.append(", ");
    }
    return buf.delete(buf.length() - 2, buf.length());
  }

  private static void appendValue(StringBuilder buf, Object arg) {
    if (arg instanceof String)
      appendSurrounded(buf, arg, "\"");
    else if (arg instanceof Character)
      appendSurrounded(buf, arg, "\'");
    else if (arg instanceof Object[])
      buf.append(Arrays.toString((Object[])arg));
    else if (arg instanceof byte[])
      buf.append(Arrays.toString((byte[])arg));
    else if (arg instanceof short[])
      buf.append(Arrays.toString((short[])arg));
    else if (arg instanceof int[])
      buf.append(Arrays.toString((int[])arg));
    else if (arg instanceof long[])
      buf.append(Arrays.toString((long[])arg));
    else if (arg instanceof float[])
      buf.append(Arrays.toString((float[])arg));
    else if (arg instanceof double[])
      buf.append(Arrays.toString((double[])arg));
    else if (arg instanceof boolean[])
      buf.append(Arrays.toString((boolean[])arg));
    else if (arg instanceof char[])
      buf.append(Arrays.toString((char[])arg));
    else
      buf.append(arg);
  }

  /**
   * Appends s to the buffer surrounded by prefixAndSuffix.
   * @return the given buffer, for method chaining.
   */
  public static StringBuilder appendSurrounded(StringBuilder buf, Object s, String prefixAndSuffix) {
    return buf.append(prefixAndSuffix).append(s).append(prefixAndSuffix);
  }

  /**
   * @return {@code str} surrounded by {@code wrapper} on both sides.
   */
  public static String surround(String str, String wrapper) {
    return wrapper + str + wrapper;
  }

  /**
   * If {@code str} is not empty and neither is {@code token}, appends {@code delimiter} followed by {@code token}.
   * If only {@code str} is empty but {@code token}, simply appends {@code token}.  If both are empty, does nothing.
   * @return the given {@link StringBuilder}, for method chaining.
   */
  public static StringBuilder append(StringBuilder str, CharSequence delimiter, CharSequence token) {
    // TODO: unit test this method
    if (token.length() > 0) {
      if (str.length() > 0)
        str.append(delimiter);
      str.append(token);
    }
    return str;
  }

  /** Returns a string which represents a mirror image of the given string */
  public static String reverse(String s) {
    // in pure Java, you could do it like this: return new StringBuilder(s).reverse().toString();
    // but GWT doesn't emulate the StringBuilder.reverse method
    char[] chars = s.toCharArray();
    for (int i = 0, j = chars.length-1; i < j; i++, j--) {
      // swap the symmetric characters at indices i and j
      char temp = chars[i];
      chars[i] = chars[j];
      chars[j] = temp;
    }
    return new String(chars);
  }

  /**
   * Another debugging method that pretty prints a 2d matrix into a string,
   * such that the indices are visible and all values are aligned.
   * @param matrix
   * @return
   */
  public static String matrixToPrettyString(final String[][] matrix) {
    MaxComparable<Integer> maxWidth = new MaxComparable<Integer>();
    MaxComparable<Integer> maxColIndex = new MaxComparable<Integer>();
    for (int i = 0; i < matrix.length; i++) {
      int m = matrix[i].length;
      maxColIndex.update(m-1);
      for (int j = 0; j < m; j++) {
        maxWidth.update(matrix[i][j].length());
      }
    }
    maxWidth.update((maxColIndex.get()-1) / 10 + 1); // account for the width of the largest index
    final StringBuilder str = new StringBuilder();
    final int w = maxWidth.get();
    class Printer {
      void printValue(String value, int maxWidth) {
        if (value == null)
          value = "";
        int dLen = maxWidth - value.length();
        for (int i = 0; i < dLen; i++) {
          // pad the value with spaces on the left
          str.append(' ');
        }
        str.append(value);
        str.append(' ');  // delimit columns with a space
      }
      void printValue(String value) {
        printValue(value, w);
      }
      void printRowIndex(int i) {
        String s = (i >= 0) ? Integer.toString(i) : "";
        printValue(s, (matrix.length-1) / 10 + 1);
      }
    }
    Printer p = new Printer();
    // first print the column indices
    p.printRowIndex(-1);
    for (int j = 0; j <= maxColIndex.get(); j++) {
      p.printValue(Integer.toString(j));
    }
    str.append('\n');
    // now print the values
    for (int i = 0; i < matrix.length; i++) {
      int m = matrix[i].length;
      p.printRowIndex(i);
      for (int j = 0; j < m; j++) {
        p.printValue(matrix[i][j]);
      }
      str.append('\n');
    }
    return str.toString();
  }

  /**
   * Splits the given string on the given delimiter, returning a list of the tokens (each one trimmed). Ignores elements
   * whose value is an empty string after trimming.
   * @param str a string like "a, b, c"
   * @param delimRegex a regular expression like ","
   * @return a list like ["a", "b", "c"]
   */
  public static List<String> splitAndTrim(String str, String delimRegex) {
    String[] parts = str.trim().split(delimRegex);
    List<String> ret = new ArrayList<String>(parts.length);
    for (String part : parts) {
      String trimmedPart = part.trim();
      if (notBlank(trimmedPart))
        ret.add(trimmedPart);
    }
    return ret;
  }

  public static List<Character> asList(CharSequence str) {
    return CollectionUtils.asList(new CharSequenceIterator(str));
  }

}