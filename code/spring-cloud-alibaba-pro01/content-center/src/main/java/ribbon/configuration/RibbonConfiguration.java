package ribbon.configuration;

import com.netflix.loadbalancer.IRule;
import com.stt.contentcenter.configuration.NacosSameClusterWeightedRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 配置随机规则
// 为什么要单独在Application的包外部配置，原因是防止注入后，更改其他通用配置
@Configuration
public class RibbonConfiguration {

	@Bean
	public IRule ribbonRule(){
		return new NacosSameClusterWeightedRule();
//		return new RandomRule();
	}

//	@Bean
//	public IPing ping(){
//		return new PingUrl();
//	}
}
