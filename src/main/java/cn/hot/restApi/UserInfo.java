package cn.hot.restApi;


import com.alibaba.fastjson.JSONObject;

public class UserInfo {
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String privilege;
    private String unionid;
    private boolean isSubscribe = true;

    private String accessToken;

    public UserInfo() {
    }

    public UserInfo(JSONObject userInfo) {
        this.openid = userInfo.getString("openid");
        this.nickname = userInfo.getString("nickname");
        this.sex = userInfo.getString("sex");
        this.province = userInfo.getString("province");
        this.city = userInfo.getString("city");
        this.country = userInfo.getString("country");
        this.headimgurl = userInfo.getString("headimgurl");
        this.privilege = userInfo.getString("privilege");
        this.unionid = userInfo.getString("unionid");
        String subscribe = userInfo.getString("subscribe");
        if(subscribe!=null && "1".equals(subscribe)){
            this.isSubscribe = true;
        }else{
            this.isSubscribe = false;
        }
    }

    public UserInfo(String openid, String nickname, String sex, String province, String city, String country, String headimgurl, String privilege, String unionid) {
        this.openid = openid;
        this.nickname = nickname;
        this.sex = sex;
        this.province = province;
        this.city = city;
        this.country = country;
        this.headimgurl = headimgurl;
        this.privilege = privilege;
        this.unionid = unionid;
    }

    public UserInfo(String openid, String nickname, String sex, String province, String city, String country, String headimgurl) {
        this.openid = openid;
        this.nickname = nickname;
        this.sex = sex;
        this.province = province;
        this.city = city;
        this.country = country;
        this.headimgurl = headimgurl;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isSubscribe() {
        return isSubscribe;
    }

    public void setSubscribe(boolean subscribe) {
        isSubscribe = subscribe;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", privilege='" + privilege + '\'' +
                ", unionid='" + unionid + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
