package com.haobai.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HttpUtils {

	@Autowired
	private CloseableHttpClient httpClient;

	@Autowired
	private RequestConfig config;

	/**
	 * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResult doGet(String url) throws Exception {
		// 声明 http get 请求
		HttpGet httpGet = new HttpGet(url);

		// 装载配置信息
		httpGet.setConfig(config);

		// 发起请求
		CloseableHttpResponse response = this.httpClient.execute(httpGet);

		return new HttpResult(response.getStatusLine().getStatusCode(),
				EntityUtils.toString(response.getEntity(), "UTF-8"));
		/*
		 * // 判断状态码是否为200 if (response.getStatusLine().getStatusCode() == 200) { //
		 * 返回响应体的内容 return EntityUtils.toString(response.getEntity(), "UTF-8"); } return
		 * null;
		 */
	}

	/**
	 * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResult doGet(String url, Map<String, Object> map) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(url);

		if (map != null) {
			// 遍历map,拼接请求参数
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}

		// 调用不带参数的get请求
		return this.doGet(uriBuilder.build().toString());

	}

	/**
	 * 带参数的post请求 map转form表单
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPostByForm(String url, Map<String, Object> map) throws Exception {
		// 声明httpPost请求
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息
		httpPost.setConfig(config);

		// 判断map是否为空，不为空则进行遍历，封装from表单对象
		if (map != null) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			// 构造from表单对象
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

			// 把表单放到post里
			httpPost.setEntity(urlEncodedFormEntity);
		}

		// 发起请求
		CloseableHttpResponse response = this.httpClient.execute(httpPost);
		System.out.println(response.getHeaders(HttpHeaders.CONNECTION).toString());
		Header[] header = response.getHeaders(HttpHeaders.CONNECTION);
		return new HttpResult(response.getStatusLine().getStatusCode(),
				EntityUtils.toString(response.getEntity(), "UTF-8"));
	}

	/**
	 * 带参数的post请求 map转json
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPostByJson(String url, Map<String, Object> map) throws Exception {
		// 声明httpPost请求
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息
		httpPost.setConfig(config);

		// 判断map是否为空，不为空则进行遍历，封装json字符串
		if (map != null) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(list);
			StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			// 把json放到post里
			httpPost.setEntity(entity);
		}

		// 发起请求
		CloseableHttpResponse response = this.httpClient.execute(httpPost);
		return new HttpResult(response.getStatusLine().getStatusCode(),
				EntityUtils.toString(response.getEntity(), "UTF-8"));
	}

	/**
	 * 带参数的post请求 object转json
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPostByObject(String url, Object obj) throws Exception {
		// 声明httpPost请求
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息
		httpPost.setConfig(config);

		// 判断object是否为空，不为空则进行遍历，封装json字符串
		if (obj != null) {
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(obj);
			StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			// 把json放到post里
			httpPost.setEntity(entity);
		}

		// 发起请求
		CloseableHttpResponse response = this.httpClient.execute(httpPost);
		return new HttpResult(response.getStatusLine().getStatusCode(),
				EntityUtils.toString(response.getEntity(), "UTF-8"));
	}

	/**
	 * 不带参数post请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPost(String url) throws Exception {
		return this.doPostByForm(url, null);
	}
}
