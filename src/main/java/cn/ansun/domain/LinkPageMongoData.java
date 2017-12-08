package cn.ansun.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="craw_page")
public class LinkPageMongoData{

	//链接
	private String linkHref;
	//标题
	private String linkTitle;
    //全款
	private String totalMoney;
	//首付
	private String firstMoney;
	//上牌时间
	private String register;
	//行驶里程
	private String dirveMiles;
	
	
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

