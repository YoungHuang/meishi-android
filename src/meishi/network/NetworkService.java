package meishi.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;

public class NetworkService {
	private static final String TAG = "NetworkService";
	
	public static String enc = "UTF-8";
	
	public static final String hostUrl = "http://10.60.4.66:8080/meishi";
	public static final String shopLogoUrl = hostUrl + "/shop/logo/";
	public static final String dishListUrl = hostUrl + "/dish/listByDishCategoryId";
	public static final String submitOrderUrl = hostUrl + "/order/submit";
	
	// 检查当前网络是否已经连接
	public static boolean isNetworkActive(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
		}
		
		return false;
	}
	
	//  从一个URL取得一个图片
	public static BitmapDrawable getImageFromUrl(String urlString) {
		Log.d(TAG, urlString);
		BitmapDrawable icon = null;
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream input = conn.getInputStream();
			icon = new BitmapDrawable(input);
			conn.disconnect();
		} catch (Exception e) {
			Log.e(TAG, "getImageFromUrl", e);
		}
		
		return icon;
	}
	
	/**
	 * 发送GET请求
	 * @throws Exception 
	 */
	public static <T> T getForObject(String url, Class<T> responseType, Map<String, String> urlVariables) throws IOException, ResponseException {
		Gson gson = new Gson();
		byte[] data = sendGetRequest(url, urlVariables);
		String json = new String(data, enc);
		T t = gson.fromJson(json, responseType);
		return t;
	}
	
	/**
	 * 发送POST请求
	 * @throws Exception 
	 */
	public static <T> T postForObject(String url, Object request, Class<T> responseType) throws IOException, ResponseException {
		Gson gson = new Gson();
		byte[] data = sendPostRequest(url, gson.toJson(request), enc);
		String json = new String(data, enc);
		T t = gson.fromJson(json, responseType);
		return t;
	}
	
	public static <T> List<T> getForList(String url, Type type, Map<String, String> urlVariables) throws IOException, ResponseException {
		byte[] data = sendGetRequest(url, urlVariables);
		String json = new String(data, enc);
		Gson gson = new Gson();
		List<T> list = gson.fromJson(json, type);
		return list;
	}
	
	/**
	 * 发送GET请求
	 * @throws IOException 
	 * @throws ResponseException 
	 */
	public static byte[] sendGetRequest(String path, Map<String, String> params) throws IOException, ResponseException {
		Log.d(TAG, "sendGetRequest: " + path);
		StringBuilder sb = new StringBuilder(path);
		if(params!=null && !params.isEmpty()){
			sb.append("?");
			for(Map.Entry<String, String> entry : params.entrySet()){
				sb.append(entry.getKey()).append('=')
				  .append(URLEncoder.encode(entry.getValue(), enc)).append('&');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		URL url = new URL(sb.toString());
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestProperty("Content-Type", "application/json");
		InputStream inStream = conn.getInputStream();//通过输入流获取数据
		byte[] data;
		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
			data = readInputStream(inStream);

			return data;
		}
		data = readInputStream(inStream);
		ResponseException ex = contructResponseException(data);
		throw ex;
	}
	
	private static ResponseException contructResponseException(byte[] data) throws IOException, ResponseException {
		ResponseException ex = new ResponseException();
		String json = new String(data, enc);
		Gson gson = new Gson();
		ResponseMessage responseMessage = gson.fromJson(json, ResponseMessage.class);
		ex.setResponseMessage(responseMessage);
		return ex;
	}
	
	/**
	 * 发送POST请求
	 */
	public static byte[] sendPostRequest(String path, Map<String, String> params, String enc) throws IOException, ResponseException {
		Log.d(TAG, "sendPostRequest: " + path);
		StringBuilder sb = new StringBuilder();
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				sb.append(entry.getKey()).append('=')
				  .append(URLEncoder.encode(entry.getValue(), enc)).append('&');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		byte[] entitydata = sb.toString().getBytes();//得到实体的二进制数据
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entitydata);
		outStream.flush();
		outStream.close();
		byte[] data;
		InputStream inStream = conn.getInputStream();//通过输入流获取数据
		if(conn.getResponseCode() == 200){
			data = readInputStream(inStream);

			return data;
		}
		data = readInputStream(inStream);
		ResponseException ex = contructResponseException(data);
		throw ex;
	}
	
	/**
	 * 发送POST请求
	 */
	public static byte[] sendPostRequest(String path, String json, String enc) throws IOException, ResponseException {
		Log.d(TAG, "sendPostRequest: " + path);

		byte[] entitydata = json.getBytes();//得到实体的二进制数据
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entitydata);
		outStream.flush();
		outStream.close();
		byte[] data;
		InputStream inStream = conn.getInputStream();//通过输入流获取数据
		if(conn.getResponseCode() == 200){
			data = readInputStream(inStream);

			return data;
		}
		data = readInputStream(inStream);
		ResponseException ex = contructResponseException(data);
		throw ex;
	}
	
	/**
	 * 从输入流中获取数据
	 * @param inStream 输入流
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	private static byte[] readInputStream(InputStream inStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}
