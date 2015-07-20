package npp.spec.service;

import java.io.IOException;

public interface LoginService {

	public boolean isValid(String username, String password) throws IOException;

	public String getRole(String username) throws IOException;

}
