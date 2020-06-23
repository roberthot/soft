package cn.hot.util.payApi;

import cn.hot.util.HttpUtil;
import cn.hot.util.RandomUtil;
import cn.hot.util.encrypt.MD5;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;

import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;

public class PayApi {

    public static String pay(String body, String pay, String openid, String url_, String ip, String tradeNo, String appId, String mchId, String mchKey) {
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        List<NameValuePair> nvps = new ArrayList<>(); //注意添加顺序
        nvps.add(new BasicNameValuePair("appid", appId));//appid
        nvps.add(new BasicNameValuePair("body", body));//商品描述
        nvps.add(new BasicNameValuePair("mch_id", mchId));//商户号
        nvps.add(new BasicNameValuePair("nonce_str", RandomUtil.random()));//随机字符串
        nvps.add(new BasicNameValuePair("notify_url", url_));//通知地址，成功返回地址
        nvps.add(new BasicNameValuePair("openid", openid));
        nvps.add(new BasicNameValuePair("out_trade_no", tradeNo));//商户订单号
        nvps.add(new BasicNameValuePair("spbill_create_ip", ip));//终端 IP
        nvps.add(new BasicNameValuePair("total_fee", String.valueOf(Integer.parseInt(pay))));//总金额
        nvps.add(new BasicNameValuePair("trade_type", "JSAPI"));//交易类型  JSAPI、NATIVE、APP
        //签名生成

        String xml = sign(nvps, mchKey);
        LoggerFactory.getLogger(PayApi.class).info("Pay {}", xml);
        try {
            return HttpUtil.execute(url, new StringEntity(xml, "utf-8"));
        } catch (UnsupportedCharsetException e) {
            LoggerFactory.getLogger(PayApi.class).error("Pay error:{}", e);
        }

        return "";
    }

    /**
     * @param out_trade_no  商户订单号
     * @param out_refund_no 商户退款单号
     * @param total_fee     订单金额
     * @param refund_fee    退款金额
     * @param weChatInfo    需要传入cert文件地址
     * @return
     */
    public static String refund(String out_trade_no, String out_refund_no, String total_fee, String refund_fee
            , String appId, String mchId, String mchKey, String cert) {
        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("appid", appId));//appid
        nvps.add(new BasicNameValuePair("mch_id", mchId));//商户号
        nvps.add(new BasicNameValuePair("nonce_str", RandomUtil.random()));//随机字符串
        nvps.add(new BasicNameValuePair("out_refund_no", out_refund_no));//商户退款单号
        nvps.add(new BasicNameValuePair("out_trade_no", out_trade_no));//商户订单号
        nvps.add(new BasicNameValuePair("refund_fee", refund_fee));//退款金额
        nvps.add(new BasicNameValuePair("total_fee", total_fee));//订单金额

        //转为xml
        String xml = sign(nvps, mchKey);
        try {
            return HttpUtil.executeSSL(url, new StringEntity(xml, "utf-8"), mchId, cert);
        } catch (UnsupportedCharsetException e) {
            LoggerFactory.getLogger(PayApi.class).error("Refund error:{}", e.getMessage());
        }
        return "";
    }

    public static String sign(List<NameValuePair> nvps, String mchKey) {
        String sign = HttpUtil.toUrl(nvps);
        sign = sign + "key=" + mchKey;
        sign = MD5.encode(sign).toUpperCase();
        nvps.add(new BasicNameValuePair("sign", sign));//签名

        //转为xml
        String xml = HttpUtil.toXml(nvps);
        return xml;
    }

}
