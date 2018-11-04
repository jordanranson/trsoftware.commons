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

package solutions.trsoftware.commons.shared.text.markovchain.dict;

/**
 * Maps words in the dictionary to unique shorts and back again.
 *
 * Supports dictionaries with up to Short.MAX_VALUE = 32767 entries.
 *
 * Uses StringUtf8 for inner to save memory.
 *
 * Oct 20, 2009
 *
 * @author Alex
 */
public class ShortArrayCodingDictionary extends ArrayCodingDictionary<String, Short> {

  protected String encodeKey(String key) {
    return key;
  }

  protected String decodeKey(String key) {
    return key;
  }

  protected Short makeValue(int value) {
    return (short)value;
  }
}