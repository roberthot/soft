package cn.hot.restApi;

/**
 * 错误类对接安卓返回头文件
 */
public enum ErrorEnum {
    LOGIN_SUCCESS("1","登录成功"),
    LOGIN_ACCOUNT_ERROR("0","账户错误"),
    LOGIN_PASS_ERROR("2","密码错误"),
    MENT_ERROR("999999", "执行异常，请稍后重试..."),
    SUCCESS("100000", "执行成功"),
    PARAMSTRING_ERROR("888888", "请求参数不完整"),
    CHECK_MD5_ERROR("222222", "未定义的请求方式"),
    ACCESS_ILLEGAL("111111", "您的数据异常，请重新操作！"),
    JURISDICTION_ERROR("333333", "无权操作，请更换帐号重试"),
    COMPANYSTATUS_ERROR("555555", "无权操作");

    private final String errMsg;
    private final String errCode;

    //构造器默认也只能是private, 从而保证构造函数只能在内部使用
    private ErrorEnum(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    // 普通方法
    public static String getErrorCodeName(String code) {
        for (ErrorEnum e : ErrorEnum.values()) {
            if (e.getErrCode() == code) {
                return e.getErrMsg();
            }
        }
        return null;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

}
