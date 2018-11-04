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

package solutions.trsoftware.commons.shared.util.text;

import solutions.trsoftware.commons.client.bridge.text.AbstractNumberFormatter;
import solutions.trsoftware.commons.shared.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Allows formatting and parsing numbers using the same class in both clientside GWT code and serverside Java code.
 *
 *<p>
 *  <b>NOTE</b>: this requires {@link DecimalFormat} to be emulated in GWT (it's not part of GWT's
 *  <a href="http://www.gwtproject.org/doc/latest/RefJreEmulation.html">JRE emulation library</a>).
 *
 *  Our emulated version of {@link DecimalFormat} is located in our module's {@code super-source} directory
 *  ({@code src/solutions/trsoftware/commons/translatable})
 *</p>
 *
 * <p style="color: #6495ed; font-weight: bold;">
 *   TODO: use this class to replace {@link AbstractNumberFormatter}
 * </p>
 * @author Alex, 10/31/2017
 */
public class SharedNumberFormat {

  private DecimalFormat format;

  /**
   * Creates a {@link DecimalFormat} with the given pattern.
   * @param pattern a formatting string as described in the doc for {@link DecimalFormat}
   */
  public SharedNumberFormat(String pattern) {
    format = new DecimalFormat(pattern);
  }

  /**
   * Creates a {@link DecimalFormat} from a pattern string obtained by invoking {@link #buildPattern(int, int, int, boolean)}
   * with the given parameters.
   * @see #buildPattern(int, int, int, boolean)
   */
  public SharedNumberFormat(int minIntegerDigits, int minFractionDigits, int maxFractionDigits, boolean percent) {
    this(buildPattern(minIntegerDigits, minFractionDigits, maxFractionDigits, percent));
  }

  /**
   * Creates a {@link DecimalFormat} from a pattern string obtained by invoking
   * {@link #buildPattern(int, int, int, boolean) <code>buildPattern(0, 0, maxFractionDigits, false)</code>}
   * @see #buildPattern(int, int, int, boolean)
   */
  public SharedNumberFormat(int maxFractionDigits) {
    this(buildPattern(0, 0, maxFractionDigits, false));
  }

  /**
   * Creates a format pattern string suitable for the {@link DecimalFormat} constructor based on the given parameters.
   * @return a string suitable for {@link DecimalFormat#DecimalFormat(String)}
   */
  public static String buildPattern(int minIntegerDigits, int minFractionDigits, int maxFractionDigits, boolean percent) {
    StringBuilder patternBuffer = new StringBuilder();
    if (minIntegerDigits <= 0)
      patternBuffer.append('#');
    else
      patternBuffer.append(requiredDigitsPattern(minIntegerDigits));
    maxFractionDigits = Math.max(minFractionDigits, maxFractionDigits);  // ensure that maxFD >= minFD
    if (minFractionDigits > 0 || maxFractionDigits > 0)
      patternBuffer.append('.');
    if (minFractionDigits > 0)
      patternBuffer.append(requiredDigitsPattern(minFractionDigits));
    if (maxFractionDigits > 0)
      patternBuffer.append(optionalDigitsPattern(maxFractionDigits - minFractionDigits));
    if (percent)
      patternBuffer.append('%');
    return patternBuffer.toString();
  }

  private static String requiredDigitsPattern(int nDigits) {
    return StringUtils.repeat('0', nDigits);
  }

  private static String optionalDigitsPattern(int nDigits) {
    return StringUtils.repeat('#', nDigits);
  }

  public String format(double value) {
    return format.format(value);
  }

  /**
   * Parses the given string into a number according to this formatting pattern.
   * Returns a {@code double} instead of {@link Number} (as in {@link DecimalFormat#parse(String)})
   * because on the client-side, our emulated version of {@link DecimalFormat}
   * ({@code src/solutions/trsoftware/commons/translatable/java/text/DecimalFormat.java})
   * uses {@link com.google.gwt.i18n.client.NumberFormat#parse(String)}, which returns a {@code double}.
   */
  public double parse(String source) throws ParseException {
    return format.parse(source).doubleValue();
  }

  /**
   * @return the pattern used by this number format.
   */
  public String getPattern() {
    return format.toPattern();
  }
}
