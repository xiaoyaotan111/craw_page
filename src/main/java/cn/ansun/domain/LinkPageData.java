package cn.ansun.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "craw_page")
public class LinkPageData{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	//链接
	@Column(name = "link_href")
	private String linkHref;
	//标题
	@Column(name = "link_title")
	private String linkTitle;
    //全款
	@Column(name = "total_money")
	private String totalMoney;
	//首付
	@Column(name = "first_money")
	private String firstMoney;
	//上牌时间
	@Column(name = "register")
	private String register;
	//行驶里程
	@Column(name = "drive_miles")
	private String dirveMiles;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLinkHref() {
		return linkHref;
	}
	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
	}
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getFirstMoney() {
		return firstMoney;
	}
	public void setFirstMoney(String firstMoney) {
		this.firstMoney = firstMoney;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public String getDirveMiles() {
		return dirveMiles;
	}
	public void setDirveMiles(String dirveMiles) {
		this.dirveMiles = dirveMiles;
	}
	
	
	
}

