package com.example.gateway.filter;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.example.gateway.filter.CustomLocalGatewayFilterFactory.Config;

import reactor.core.publisher.Mono;

/**
 * 自定义局部过滤器工厂继承AbstractNameValueGatewayFilterFactory或AbstractGatewayFilterFactory简化开发，它们都实现了GatewayFilterFactory
 * 
 * @author zjy
 *
 */
@Component
public class CustomLocalGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

	private static final Log log = LogFactory.getLog(CustomLocalGatewayFilterFactory.class);

	private static final String TIME_BEGIN = "timeBegin";
	private static final String KEY = "active";

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList(KEY);
	}

	public CustomLocalGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			exchange.getAttributes().put(TIME_BEGIN, System.currentTimeMillis());
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				Long startTime = exchange.getAttribute(TIME_BEGIN);
				if (startTime != null) {
					long costTime = System.currentTimeMillis() - startTime;
					StringBuilder sb = new StringBuilder(exchange.getAttributes().get(GATEWAY_REQUEST_URL_ATTR).toString()).append(": ")
							.append(costTime).append("ms");
					if (config.getActive()) {
						exchange.getResponse().getHeaders().set("request-cost-time", String.valueOf(costTime));
						log.info(sb.toString());
					}
				}
			}));
		};
	}

	public static class Config {

		private boolean active;

		public boolean getActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

	}
}
