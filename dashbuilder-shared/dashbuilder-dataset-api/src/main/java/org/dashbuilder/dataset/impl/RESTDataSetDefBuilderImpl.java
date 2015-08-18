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
package org.dashbuilder.dataset.impl;

import org.dashbuilder.dataset.def.RESTDataSetDef;
import org.dashbuilder.dataset.def.RESTDataSetDefBuilder;
import org.dashbuilder.dataset.def.DataSetDef;

public class RESTDataSetDefBuilderImpl extends AbstractDataSetDefBuilder<RESTDataSetDefBuilderImpl> implements RESTDataSetDefBuilder<RESTDataSetDefBuilderImpl> {

    protected DataSetDef createDataSetDef() {
        return new RESTDataSetDef();
    }

    @Override
    public RESTDataSetDefBuilderImpl serverRootURL(String url) {
        ((RESTDataSetDef) def).setServerRootURL(url);
        return this;
    }

    @Override
    public RESTDataSetDefBuilderImpl expression(String expression) {
        ((RESTDataSetDef) def).setExpression(expression);
        return this;
    }

}
