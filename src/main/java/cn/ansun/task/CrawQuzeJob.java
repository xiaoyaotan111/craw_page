package cn.ansun.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

import cn.ansun.domain.Parameter;
import cn.ansun.service.CrawService;

@Service
public class CrawQuzeJob {
	@Autowired 
	private CrawService crawService;
	
	private final static String JOB_NAME = "CrawQuzeJob";
	
	private ExecutorService executor;
	
	@PostConstruct
	public void init() {
		System.out.println(JOB_NAME + " init");
	}

	@PreDestroy
	public void destroy() {
		executor.shutdownNow();
		System.out.println(JOB_NAME + " shutdown");
	}
	
    @Scheduled(cron = "*/10 * * * * ?")
	public void crawPage(){
		try {
			System.out.println(JOB_NAME + " -> start");
			long startTime = System.currentTimeMillis();
			if (null == executor || executor.isShutdown()) {
				executor = Executors.newFixedThreadPool(5);
			}
			crawpage();
			executor.shutdown();
			try {
				executor.awaitTermination(10000000L, TimeUnit.MILLISECONDS);
			} catch (Exception ex) {
				System.out.println("异常了，"+ex.getMessage());
			}
			System.out.println(JOB_NAME +" -> end ：" + (System.currentTimeMillis() - startTime) + "毫秒");
		} catch (Exception e) {
			System.out.println("异常了，"+e.getMessage());
		}
	}
	
	private void crawpage(){
		executor.execute(new Runnable() {
			public void run() {
				Parameter rule = new Parameter(
						"https://www.xin.com/beijing/baoshijie/sn_v9/?channel=a49b117c44837d110753e751863f53", null, null, null,
						-1, Parameter.GET);
				crawService.extract(rule);
			}
		});
	}

}
