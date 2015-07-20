package npp.spec.dao;

import java.io.IOException;

public interface LoginDao {

	public boolean isValid(Transaction transaction, String username, String password)
			throws IOException;

	public String getRoleByUserName(Transaction transaction, String username) throws IOException;
}
