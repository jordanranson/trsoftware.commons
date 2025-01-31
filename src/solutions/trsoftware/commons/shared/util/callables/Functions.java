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

package solutions.trsoftware.commons.shared.util.callables;

import com.google.web.bindery.event.shared.UmbrellaException;

import java.util.HashSet;
import java.util.Set;

/**
 * Jan 12, 2010
 *
 * @author Alex
 */
public abstract class Functions {

  public static Function1 IDENTITY_FUNCTION = arg -> arg;

  /** @return the {@link T} &rarr; {@link T} identity function, {@code f(x) = x }*/
  @SuppressWarnings("unchecked")
  public static <T> Function1<T, T> identity() {
    return IDENTITY_FUNCTION;
  }

  /**
   * Calls the supplied function for each item in the given iterator, aggregating any exceptions into a
   * single {@link UmbrellaException}, which will be thrown at the end, if needed.
   * Use this method when you want to be sure that some code will be executed for all elements in a collection,
   * regardless of exceptions.
   * @throws UmbrellaException if the function throws an exception for any of the items.
   */
  public static <T> void tryCall(Iterable<T> items, Function1_<T> func) {
    Set<Throwable> caught = null;
    for (T item : items) {
      try {
        func.call(item);
      } catch (Throwable e) {
        if (caught == null)
          caught = new HashSet<Throwable>();
        caught.add(e);
      }
    }
    if (caught != null)
      throw new UmbrellaException(caught);
  }

}
