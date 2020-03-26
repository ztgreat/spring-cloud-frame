package com.cloud.gateway.decorator;


import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.cloud.nacos.ribbon.NacosServerList;

import java.util.List;

public class NacosServerListDecorator extends NacosServerList {


    public NacosServerListDecorator(NacosDiscoveryProperties discoveryProperties) {
        super(discoveryProperties);
    }

    @Override
    public List<NacosServer> getInitialListOfServers() {
        List<NacosServer> servers = super.getInitialListOfServers();

        filter(servers);

        return servers;
    }

    @Override
    public List<NacosServer> getUpdatedListOfServers() {
        List<NacosServer> servers = super.getUpdatedListOfServers();

        filter(servers);

        return servers;
    }

    private void filter(List<NacosServer> servers) {

    }


}