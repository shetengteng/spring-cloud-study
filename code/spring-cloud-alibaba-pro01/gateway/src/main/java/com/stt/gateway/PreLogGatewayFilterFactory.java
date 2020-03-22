package com.stt.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// 注意必须要使用GatewayFilterFactory结尾
@Slf4j
@Component
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory{

	@Override
	public GatewayFilter apply(NameValueConfig config) {
		GatewayFilter gatewayFilter = new GatewayFilter() {
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
				// 对日志的操作需要在匿名内部类中
				log.info("记录日志,配置参数：{}，{}",config.getName(),config.getValue());
				// 可修改请求
				ServerHttpRequest request = exchange.getRequest().mutate().build();
				ServerWebExchange modifiedExchange = exchange.mutate().request(request).build();
				// 交给下一个过滤器
				return chain.filter(modifiedExchange);
			}
		};

		return  new OrderedGatewayFilter(gatewayFilter,1000);
	}

}
