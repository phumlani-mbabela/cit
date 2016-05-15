package co.za.zwideheights.cit.ui.jsf;

import java.util.List;

import javax.faces.model.DataModel;

import co.za.zwidehsights.jpa.entity.cit.Grave;


public class GraveListDataModel extends DataModel<Grave>{
	
	private int rowIndex= -1;
	private int totalNumRows;
	private int pageSize;
	private List<Grave> list;

	public GraveListDataModel() {
		super();
	}
	
	public GraveListDataModel(List<Grave> list, int totalNumRows, int pageSize) {
		super();
		setWrappedData(list);
		this.totalNumRows = totalNumRows;
		this.pageSize = pageSize;
	}

	@Override
	public int getRowCount() {
		return totalNumRows;
	}

	@Override
	public Grave getRowData() {
		if(list == null)
			return null;
		else if(!isRowAvailable())
			throw new IllegalArgumentException();
		else {
			int dataIndex = getRowIndex();
			return list.get(dataIndex);
		}
	}

	@Override
	public int getRowIndex() {
		return (rowIndex % pageSize);
	}

	@Override
	public Object getWrappedData() {
		return list;
	}

	@Override
	public boolean isRowAvailable() {
		if(list == null)
			return false;

		int rowIndex = getRowIndex();
		if(rowIndex >=0 && rowIndex < list.size())
			return true;
		else
			return false;
	}
	

	@Override
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public void setWrappedData(Object list) {
		this.list = (List) list;
	}

}
