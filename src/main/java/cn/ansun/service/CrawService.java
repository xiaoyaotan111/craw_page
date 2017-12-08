package cn.ansun.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.ansun.dao.LinkPageDataRepository;
import cn.ansun.domain.LinkPageData;
import cn.ansun.domain.LinkPageMongoData;
import cn.ansun.domain.Parameter;
import cn.ansun.domain.ParameterException;
import cn.ansun.util.HttpHelper;

@Service
public class CrawService {
	
	@Autowired
	private LinkPageDataRepository linkPageDataRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * @param parameter
	 * @return
	 */
	public void extract(Parameter rule) {

		// 进行对rule的必要校验
		validateRule(rule);

		//用mysql实体保存
		/*List<LinkPageData> datas = new ArrayList<LinkPageData>();
		LinkPageData data = null;*/
		
		//用mongo实体保存
		List<LinkPageMongoData> datas = new ArrayList<LinkPageMongoData>();
		LinkPageMongoData data = null;
		try {
			/**
			 * 解析parameter
			 */
			String url = rule.getUrl();
			String[] params = rule.getParams();
			String[] values = rule.getValues();
			String resultTagName = rule.getResultTagName();
			int type = rule.getType();
			int requestType = rule.getRequestMoethod();

			//初始化获取页面内容
			Connection conn = Jsoup.connect(url);
			// 设置查询参数
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					conn.data(params[i], values[i]);
				}
			}
			//初始化请求数据
			Document doc=HttpHelper.dosend(conn, requestType);
			//获取分页
			Elements pageEls = new Elements();
			pageEls=doc.select("div[class=con-page search_page_link]").get(0).getElementsByTag("a");
			
			
			//分页获取页面数据
			String newUrl="";
			for(Element pageLink : pageEls){
				String midleUrl=pageLink.attr("href");
				if("下一页".equals(pageLink.text())){
					midleUrl="/beijing/baoshijie/sn_v9/i1/";
				}
				newUrl="https://www.xin.com/"+midleUrl+"?channel=a49b117c44837d110753e751863f53";
				Connection newConn = Jsoup.connect(newUrl);
				Document newDoc=HttpHelper.dosend(newConn, requestType);
				
				// 处理返回数据
				Elements results = new Elements();
				switch (type) {
				case Parameter.CLASS:
					results = newDoc.getElementsByClass(resultTagName);
					break;
				case Parameter.ID:
					Element result = newDoc.getElementById(resultTagName);
					results.add(result);
					break;
				case Parameter.SELECTION:
					results = newDoc.select(resultTagName);
					break;
				default:
					// 当resultTagName为空时默认去body标签
					if (StringUtil.isBlank(resultTagName)) {
						results = newDoc.select("div[class=err-show change-top change]");
						if(results!=null && results.size()>0){
							System.out.println("没有找到您想要的爱车，建议您减少筛选条件试试。");
							break;
						}
						results = newDoc.select("div[class=_list-con list-con clearfix ab_carlist]");
					}
				}

				for (Element result : results) {
					Elements links = result.getElementsByTag("li");
					for (Element link : links) {
						// 必要的筛选
						Elements pads =link.select("div[class=pad]");
	                    for(Element pad : pads){
	                    	Element h2=pad.getElementsByTag("h2").get(0);
	                    	Element span=pad.getElementsByTag("span").get(0);
	                    	Element p=pad.getElementsByTag("p").get(0);
	                    	
	                    	data = new LinkPageMongoData();
	                    	data.setLinkHref(h2.childNodes().get(0).attr("href").substring(2, h2.childNodes().get(0).attr("href").length()));
	                    	data.setLinkTitle(h2.text());
	                    	String spanInfo=span.text();
	                    	                    	
	                    	data.setRegister(spanInfo.substring(0, spanInfo.indexOf("｜")));
	                    	data.setDirveMiles(spanInfo.substring(spanInfo.indexOf("｜")+1, spanInfo.length()));
	                    	String pinfo=p.text();
	                    	if(pinfo.contains("首付")){
	                    		data.setTotalMoney(pinfo.substring(0, pinfo.indexOf("首")));
		                    	data.setFirstMoney(pinfo.substring(pinfo.indexOf("首"), pinfo.length()));
	                    	}else{
	                    		data.setTotalMoney(pinfo.substring(0, pinfo.length()));
		                    	data.setFirstMoney("不支持首付");
	                    	}
	                    	
	                    	datas.add(data);
	                    }
					}
				}
			}
			
			for (LinkPageMongoData pageData : datas) {
				//保存到mysql
				//linkPageDataRepository.save(pageData);
				//保存到mongo
				mongoTemplate.insert(pageData, "craw_page");
			}
		

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 对传入的参数进行必要的校验
	 */
	private static void validateRule(Parameter rule) {
		String url = rule.getUrl();
		if (StringUtil.isBlank(url)) {
			throw new ParameterException("url不能为空！");
		}
		/*if (!url.startsWith("http://")) {
			throw new RuleException("url的格式不正确！");
		}*/

		if (rule.getParams() != null && rule.getValues() != null) {
			if (rule.getParams().length != rule.getValues().length) {
				throw new ParameterException("参数的键值对个数不匹配！");
			}
		}

	}

}
