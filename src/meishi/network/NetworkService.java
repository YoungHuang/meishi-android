package meishi.network;

import java.util.Map;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class NetworkService {
	private static RestTemplate restTemplate;
	
	static {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
	}
	
	/**
	 * 发送GET请求
	 */
	public static <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) {
		return restTemplate.getForObject(url, responseType, urlVariables);
	}
	
	/**
	 * 发送POST请求
	 */
	public static <T> T postForObject(String url, Object request, Class<T> responseType) {
		return restTemplate.postForObject(url, request, responseType);
	}
}
