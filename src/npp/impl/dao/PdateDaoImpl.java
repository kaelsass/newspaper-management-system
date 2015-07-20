package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.PublicationDateDto;
import npp.spec.dao.PdateDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class PdateDaoImpl implements PdateDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<PublicationDateDto> getAllList(
			Transaction transaction, DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			query.setSuffix(ORDER);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);

			resultSet = prepareStatement.executeQuery();

			List<PublicationDateDto> items = new ArrayList<PublicationDateDto>();
			while (resultSet.next()) {
				PublicationDateDto dto = makeDto(transaction,
						resultSet);
				items.add(dto);
			}
			return items;
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}

			if (prepareStatement != null) {
				try {
					prepareStatement.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
	}


	private PublicationDateDto makeDto(Transaction transaction,
			ResultSet resultSet) throws SQLException, IOException {
		int pday = resultSet.getInt(1);
		int ptype = resultSet.getInt(2);

		PublicationDateDto dto = new PublicationDateDto(pday, ptype);
		return dto;
	}

	private static final String ORDER = " order by pday";
	private static final String GET_ALL = "select pday, ptype from n_pdate where 1 = 1 ";

}
