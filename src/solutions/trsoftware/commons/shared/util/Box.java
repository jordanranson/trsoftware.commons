/*
 * Copyright 2021 TR Software Inc.
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
 */

package solutions.trsoftware.commons.shared.util;

/**
 * A container for a value, useful for inside closures, where all references
 * must be final.
 *
 * @author Alex
 */
public class Box<T> implements TakesValue<T> {
  private T value;
  private boolean initialized;

  public Box() {
  }

  public Box(T value) {
    setValue(value);
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
    initialized = true;
  }

  /**
   * Sets a new value and returns the old one.
   * @return the old {@link #value}
   */
  public T replaceValue(T value) {
    T oldValue = this.value;
    setValue(value);
    return oldValue;
  }

  public boolean hasValue() {
    return initialized;
  }
}
