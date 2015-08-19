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
package org.dashbuilder.dataprovider.backend.rest;

import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.commons.io.IOUtils;
import org.dashbuilder.dataprovider.DataSetProvider;
import org.dashbuilder.dataprovider.DataSetProviderType;
import org.dashbuilder.dataprovider.backend.StaticDataSetProvider;
import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dataset.DataSetFactory;
import org.dashbuilder.dataset.DataSetLookup;
import org.dashbuilder.dataset.DataSetMetadata;
import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dataset.def.DataSetDefRegistry;
import org.dashbuilder.dataset.def.RESTDataSetDef;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

/**
 * @author Dr. David H. Akehurst
 */
@ApplicationScoped
@Named("rest")
public class RestDataSetProvider implements DataSetProvider {

	@Inject
	protected StaticDataSetProvider staticDataSetProvider;

	@Inject
	protected DataSetDefRegistry dataSetDefRegistry;

	@Inject
	protected Logger log;

	@Override
	public DataSetProviderType getType() {
		return DataSetProviderType.REST;
	}

	@Override
	public DataSetMetadata getDataSetMetadata(DataSetDef def) throws Exception {
		DataSet dataSet = lookupDataSet(def, null);
		if (dataSet == null)
			return null;
		return dataSet.getMetadata();
	}

	@Override
	public DataSet lookupDataSet(DataSetDef def, DataSetLookup lookup) throws Exception {
		RESTDataSetDef dataSetDef = (RESTDataSetDef)def;
		String targetUrlString = dataSetDef.getServerRootURL();
		String expression = dataSetDef.getExpression();
		
		DataSet dataSet = DataSetFactory.newEmptyDataSet();
		dataSet.setUUID(dataSetDef.getUUID());
		dataSet.setDefinition(dataSetDef);
		JsonArray array = this.fetchDataAsArray(targetUrlString, expression);
		boolean columnsDone = false;
		for(JsonValue v: array) {
			if (JsonValue.ValueType.OBJECT == v.getValueType()) {
				JsonObject obj = (JsonObject)v;
				if (!columnsDone) {
					columnsDone=true;
					for(String s:obj.keySet()) {
						JsonValue pv = obj.get(s);
						ColumnType type = ColumnType.LABEL;
						if (JsonValue.ValueType.NUMBER == pv.getValueType()) {
							type = ColumnType.NUMBER;
						}
						dataSet.addColumn(s, type);
					}
				}
				ArrayList<Object> javaValues = new ArrayList<>();
				for(JsonValue pv: obj.values()) {
					if (JsonValue.ValueType.NUMBER == pv.getValueType()) {
						javaValues.add( ((JsonNumber)pv).intValue() );
					} else if (JsonValue.ValueType.STRING == pv.getValueType()) {
						javaValues.add( ((JsonString)pv).getString() );
					} else {
						javaValues.add(pv.toString());
					}
				}
				dataSet.addValues( javaValues.toArray(new Object[javaValues.size()]) );
			}
		}
		
//		dataSet.addColumn("name", ColumnType.TEXT);
//		dataSet.addColumn("age", ColumnType.NUMBER);
//		
//		dataSet.addValues("Fred", 25);
//		dataSet.addValues("Jane", 24);
		
		 staticDataSetProvider.registerDataSet(dataSet);
		
		// Return the lookup results
		return dataSet;
	}

	@Override
	public boolean isDataSetOutdated(DataSetDef def) {
		return true;
	}

	JsonArray fetchDataAsArray(String targetUrlString, String expression) {
		log.debug("targetUrl = "+targetUrlString);
		log.debug("expression = "+expression);
		try {
			URL url = new URL(targetUrlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			String js = IOUtils.toString(conn.getInputStream());
			log.debug("json data = "+js);
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("JavaScript");
			engine.put("json", js);
			String setup = "var data=JSON.parse(json);";
			String eval = "result = " + expression + ";";
			String endit = "var jr = JSON.stringify(result)";
			log.debug("evaluating JS: "+setup + eval + endit);
			engine.eval(setup + eval + endit);
			String result = (String) engine.get("jr");
			log.debug("result json = "+result);
			JsonReader jsonReader = Json.createReader(new StringReader(result));
			JsonArray jsonArray = jsonReader.readArray();
			jsonReader.close();
			return jsonArray;
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
