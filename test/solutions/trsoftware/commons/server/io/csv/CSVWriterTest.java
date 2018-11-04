/*
 * Copyright 2018 TR Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package solutions.trsoftware.commons.server.io.csv;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sep 28, 2009
 *
 * @author Alex
 */
public class CSVWriterTest extends TestCase {
  CSVWriter csvWriter;
  private static final String sep = System.getProperty("line.separator");

  public void testCSVWriter() throws Exception {
    // make sure elements that don't need to be quoted aren't
    checkWritingAndReading("foo,1,bar"+ sep, new String[]{"foo", "1", "bar"});
    // the last two elements need to be quoted and the last needs the quote chars escaped
    checkWritingAndReading(
        "foo,1,\"2,3,4\",\"Joe said, \"\"bar this fool\"\"\"" + sep,
        new String[]{"foo", "1", "2,3,4", "Joe said, \"bar this fool\""});
  }

  public void testUnicode() throws Exception {
    checkWritingAndReading("book,\"SPAM: A Biography: The Amazing True Story of America's \"\"Miracle Meat!\"\" (????: ?????????: The Amazing True Story ???? ??????? ????!)\",Carolyn Wyman,russian,00156004771,3,6,351,\"Jay ????????? ?? ?????????? ??????? ? ??? ????????? ??????? ????? ? ??????? ???????? ????????, ??????? ?? ???????????, ???????? ?????? ?? ??? ???? ??????????. ????? ????, ?? ????????? ???? ????????? ?????????? ? ???? ?????? ???????? ?????????????, ??? ????? ????? ?????????? ????? ????????? ???????????? ?? ??????? ???????? ????? ????????? ??? ?? ???????.\",Bookfail,http://www.bookfail.com/,\"For more bizarre and offbeat book titles, visit Bookfail.com (NSFW)\",1390392" + sep,
        new String[]{"book", "SPAM: A Biography: The Amazing True Story of America's \"Miracle Meat!\" (????: ?????????: The Amazing True Story ???? ??????? ????!)", "Carolyn Wyman", "russian", "00156004771", "3", "6", "351", "Jay ????????? ?? ?????????? ??????? ? ??? ????????? ??????? ????? ? ??????? ???????? ????????, ??????? ?? ???????????, ???????? ?????? ?? ??? ???? ??????????. ????? ????, ?? ????????? ???? ????????? ?????????? ? ???? ?????? ???????? ?????????????, ??? ????? ????? ?????????? ????? ????????? ???????????? ?? ??????? ???????? ????? ????????? ??? ?? ???????.", "Bookfail", "http://www.bookfail.com/", "For more bizarre and offbeat book titles, visit Bookfail.com (NSFW)", "1390392"});
  }

  public void testMultiline() throws Exception {
    List<Object[]> inputLines = new ArrayList<Object[]>();
    inputLines.add(new Object[]{1, "foo", 1.5});
    inputLines.add(new Object[]{2, "bar", 2.5});

    String expectedOutput = "1,foo,1.5" + sep + "2,bar,2.5" + sep;

    StringWriter stringWriter = new StringWriter();
    CSVWriter csvWriter = new CSVWriter(stringWriter);
    csvWriter.writeAll(inputLines);
    String output = stringWriter.getBuffer().toString();
    assertEquals(expectedOutput, output);

    // make sure we get our original input back when we read the output.
    CSVReader csvReader = new CSVReader(new StringReader(output));
    List<String[]> resultLines = csvReader.readAll();
    for (int i = 0; i < inputLines.size(); i++) {
      Object[] inputRow = inputLines.get(i);
      String[] outputRow = resultLines.get(i);
      assertEquals("row " + i + " length does not match input", inputRow.length, outputRow.length);
      for (int j = 0; j < inputRow.length; j++) {
        assertEquals("element " + i + "," + j + " does not match input", String.valueOf(inputRow[j]), outputRow[j]);
      }
    }
  }

  /**
   * Checks that the output of CSVWriter matches the expectation, and also that
   * it will be read in as the original input by the CSVReader
   */
  private void checkWritingAndReading(String expectedOutput, String[] input) throws IOException {
    StringWriter stringWriter = new StringWriter();
    CSVWriter csvWriter = new CSVWriter(stringWriter);
    csvWriter.writeNext(input);
    String output = stringWriter.getBuffer().toString();
    assertEquals(expectedOutput, output);

    // make sure we get our original input back when we read the output.
    CSVReader csvReader = new CSVReader(new StringReader(output));
    String[] result = csvReader.readNext();
    assertEquals(Arrays.asList(input), Arrays.asList(result));
  }

  public void testWriteCsvLine() throws Exception {
    assertEquals("1,foo,1.5" + sep, CSVWriter.writeCsvLine(new Object[]{1, "foo", 1.5}));
  }

  public void testProperlyQuotesLineBreaks() throws Exception {
    checkWritingAndReading("A,\"B\nC\nD\",E"+ sep, new String[]{"A", "B\nC\nD", "E"});
    checkWritingAndReading("\"1\n23\n4\",5,6"+ sep, new String[]{"1\n23\n4", "5", "6"});
  }
}