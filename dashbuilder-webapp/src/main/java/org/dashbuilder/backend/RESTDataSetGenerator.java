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
package org.dashbuilder.backend;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.enterprise.context.ApplicationScoped;

import org.dashbuilder.backend.RESTDataSet;
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dataset.DataSetBuilder;
import org.dashbuilder.dataset.DataSetFactory;
import org.dashbuilder.dataset.DataSetGenerator;

import static org.dashbuilder.shared.sales.SalesConstants.*;

/**
 * Generates a random data set containing sales opportunity records.
 */
@ApplicationScoped
public class RESTDataSetGenerator implements DataSetGenerator {

    public DataSet buildDataSet(Map<String,String> params) {
        String serverUrlString = params.get("url");
        
        return new RESTDataSet();
    }


}
