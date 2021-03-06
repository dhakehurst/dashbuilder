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

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.github.gwtbootstrap.client.ui.event.ShownEvent;
import com.github.gwtbootstrap.client.ui.event.ShownHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.displayer.client.resources.i18n.CommonConstants;
import org.uberfire.ext.widgets.common.client.common.popups.BaseModal;

@Dependent
public class DisplayerEditorPopup extends BaseModal {

    interface Binder extends UiBinder<Widget, DisplayerEditorPopup> {}
    private static Binder uiBinder = GWT.create(Binder.class);

    @UiField(provided = true)
    DisplayerEditor editor;

    private DisplayerSettings settings;
    private DisplayerEditor.Listener editorListener;
    private HandlerRegistration showHandlerRegistration;

    @Inject
    public DisplayerEditorPopup(DisplayerEditor editor) {
        this.editor = editor;
        add(uiBinder.createAndBindUi(this));
        setMaxHeigth("550px");
        setWidth(950);
    }

    public void init(final DisplayerSettings settings, final DisplayerEditor.Listener editorListener) {
        this.settings = settings;
        this.editorListener = editorListener;
        show();
        this.showHandlerRegistration = this.addShownHandler(shownHandler);
    }

    /**
     * <p>The popup must be visible in order that the table can display the different row's values. So after popup is shown, initialize the editor.</p>
     */
    private final ShownHandler shownHandler = new ShownHandler() {
        @Override
        public void onShown(final ShownEvent shownEvent) {
            editor.init(settings, editorListener);
            setTitle(CommonConstants.INSTANCE.displayer_editor_title());
            if (editor.isBrandNewDisplayer()) setTitle(CommonConstants.INSTANCE.displayer_editor_new());
            removeShownHandler();
        }
    };

    @UiHandler("cancelButton")
    void cancel(final ClickEvent event) {
        hide();
        editor.close();
        clearSettings();
    }

    @UiHandler("okButton")
    void ok(final ClickEvent event) {
        hide();
        editor.save();
        clearSettings();
    }

    private void removeShownHandler() {
        if (this.showHandlerRegistration != null) {
            this.showHandlerRegistration.removeHandler();
            this.showHandlerRegistration = null;
        }
    }

    private void clearSettings() {
        this.settings = null;
        this.editorListener = null;
        removeShownHandler();
    }
}
