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
package org.dashbuilder.client.widgets.dataset.editor.widgets.editors.rest;

import java.util.List;

import javax.enterprise.context.Dependent;

import org.dashbuilder.client.widgets.dataset.editor.widgets.editors.AbstractDataSetDefEditor;
import org.dashbuilder.common.client.validation.editors.ValueBoxEditorDecorator;
import org.dashbuilder.dataset.client.validation.editors.RESTDataSetDefEditor;
import org.dashbuilder.dataset.def.DataSetDef;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>This is the view implementation for Data Set Editor widget for editing REST provider specific attributes.</p>
 *
 * @since 0.3.0 
 * @author Dr. David H. Akehurst
 *  
 */
@Dependent
public class RESTDataSetDefAttributesEditor extends AbstractDataSetDefEditor implements RESTDataSetDefEditor {
    
    interface RESTDataSetDefAttributesEditorBinder extends UiBinder<Widget, RESTDataSetDefAttributesEditor> {}
    private static RESTDataSetDefAttributesEditorBinder uiBinder = GWT.create(RESTDataSetDefAttributesEditorBinder.class);

    @UiField
    FlowPanel restAttributesPanel;
    
    @UiField
    @Path("serverRootURL")
    ValueBoxEditorDecorator<String> serverRootURL;
    
    @UiField
    @Path("expression")
    ValueBoxEditorDecorator<String> expression;
    
    private boolean isEditMode;

    public RESTDataSetDefAttributesEditor() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    @Override
    public void set(DataSetDef dataSetDef) {
        super.set(dataSetDef);
    }
    
    @Override
    public void showErrors(List<EditorError> errors) {
        consumeErrors(errors);
    }
        
    public void clear() {
        super.clear();
        serverRootURL.clear();
        expression.clear();
    }
    
}
