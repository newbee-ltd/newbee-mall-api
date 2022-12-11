package ltd.newbee.mall.config;

import com.alipay.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author : [wangminan]
 * @description : [支付宝的配置文件]
 */
@Configuration
@PropertySource("classpath:alipay-sandbox.properties") //读取配置文件
public class AlipayClientConfig {

    @Resource
    private Environment config; // 依赖注入

    @Bean
    public AlipayClient alipayClient() throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        //设置网关地址
        alipayConfig.setServerUrl(config.getProperty("alipay.gateway-url"));
        //设置应用ID
        alipayConfig.setAppId(config.getProperty("alipay.app-id"));
        //设置应用私钥
        alipayConfig.setPrivateKey(config.getProperty("alipay.merchant-private-key"));
        //设置请求格式，固定值json
        alipayConfig.setFormat(AlipayConstants.FORMAT_JSON);
        //设置字符集
        alipayConfig.setCharset(AlipayConstants.CHARSET_UTF8);
        //设置签名类型
        alipayConfig.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
        //设置支付宝公钥
        alipayConfig.setAlipayPublicKey(config.getProperty("alipay.alipay-public-key"));
        //实例化客户端
        return new DefaultAlipayClient(alipayConfig);
    }
}
