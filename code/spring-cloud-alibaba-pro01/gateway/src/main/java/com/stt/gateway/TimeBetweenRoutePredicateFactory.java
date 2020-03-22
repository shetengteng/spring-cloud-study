package com.stt.gateway;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
// 注意命名必须是RoutePredicateFactory结尾
public class TimeBetweenRoutePredicateFactory
		extends AbstractRoutePredicateFactory<TimeBetweenConfig> {

	public TimeBetweenRoutePredicateFactory(){
		super(TimeBetweenConfig.class);
	}

	@Override
	public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
		// 返回断言，是否满足路由转发条件
		return new Predicate<ServerWebExchange>() {
			@Override
			public boolean test(ServerWebExchange serverWebExchange) {
				LocalTime now = LocalTime.now();
				return now.isAfter(config.getStart()) && now.isBefore(config.getEnd());
			}
		};
	}

	/**
	 * 用于控制配置类和配置文件的映射关系
	 * 配置类的字段顺序
	 * @return
	 */
	@Override
	public List<String> shortcutFieldOrder() {
		// TimeBetweenConfig内的字段名称顺序和yml中配置赋值的顺序一致
		return Arrays.asList("start","end");
	}
}
