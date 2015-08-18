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

/**
 * A builder for defining REST API data sets
 *
 * <pre>
 *    DataSetDef dataSetDef = DataSetDefFactory.newRESTDataSetDef()
 *     .uuid("all_employees")
 *     .serverRootURL("http://myhost.com/servivce/api")
 *     .buildDef();
 * </pre>
 */
public interface RESTDataSetDefBuilder<T extends DataSetDefBuilder> extends DataSetDefBuilder<T> {

    /**
     * A valid REST stream URL
     *
     * @param url A valid URL to a REST API service
     * @return The DataSetDefBuilder instance that is being used to configure a DataSetDef.
     */
    T serverRootURL(String url);

    T expression(String expression);
}
