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

package solutions.trsoftware.commons.shared.util.trees;

import java.util.Collection;

/**
 * The base type of a <a href="https://en.wikipedia.org/wiki/Tree_(data_structure)">tree</a> node which supports
 * the <a href="https://en.wikipedia.org/wiki/Visitor_pattern">Visitor pattern</a>.
 *
 * @param <T> the type of the data stored in the node.
 * @see Visitor
 * @author Alex, 10/31/2017
 */
public interface Node<T> {

  <V extends Visitor> void accept(V visitor);

  Collection<Node<T>> getChildren();

  /** @return the value stored in this node */
  T getValue();
}
