package org.dashbuilder.dataprovider.backend.elasticsearch.rest.client.impl.jest.gson;

import com.google.gson.*;
import org.dashbuilder.dataprovider.backend.elasticsearch.rest.client.model.SearchHitResponse;
import org.dashbuilder.dataset.DataColumn;
import org.dashbuilder.dataset.DataSetMetadata;
import org.dashbuilder.dataset.def.ElasticSearchDataSetDef;

import java.lang.reflect.Type;
import java.util.*;

public class HitDeserializer extends AbstractAdapter<HitDeserializer> implements JsonDeserializer<SearchHitResponse> {

    public HitDeserializer(DataSetMetadata metadata, ElasticSearchDataSetDef definition, List<DataColumn> columns) {
        super(metadata, definition, columns);
    }

    @Override
    public SearchHitResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SearchHitResponse result = null;
        if (typeOfT.equals(SearchHitResponse.class)) {

            JsonObject hitObject = (JsonObject) json;
            float score = 0;
            JsonElement scoreElement = hitObject.get("_score");
            if (scoreElement != null && scoreElement.isJsonPrimitive()) score = scoreElement.getAsFloat();
            String index = hitObject.get("_index").getAsString();
            String id = hitObject.get("_id").getAsString();
            String type = hitObject.get("_type").getAsString();
            long version = 0;
            Map<String ,Object> fields = new HashMap<String, Object>();
            JsonElement sourceObject = hitObject.get("_source");
            JsonElement fieldsObject = hitObject.get("fields");

            if (fieldsObject != null && fieldsObject.isJsonObject()) {
                Set<Map.Entry<String, JsonElement>> _fields = ((JsonObject)fieldsObject).entrySet();
                for (Map.Entry<String, JsonElement> field : _fields) {
                    String fieldName = field.getKey();
                    JsonElement fieldValueArray = field.getValue();
                    if (fieldValueArray != null && fieldValueArray.isJsonArray()) {
                        Iterator fieldValueArrayIt = ((JsonArray)fieldValueArray).iterator();
                        while (fieldValueArrayIt.hasNext()) {
                            JsonElement element = (JsonElement) fieldValueArrayIt.next();
                            if (element != null && element.isJsonPrimitive()) fields.put(fieldName, element.getAsString());
                        }
                    }
                }
            }

            if (sourceObject != null && sourceObject.isJsonObject()) {
                Set<Map.Entry<String, JsonElement>> _fields = ((JsonObject)sourceObject).entrySet();
                for (Map.Entry<String, JsonElement> field : _fields) {
                    String fieldName = field.getKey();
                    String fieldValue = field.getValue().getAsString();
                    fields.put(fieldName, fieldValue);
                }

            }

            result = new SearchHitResponse(score, index, id, type, version, orderFields(fields, columns));
        }

        return result;
    }

}