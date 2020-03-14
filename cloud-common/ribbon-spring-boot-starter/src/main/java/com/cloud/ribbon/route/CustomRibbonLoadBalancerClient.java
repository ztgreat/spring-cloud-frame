package com.cloud.ribbon.route;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration
//@Component
public class CustomRibbonLoadBalancerClient extends RibbonLoadBalancerClient implements LoadBalancerClient {
    public CustomRibbonLoadBalancerClient(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public ServiceInstance choose(String serviceId) {
        return super.choose(serviceId);
    }
}
