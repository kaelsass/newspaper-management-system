package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.DepartmentDto;

import org.primefaces.model.SelectableDataModel;


public class DepartmentListModel extends ListDataModel<DepartmentDto> implements
		SelectableDataModel<DepartmentDto> {

	public DepartmentListModel(List<DepartmentDto> departmentList) {
		super(departmentList);
	}

	@Override
	public DepartmentDto getRowData(String rowKey) {
		String departmentSeq = rowKey;
		List<DepartmentDto> departmentList = (List<DepartmentDto>) getWrappedData();
		for (DepartmentDto departmentDto : departmentList) {
			if (departmentSeq.equals(departmentDto.getSeq() + "")) {
				return departmentDto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(DepartmentDto employeeDto) {
		return employeeDto.getSeq()+"";
	}

}
