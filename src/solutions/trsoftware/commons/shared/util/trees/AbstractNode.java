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

import static solutions.trsoftware.commons.client.util.LogicUtils.firstNonNull;

/**
 *
 * @author Alex, 10/31/2017
 */
public abstract class AbstractNode<T> implements Node<T> {

  @Override
  public <V extends Visitor> void accept(V visitor) {
    TraversalStrategy strategy = firstNonNull(visitor.getStrategy(), TraversalStrategy.PRE_ORDER);
    strategy.traverse(this, visitor);
    /*
    TODO: might want to also support generic visitation using startVisit/endVisit methods instead of a TraversalStrategy
    (see https://en.wikipedia.org/wiki/Tree_traversal#Generic_tree and com.google.gwt.dev.js.ast.JsVisitor)
    */
  }
}
