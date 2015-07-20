package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.UserDto;

import org.primefaces.model.SelectableDataModel;


public class UserListModel extends ListDataModel<UserDto> implements SelectableDataModel<UserDto> {

	public UserListModel(List<UserDto> userList){
		super(userList);
	}

	@Override
	public UserDto getRowData(String rowKey) {
		String userId = rowKey;
		List<UserDto> userList = (List<UserDto>)getWrappedData();
		for (UserDto userDto : userList) {
			if(userDto.getUserName().equals(userId)){
				return userDto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(UserDto userDto) {
		return userDto.getUserName();
	}

}
