package com.pramati.ts.oauth.server.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FrameworkEndpoint
public class LogoutController {
	
	
	private AuthorizationServerEndpointsConfigurer endpoints = new AuthorizationServerEndpointsConfigurer();
    
    @Bean
	public AuthorizationServerTokenServices defaultAuthorizationServerTokenServices() {
		return endpoints.getDefaultAuthorizationServerTokenServices();
	}

	public AuthorizationServerEndpointsConfigurer getEndpointsConfigurer() {
		if (!endpoints.isTokenServicesOverride()) {
			endpoints.tokenServices(defaultAuthorizationServerTokenServices());
		}
		return endpoints;
	}
	
	@Bean
	public ConsumerTokenServices consumerTokenServices() throws Exception {
		return getEndpointsConfigurer().getConsumerTokenServices();
	}
    
 
    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public void revokeToken(HttpServletRequest request) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")){
            String tokenId = authorization.substring("Bearer".length()+1);
            consumerTokenServices().revokeToken(tokenId);
        }
    }
}