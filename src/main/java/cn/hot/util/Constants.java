package cn.hot.util;

public interface Constants {

    String ENDTYPE_STARTING = "2";
    String ENDTYPE_STARTED  = "0";
    String ENDTYPE_ENDED    = "1";

    int DEFAULT_CHARGE_TIME = 0; //充满自停
    int DEFAULT_PREFERENTIAL_TIME = 300; //默认优惠时长

    //用户类型
    String USER_FROM_WECHAT  = "1";//微信
    String USER_FROM_VIRTUAL = "2";//虚拟
    String USER_FROM_ALIPAY  = "3";//支付宝

    int LOGIN_TIMEOUT = 1800; // 登录超时时间，默认1800秒

    String DEVICE_KEY_PRE = "Device_";
    String CUSTOMER_KEY_PRE = "Customer_";
    String GATE_KEY_PRE = "Gate_";
    String PROJECT_INFO = "Project_";

    //接口
    String MERCHANT_INFO_KEY_PRE = "MerchantInfo";
    String DEVICE_INFO_KEY_PRE = "DeviceInfo";
    String GATE_INFO_KEY_PRE = "GateInfo";

    String WECHAT_SETTING_KEY_PRE = "Setting";
    String ALIINFO_SETTING_KEY_PRE="AliInfo";


    static String getKey(String key, Object suffix){
        StringBuffer key_ = new StringBuffer(key);
        key_.append(suffix);
        return key_.toString();
    }

}
