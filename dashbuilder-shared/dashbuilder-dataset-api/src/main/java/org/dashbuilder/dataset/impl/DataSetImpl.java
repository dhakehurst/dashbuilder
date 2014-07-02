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

import java.util.List;
import java.util.ArrayList;

import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.DataColumn;
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dataset.DataSetMetadata;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DataSetImpl implements DataSet {

    protected String uuid = null;
    protected List<DataColumn> columns = new ArrayList<DataColumn>();
    protected int rowCountNonTrimmed = -1;

    public DataSetMetadata getMetadata() {
        return new DataSetMetadataImpl(this);
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public List<DataColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DataColumn> columns) {
        this.columns = columns;
    }

    public DataColumn getColumnById(String id) {
        for (DataColumn column : columns) {
            if (column.getId().equals(id)) return column;
        }
        return null;
    }

    public DataColumn getColumnByIndex(int index) {
        if (columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException("The data set is empty.");
        }
        if (index >= columns.size()) {
            throw new IllegalArgumentException("The column index " + index + " is out of bounds: " + (columns.size()-1));
        }
        return columns.get(index);
    }

    @Override
    public int getColumnIndex( DataColumn dataColumn ) {
        if (dataColumn == null || "".equals( dataColumn.getId() ) ) {
            throw new IllegalArgumentException("Wrong column specified.");
        }
        for (int i = 0; i < columns.size(); i++) {
            if ( dataColumn.getId().equalsIgnoreCase( columns.get( i ).getId() ) ) return i;
        }
        throw new IllegalArgumentException( "The column with id " + dataColumn.getId() + " does not exist." );
    }

    public DataSet addColumn(String id, ColumnType type) {
        return addColumn(id, type, null);
    }

    public DataSet addColumn(String id, ColumnType type, List values) {
        DataColumnImpl c = new DataColumnImpl();
        c.setDataSet(this);
        c.setId(id);
        c.setColumnType(type);
        if (values != null) c.setValues(values);
        columns.add(c);
        return this;
    }

    public boolean isEmpty() {
        return getRowCount() == 0;
    }

    public int getRowCount() {
        if (columns == null || columns.isEmpty()) {
            return 0;
        }
        return columns.get(0).getValues().size();
    }

    public int getRowCountNonTrimmed() {
        if (rowCountNonTrimmed == -1) return getRowCount();
        return rowCountNonTrimmed;
    }

    public Object getValueAt(int row, String columnId) {
        DataColumn columnObj = getColumnById(columnId);
        return getValueAt(row, columnObj);
    }

    public Object getValueAt(int row, int column) {
        DataColumn columnObj = getColumnByIndex(column);
        return getValueAt(row, columnObj);
    }

    protected Object getValueAt(int row, DataColumn column) {
        if (row >= getRowCount()) {
            throw new IllegalArgumentException("The row index " + row + " is out of bounds: " + (getRowCount()-1));
        }
        return column.getValues().get(row);
    }

    public DataSet setValueAt(int row, int column, Object value) {
        DataColumn columnObj = getColumnByIndex(column);

        List l = columnObj.getValues();
        if (row > l.size()) {
            throw new IllegalArgumentException("The row index " + row + " is out of bounds: " + (l.size()-1));
        }

        if (row == l.size()) l.add(value);
        l.set(row, value);
        return this;
    }

    public DataSet setValuesAt(int row, Object... values) {
        if (columns == null || columns.isEmpty()) return null;
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            setValueAt(row, i, value);
        }
        return this;
    }

    public DataSet setValues(Object[][] values) {
        if (columns == null || columns.isEmpty()) return null;
        for (int i = 0; i < values.length; i++) {
            Object[] row = values[i];
            for (int j = 0; j < row.length; j++) {
                Object value = row[j];
                setValueAt(i, j, value);
            }
        }
        return this;
    }

    public DataSet trim(int offset, int rows) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset can't be negative: " + offset);
        }
        if (offset == 0 && (rows <= 0 || rows >= this.getRowCount())) {
            this.rowCountNonTrimmed = -1;
            return this;
        }
        if (offset >= getRowCount()) {
            throw new IllegalArgumentException("Offset can't be greater than the number of rows: " + offset);
        }

        DataSetImpl other = cloneEmpty();
        other.rowCountNonTrimmed = getRowCount();
        for (int i=0; i<columns.size(); i++) {
            DataColumn column = columns.get(i);
            DataColumn colOther = other.getColumns().get(i);
            List values = column.getValues();
            List valOther = colOther.getValues();
            for (int j=offset; j<values.size() && j<( offset+rows ); j++) {
                Object value = values.get(j);
                valOther.add(value);
            }
        }
        return other;
    }

    public DataSet trim(List<Integer> rows) {
        DataSetImpl other = cloneEmpty();
        other.rowCountNonTrimmed = getRowCount();
        for (int i=0; i<columns.size(); i++) {
            List values = columns.get(i).getValues();
            List valOther = other.getColumns().get(i).getValues();
            for (Integer row : rows) {
                if (row >= values.size()) {
                    throw new IllegalArgumentException("Row number is out of bounds: " + row);
                }
                Object value = values.get(row);
                valOther.add(value);
            }
        }
        return other;
    }

    public DataSetImpl cloneEmpty() {
        DataSetImpl other = new DataSetImpl();
        for (int i=0; i<columns.size(); i++) {
            DataColumn column = columns.get(i);
            other.addColumn(column.getId(), column.getColumnType());
        }
        return other;
    }

    public long getEstimatedSize() {
        int nrows = getRowCount();
        if (nrows == 0) return 0;

        List<DataColumn> columns = getColumns();
        int ncells = nrows * columns.size();
        int result = ncells * 4;
        for (int i = 0; i < columns.size(); i++) {
            Object firstRowValue = getValueAt(0, i);
            if (firstRowValue instanceof String) {
                for (int j = 0; j < nrows; j++) {
                    String stringValue = (String) getValueAt(j, i);
                    result += MemSizeEstimator.sizeOfString(stringValue);
                }
            } else {
                int singleValueSize = MemSizeEstimator.sizeOf(firstRowValue);
                result += nrows * singleValueSize;
            }
        }
        return result;
    }
}