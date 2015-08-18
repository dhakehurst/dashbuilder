package org.dashbuilder.backend;

import java.util.Date;
import java.util.List;

import org.dashbuilder.dataset.ColumnType;
import org.dashbuilder.dataset.DataColumn;
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dataset.DataSetMetadata;
import org.dashbuilder.dataset.def.DataSetDef;

public class RESTDataSet implements DataSet {

	@Override
	public DataSetMetadata getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSetDef getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefinition(DataSetDef def) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUUID(String uuid) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getCreationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCreationDate(Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DataColumn> getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColumns(List<DataColumn> columnList) {
		// TODO Auto-generated method stub

	}

	@Override
	public DataColumn getColumnById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataColumn getColumnByIndex(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnIndex(DataColumn dataColumn) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DataSet addColumn(String id, ColumnType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet addColumn(String id, ColumnType type, List values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet addColumn(DataColumn column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet removeColumn(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValueAt(int row, String columnId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet setValueAt(int row, int column, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet setValuesAt(int row, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet addValuesAt(int row, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet addValues(Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet addEmptyRowAt(int row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet trim(int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet trim(List<Integer> rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRowCountNonTrimmed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRowCountNonTrimmed(int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public DataSet cloneEmpty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSet cloneInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getEstimatedSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
