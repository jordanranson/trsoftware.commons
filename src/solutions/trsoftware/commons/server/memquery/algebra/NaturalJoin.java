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

package solutions.trsoftware.commons.server.memquery.algebra;

import solutions.trsoftware.commons.server.memquery.RelationSchema;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex, 1/10/14
 */
public class NaturalJoin extends EquiJoin {

  public NaturalJoin(RelationalExpression lhs, RelationalExpression rhs, Type type) {
    super(lhs, rhs, new Params(type, buildColNameCorrespondence(lhs.getOutputSchema(), rhs.getOutputSchema())));
  }

  /** Generates the correspondence of the equivalent cols from both sides */
  private static Map<String, String> buildColNameCorrespondence(RelationSchema leftSchema, RelationSchema rightSchema) {
    Set<String> sharedNames = getAttributeNamesIntersection(leftSchema, rightSchema);
    LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
    for (String name : sharedNames) {
      ret.put(name, name);
    }
    return ret;
  }

}
