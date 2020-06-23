package cn.hot.util.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliSmsUtil {
    private static Logger log = LoggerFactory.getLogger( AliSmsUtil.class );

    static final String product = "Dysmsapi";
    static final String domain = "dysmsapi.aliyuncs.com";

    public static SendSmsResponse sendSms(String accessKeyId, String accessKeySecret,String cellphone, String signName, String templateCode, String templateParam) throws ClientException {
        System.setProperty("sun.net.service.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.service.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",  accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(cellphone);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam(templateParam);
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        log.info("发送消息结束.............................................");
        return sendSmsResponse;
    }
}
