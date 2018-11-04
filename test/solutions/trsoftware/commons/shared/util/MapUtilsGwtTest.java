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

package solutions.trsoftware.commons.shared.util;

import solutions.trsoftware.commons.client.CommonsGwtTestCase;

/**
 * Date: Oct 20, 2008 Time: 6:53:29 PM
 *
 * @author Alex
 */
public class MapUtilsGwtTest extends CommonsGwtTestCase {

  MapUtilsTest delegate = new MapUtilsTest();

  public void testFilterMap() throws Exception {
    delegate.testFilterMap();
  }

  public void testRemoveNullValues() throws Exception {
    delegate.testRemoveNullValues();
  }

  public void testRetainAll() throws Exception {
    delegate.testRetainAll();
  }
}
