/**
 * Copyright (C) 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.displayer.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.displayer.client.resources.i18n.CommonConstants;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.uberfire.ext.widgets.common.client.common.popups.BaseModal;
import org.uberfire.ext.widgets.common.client.common.popups.footers.ModalFooterOKCancelButtons;

import javax.enterprise.context.Dependent;

@Dependent
public class DisplayerEditorPopup extends BaseModal {

    interface Binder extends UiBinder<ModalBody, DisplayerEditorPopup> {}
    private static Binder uiBinder = GWT.create(Binder.class);

    @UiField(provided = true)
    DisplayerEditor editor;

    public DisplayerEditorPopup() {
        this(new DisplayerEditor());
    }

    public DisplayerEditorPopup(DisplayerEditor editor) {
        this.editor = editor;
        add(uiBinder.createAndBindUi(this));
        ModalFooterOKCancelButtons footer = new ModalFooterOKCancelButtons(okCommand, cancelCommand);
        footer.enableCancelButton(true);
        footer.enableOkButton(true);
        add(footer);
        setWidth(950+"px");
    }

    public void init(DisplayerSettings settings, DisplayerEditor.Listener editorListener) {
        editor.init(settings, editorListener);
        setTitle(CommonConstants.INSTANCE.displayer_editor_title());
        if (editor.isBrandNewDisplayer()) setTitle(CommonConstants.INSTANCE.displayer_editor_new());
        show();
    }

    private final Command cancelCommand = new Command() {
        @Override
        public void execute() {
            hide();
            editor.close();
        }
    };

    private final Command okCommand = new Command() {
        @Override
        public void execute() {
            hide();
            editor.save();
        }
    };

}
