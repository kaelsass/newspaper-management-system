package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.IssueDto;
import npp.spec.dao.IssueDao;
import npp.spec.dao.Transaction;
import npp.spec.service.IssueService;


public class IssueServiceImpl implements IssueService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1069655030868538783L;
	private Transaction transaction;
	private IssueDao dao;

	@Inject
	public IssueServiceImpl(Transaction transaction, IssueDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public IssueDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			IssueDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<IssueDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<IssueDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(IssueDto dto) throws IOException {
		try{
			transaction.begin();
			dao.add(transaction, dto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void update(IssueDto dto) throws IOException {
		try{
			transaction.begin();
			dao.update(transaction, dto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void delete(int seq) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, seq);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public IssueDto getNextIssue(int seq) throws IOException {
			try{
				transaction.begin();
				IssueDto dto = dao.getNextIssueByNewspaper(transaction, seq);
				transaction.commit();
				return dto;
			}catch (IOException e){
				transaction.rollback();
				throw e;
			}
	}
}
