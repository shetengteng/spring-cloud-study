package com.stt.contentcenter.configuration;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {

	@Autowired
	private NacosDiscoveryProperties nacosDiscoveryProperties;


	@Override
	public void initWithNiwsConfig(IClientConfig iClientConfig) {

	}

	@Override
	public Server choose(Object o) {
		// 找到指定服务的所有实例 A
		// 找到相同集群的所有实例 B
		// 如果B为空，则使用A
		// 基于权重，返回一个实例

		// 集群名称
		String clusterName = nacosDiscoveryProperties.getClusterName();
		BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
		// 服务名称
		String serverName = loadBalancer.getName();
		// 该服务相关api
		NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

		try {
			// 得到所有健康的节点
			List<Instance> allInstances = namingService.selectInstances(serverName, true);
			// 得到相同集群下的节点
			List<Instance> sameClusterInstances = allInstances.stream()
					.filter(instance -> instance.getClusterName().equals(clusterName))
					.collect(Collectors.toList());

			List<Instance> instancesToUse = sameClusterInstances;
			if(CollectionUtils.isEmpty(sameClusterInstances)){
				instancesToUse=allInstances;
				log.warn("发生跨集群的应用");
			}
			// 获取一个权重最大的实例
			Instance instance = ExtendBalancer.fetchHostByRandomWeight(instancesToUse);

			return new NacosServer(instance);
		} catch (NacosException e) {
			e.printStackTrace();
		}

		return null;
	}
}

// 通过查看selectOneHealthyInstance源码使用
class ExtendBalancer extends Balancer{
	public static Instance fetchHostByRandomWeight(List<Instance> hosts){
		return getHostByRandomWeight(hosts);
	}
}