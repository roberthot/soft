package cn.hot.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 * weChat消息工具类

 */
public class MessageUtil {

    private static Logger log = LoggerFactory.getLogger(MessageUtil.class);
    /**
     * 解析微信发来的请求（XML
     * @param inputStream 对输入的内容进行解析
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(InputStream inputStream) {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();
        // 从request中取得输入流
        try {
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                List<Element> elementList1 = e.elements();
                if (elementList1.size() > 0) {
                    for (Element element : elementList1) {
                        map.put(element.getName(), element.getText());
                    }
                } else {
                    map.put(e.getName(), e.getText());
                }
            }
        } catch (DocumentException e) {
            log.error("处理微信消息异常:{}",e.getMessage());
        } finally {
            // 释放资源
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
            }
        }

        return map;
    }


    public static Map<String,String> parseXml(String xml){
        Map<String, String> map = new HashMap<>();
        org.jsoup.nodes.Document document = Jsoup.parse(xml);
        org.jsoup.select.Elements elements = document.getElementsByTag("xml");
        for (org.jsoup.nodes.Element element : elements.first().getAllElements()) {
            map.put(element.tagName(),element.text());
        }
        return map;
    }

}
