package top.caoyd.www.common.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import top.caoyd.www.common.result.RobotResult;

import com.alibaba.fastjson.JSON;
/**
 * 图灵机器人相关工具类
 * @author Lenovo
 *
 */
public class RobotUtil {
	public static RobotResult sendMessage(String info) throws Exception {
		HttpPost httpPost = new HttpPost(
				"http://www.tuling123.com/openapi/api?key=9a8e6dd0ae9e40d59eaf4c2d1875e6e2&info="
						+ info);

		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
		String responseResult = EntityUtils.toString(httpResponse.getEntity(),
				"utf-8");
		return new RobotResult(JSON.parseObject(responseResult).getString(
				"code"), JSON.parseObject(responseResult).getString("text"),
				JSON.parseObject(responseResult).getString("url"));
	}

	public static void main(String[] args) {
		try {
			System.out.println(sendMessage("0"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
