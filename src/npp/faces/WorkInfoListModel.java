package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.WorkInfoDto;

import org.primefaces.model.SelectableDataModel;


public class WorkInfoListModel extends ListDataModel<WorkInfoDto> implements SelectableDataModel<WorkInfoDto> {

	public WorkInfoListModel(List<WorkInfoDto> employeeList){
		super(employeeList);
	}

	@Override
	public WorkInfoDto getRowData(String rowKey) {
		String employeeId = rowKey;
		List<WorkInfoDto> employeeList = (List<WorkInfoDto>)getWrappedData();
		for (WorkInfoDto employeeDto : employeeList) {
			if(String.valueOf(employeeDto.getId()).equals(employeeId)){
				return employeeDto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(WorkInfoDto workInfoDto) {
		return workInfoDto.getId();
	}

}
