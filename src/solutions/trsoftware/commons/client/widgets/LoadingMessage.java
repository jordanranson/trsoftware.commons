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

package solutions.trsoftware.commons.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import solutions.trsoftware.commons.client.styles.CellPanelStyle;

/**
 * Date: Dec 18, 2007
 * Time: 9:37:13 PM
 *
 * @author Alex
 */
public class LoadingMessage extends Composite {
  
  public LoadingMessage(String message, boolean startVisible) {
    initWidget(Widgets.horizontalPanel(new CellPanelStyle().setSpacing(5),
        new LoadingImage(),
        new Label(message)));
    setStyleName("loading-message");
    if (!startVisible)
      setVisible(false);
  }

  public LoadingMessage(boolean startVisible) {
    this("Loading...", startVisible);
  }

  public LoadingMessage() {
    this(true);
  }

  public LoadingMessage(String message) {
    this(message, true);
  }

}