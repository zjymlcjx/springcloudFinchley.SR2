package com.haobai.base.configurations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;

@Order(1)
@WebFilter(urlPatterns = "/*", filterName = "corsFilter")
public class CrossFilter implements Filter {
	private static final boolean debug = true;
	private FilterConfig filterConfig = null;

	public CrossFilter() {
		super();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			if (debug) {
				log("CrossFilter:Initializing filter");
			}
		}
	}

	@Override
	public String toString() {
		if (filterConfig == null) {
			return ("CrossFilter()");
		}
		StringBuffer sb = new StringBuffer("CrossFilter(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (debug) {
			log("CrossFilter:doFilter()");
		}
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;

			String origin = httpRequest.getHeader("Origin");
			if (origin == null) {
				httpResponse.addHeader("Access-Control-Allow-Origin", "*");
			} else {
				httpResponse.addHeader("Access-Control-Allow-Origin", origin);
			}
			httpResponse.addHeader("Access-Control-Allow-Headers",
					"Origin, x-requested-with, Content-Type, Accept,X-Cookie");
			httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
			httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");
			if (httpRequest.getMethod().equals("OPTIONS")) {
				httpResponse.setStatus(HttpServletResponse.SC_OK);
				return;
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public void destroy() {
	}

	public static String getStackTrace(Throwable t) {
		String stackTrace = null;
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (Exception ex) {
		}
		return stackTrace;
	}

	public void log(String msg) {
		filterConfig.getServletContext().log(msg);
	}
}