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

package solutions.trsoftware.commons.shared.util.trees;

import solutions.trsoftware.commons.shared.util.StringUtils;

/**
 * @author Alex
 * @since 2/9/2018
 */
public class PrintVisitor<T extends Node> extends AbstractVisitor<T> {

  public PrintVisitor() {
  }

  public PrintVisitor(TraversalStrategy strategy) {
    super(strategy);
  }

  @Override
  public void visit(T node) {
    System.out.println(StringUtils.repeat(' ', node.depth()*2) + "+ " + node.toString());
  }
}
