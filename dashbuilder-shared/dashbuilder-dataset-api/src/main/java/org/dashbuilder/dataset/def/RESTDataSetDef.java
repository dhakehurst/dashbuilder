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
package org.dashbuilder.dataset.def;

import org.dashbuilder.dataprovider.DataSetProviderType;
import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.DataColumn;
import org.jboss.errai.common.client.api.annotations.Portable;

import javax.validation.constraints.NotNull;

/**
 * @author Dr. David H. Akehurst
 */
@Portable
public class RESTDataSetDef extends DataSetDef {


    public RESTDataSetDef() {
        super.setProvider(DataSetProviderType.REST);
    }

    String serverRootURL;
    public String getServerRootURL() {
        return serverRootURL;
    }

    public void setServerRootURL(String serverRootURL) {
        this.serverRootURL = serverRootURL;
    }

    String expression;
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public DataSetDef clone() {
        RESTDataSetDef def = new RESTDataSetDef();
        clone(def);
        def.setServerRootURL(this.getServerRootURL());
        def.setExpression(this.getExpression());
        return def;
    }
    
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("URL=").append(serverRootURL).append("\n");
        out.append("expression=").append(expression).append("\n");
        
        out.append("\n");
        out.append("UUID=").append(UUID).append("\n");
        out.append("Provider=").append(provider).append("\n");
        out.append("Public=").append(isPublic).append("\n");
        out.append("Push enabled=").append(pushEnabled).append("\n");
        out.append("Push max size=").append(pushMaxSize).append(" Kb\n");
        if (refreshTime != null) {
            out.append("Refresh time=").append(refreshTime).append("\n");
            out.append("Refresh always=").append(refreshAlways).append("\n");
        }
        return out.toString();
    }
}
