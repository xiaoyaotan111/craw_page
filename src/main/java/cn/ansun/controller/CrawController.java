package cn.ansun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ansun.domain.Parameter;
import cn.ansun.service.CrawService;

@Controller
@RequestMapping("/craw")
public class CrawController{

	@Autowired 
	private CrawService crawService;
	
	@RequestMapping(value = "/test", produces = "application/json; charset=utf-8")
	public @ResponseBody String crawPage() throws Exception {
		Parameter rule = new Parameter(
				"https://www.xin.com/beijing/baoshijie/sn_v9/?channel=a49b117c44837d110753e751863f53", null, null, null,
				-1, Parameter.GET);
		crawService.extract(rule);
		return "0000";
	}

	
	
	
	/*public static void main(String[] args) {
		Parameter rule = new Parameter(
				"https://www.xin.com/beijing/baoshijie/sn_v9/?channel=a49b117c44837d110753e751863f53", null, null, null,
				-1, Parameter.GET);
		List<LinkPageData> extracts = CrawService.extract(rule);
		printf(extracts);
	}
	
	public void printf(List<LinkPageData> datas) {
		System.out.println("total==" + datas.size());
		for (LinkPageData data : datas) {
			System.out.println(data.getLinkTitle());
			System.out.println(data.getLinkHref());
			System.out.println(data.getDirveMiles());
			System.out.println(data.getRegister());
			System.out.println(data.getTotalMoney());
			System.out.println(data.getFirstMoney());
			System.out.println("***********************************");

		}
	}
	*/

}
