package npp.dto;

import java.util.ArrayList;
import java.util.List;

public class InventoryDto {
	private IssueDto issue;
	private List<SubscriberDto> subscribers;
	private List<NonSubscriberDto> nonSubscribers;
	private List<RetailDto> retails;
	private List<ReturnDto> returns;
	private List<AdDto> ads;


	//You must declared default constructor for Framework.
	public InventoryDto(){
		init(new IssueDto(), new ArrayList<SubscriberDto>(), new ArrayList<NonSubscriberDto>(), new ArrayList<RetailDto>(), new ArrayList<ReturnDto>(),
				new ArrayList<AdDto>());
	}

	public InventoryDto(IssueDto issue, List<SubscriberDto> subscribers, List<NonSubscriberDto> nonSubscribers, List<RetailDto> retails,
			List<ReturnDto> returns, List<AdDto> ads){
		init(issue, subscribers, nonSubscribers, retails, returns, ads);
	}

	public InventoryDto(InventoryDto dto) {
		init(dto.getIssue(), dto.getSubscribers(), dto.getNonSubscribers(), dto.getRetails(), dto.getReturns(), dto.getAds());
	}

	private void init(IssueDto issue, List<SubscriberDto> subscribers, List<NonSubscriberDto> nonSubscribers, List<RetailDto> retails,
			List<ReturnDto> returns, List<AdDto> ads){
		this.issue = issue;
		this.subscribers = subscribers;
		this.nonSubscribers = nonSubscribers;
		this.retails = retails;
		this.returns = returns;
		this.ads = ads;
	}
	public IssueDto getIssue() {
		return issue;
	}

	public void setIssue(IssueDto issue) {
		this.issue = issue;
	}

	public List<SubscriberDto> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<SubscriberDto> subscribers) {
		this.subscribers = subscribers;
	}

	public List<NonSubscriberDto> getNonSubscribers() {
		return nonSubscribers;
	}

	public void setNonSubscribers(List<NonSubscriberDto> nonSubscribers) {
		this.nonSubscribers = nonSubscribers;
	}

	public List<RetailDto> getRetails() {
		return retails;
	}

	public void setRetails(List<RetailDto> retails) {
		this.retails = retails;
	}

	public List<ReturnDto> getReturns() {
		return returns;
	}

	public void setReturns(List<ReturnDto> returns) {
		this.returns = returns;
	}

	public List<AdDto> getAds() {
		return ads;
	}

	public void setAds(List<AdDto> ads) {
		this.ads = ads;
	}
	public int getSubscriberQuantity()
	{
		int sum = 0;
		for(SubscriberDto dto : subscribers)
		{
			sum += dto.getQuantity();
		}
		return sum;
	}
	public int getNonSubscriberQuantity()
	{
		int sum = 0;
		for(NonSubscriberDto dto : nonSubscribers)
		{
			sum += dto.getQuantity();
		}
		return sum;
	}
	public int getRetailQuantity()
	{
		int sum = 0;
		for(RetailDto dto : retails)
		{
			sum += dto.getQuantity();
		}
		return sum;
	}
	public int getReturnQuantity()
	{
		int sum = 0;
		for(ReturnDto dto : returns)
		{
			sum += dto.getQuantity();
		}
		return sum;
	}
	public int getAdQuantity()
	{
		return ads.size();
	}
	public int getRemainQuantity()
	{
//		return issue.getQuantity() - getSubscriberQuantity() - getNonSubscriberQuantity() - getRetailQuantity() + getReturnQuantity();
		return issue.getQuantity() - getSalesQuantity();
	}

	public int getSalesQuantity() {
		return getSubscriberQuantity() + getNonSubscriberQuantity() + getRetailQuantity();
	}

	public double getSalesMoney()
	{
		return getSubscriberMoney() + getRetailMoney() + getAdMoney();
	}

	public double getAdMoney() {
		double total = 0;
		for(AdDto dto : ads)
		{
			total += dto.getUnitPrice() * dto.getDiscount();
		}
		return total;
	}

	public double getRetailMoney() {
		double total = 0;
		for(RetailDto dto : retails)
		{
			total += dto.getPayables();
		}
		return total;
	}

	public double getSubscriberMoney() {
		double total = 0;
		for(SubscriberDto dto : subscribers)
		{
			total += dto.getNewspaper().getIssuePrice() * dto.getQuantity() * dto.getDiscount();
		}
		return total;
	}
}
