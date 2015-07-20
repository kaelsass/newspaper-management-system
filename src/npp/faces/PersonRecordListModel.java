package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.PersonRecordDto;

import org.primefaces.model.SelectableDataModel;


public class PersonRecordListModel extends ListDataModel<PersonRecordDto> implements
		SelectableDataModel<PersonRecordDto> {

	public PersonRecordListModel(List<PersonRecordDto> list) {
		super(list);
	}

	@Override
	public PersonRecordDto getRowData(String rowKey) {
		String departmentSeq = rowKey;
		List<PersonRecordDto> list = (List<PersonRecordDto>) getWrappedData();
		for (PersonRecordDto dto : list) {
			if (departmentSeq.equals(dto.getEmployee().getId())) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(PersonRecordDto employeeDto) {
		return employeeDto.getEmployee().getId();
	}

}
