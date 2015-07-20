package npp.spec.manager;

import java.io.IOException;

import npp.dto.UserDto;


public interface SessionManager {
	public UserDto getLoginUser() throws IOException;
	public void redirect(String url)  throws IOException;
	public void addGlobalMessageFatal(String summary, String detail);
	public void addGlobalMessageWarn(String summary, String detail);
	public void addGlobalMessageInfo(String summary, String detail);
	public String getPhotoPath() throws IOException;
	public String getThumbnailPath() throws IOException;
	public void clear();

}
