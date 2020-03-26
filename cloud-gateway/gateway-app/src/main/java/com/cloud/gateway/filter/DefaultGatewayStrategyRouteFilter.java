package com.cloud.gateway.filter;


import com.cloud.gateway.wrapper.StrategyRouteWrapper;

public class DefaultGatewayStrategyRouteFilter extends AbstractGatewayStrategyRouteFilter {

    private StrategyRouteWrapper strategyRouteWrapper;

    @Override
    public String getVersion() {
        return strategyRouteWrapper.getVersion();
    }
}