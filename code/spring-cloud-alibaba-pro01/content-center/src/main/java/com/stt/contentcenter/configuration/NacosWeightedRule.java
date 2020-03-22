package com.stt.contentcenter.configuration;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;

@Slf4j
public class NacosWeightedRule extends AbstractLoadBalancerRule {

	@Autowired
	private NacosDiscoveryProperties nacosDiscoveryProperties;

	@Override
	public void initWithNiwsConfig(IClientConfig iClientConfig) {
		// 读取配置文件并初始化
	}

	@Override
	public Server choose(Object o) {
		BaseLoadBalancer loadBalancer = (BaseLoadBalancer) getLoadBalancer();
		log.info("lb={}",loadBalancer.getName());

		// 要请求的微服务名称
		String name = loadBalancer.getName();

		// 实现负载均衡算法
		// 获取服务相关的API
		NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

		// 通过nacos client 通过基于权重的负载均衡算法，选择一个实例
		try {
			Instance instance = namingService.selectOneHealthyInstance(name);

			log.info("instance = {} port={}",instance,instance.getPort());

			return new NacosServer(instance);

		} catch (NacosException e) {
			e.printStackTrace();
		}

		return null;
	}
}
