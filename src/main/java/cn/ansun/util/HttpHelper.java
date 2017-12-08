package cn.ansun.util;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import cn.ansun.domain.Parameter;

public class HttpHelper {

	public static Document dosend(Connection conn, int requestType) throws IOException {
		// 设置请求类型
		Document doc = null;
		switch (requestType) {
		case Parameter.GET:
			doc = conn.timeout(100000).get();
			break;
		case Parameter.POST:
			doc = conn.timeout(100000).post();
			break;
		}
		return doc;
	}

}
