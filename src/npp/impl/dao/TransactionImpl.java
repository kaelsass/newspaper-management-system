package npp.impl.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;

import npp.spec.dao.Transaction;
import npp.utils.DBHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateful
@RequestScoped
public class TransactionImpl implements Transaction, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7828715984476316619L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

//	private final String ID = "stms140827";
//
//	@Inject
//	private ResourceHolder resourceHolder;
	private Connection connection;

	@Override
	public void begin() throws TransactionException {
		if(connection == null){
			try {
				//This usage is irregular. Only on the STM. Normaly use 'getConnection()'.
//				connection = resourceHolder.getResource().getConnection(ID, ID);
				connection = DBHelper.getConnection();
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				throw new TransactionException(e);
			}
		}
	}

	@Override
	public void commit() throws TransactionException {
		try {
			if(connection != null){
				connection.commit();
			}
		} catch (SQLException e) {
			throw new TransactionException(e);
		}
	}

	@Override
	public void rollback() throws TransactionException {
		try {
			if(connection != null){
				connection.rollback();
			}
		} catch (SQLException e) {
			throw new TransactionException(e);
		}
	}

	@Override
	public boolean isActive() throws TransactionException {
		try {
			if (connection != null && connection.isClosed()) {
				connection = null;
				return false;
			}
		} catch (SQLException e) {
			throw new TransactionException(e);
		}

		return true;
	}

	@PreDestroy
	public void preDestory(){
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			}
			connection = null;
		}
	}

	@Override
	public <T> T getResource(Class<T> klass) {
		if (klass.equals(Connection.class)) {
			@SuppressWarnings("unchecked")
			T con = (T)connection;

			return con;
		}

		throw new IllegalArgumentException();
	}

}
