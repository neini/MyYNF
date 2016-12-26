package com.bwie.xyn.demo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class CategoryRoot implements  Serializable{

    /**
     * code : 200
     * msg : success
     * data : {"category":[{"id":"16","cat_name":"按功效","is_leaf":"0","children":[{"id":"17","cat_name":"补水保湿","is_leaf":"1"},{"id":"31","cat_name":"舒缓修护","is_leaf":"1"},{"id":"19","cat_name":"控油去痘","is_leaf":"1"},{"id":"18","cat_name":"美白提亮","is_leaf":"1"},{"id":"20","cat_name":"紧致抗皱","is_leaf":"1"}]},{"id":"6","cat_name":"按属性","is_leaf":"0","children":[{"id":"38","cat_name":"面膜","is_leaf":"0"},{"id":"39","cat_name":"润肤水","is_leaf":"1"},{"id":"40","cat_name":"润肤乳","is_leaf":"1"},{"id":"24","cat_name":"洁面乳","is_leaf":"0"},{"id":"35","cat_name":"其他","is_leaf":"0"},{"id":"33","cat_name":"实惠套装","is_leaf":"1"},{"id":"9","cat_name":"贴式面膜","is_leaf":"0"},{"id":"10","cat_name":"睡眠面膜","is_leaf":"0"},{"id":"23","cat_name":"泥浆面膜","is_leaf":"0"}]},{"id":"11","cat_name":"按肤质","is_leaf":"0","children":[{"id":"14","cat_name":"混合性肤质","is_leaf":"1"},{"id":"13","cat_name":"中性肤质","is_leaf":"1"},{"id":"29","cat_name":"干性肤质","is_leaf":"1"},{"id":"28","cat_name":"油性肤质","is_leaf":"1"},{"id":"15","cat_name":"痘痘肤质","is_leaf":"1"},{"id":"37","cat_name":"敏感肤质","is_leaf":"0"}]}],"goodsBrief":[{"id":"121","goods_name":"镇店之宝丨美白嫩肤矿物蚕丝面膜7片","shop_price":39.9,"market_price":99,"goods_img":"http://image.hmeili.com/yunifang/images/goods/121/goods_img/161201135479872856902510.jpg","reservable":false,"efficacy":"镇店之宝 美白爆款","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"389","goods_name":"清爽平衡矿物蚕丝面膜黑面膜21片","shop_price":99.9,"market_price":297,"goods_img":"http://image.hmeili.com/yunifang/images/goods/389/goods_img/16081909368531961221339216.jpg","reservable":false,"efficacy":"以黑吸黑 净透亮肤","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"85","goods_name":"果味鲜饮|水果鲜润亮肤补水面膜套装17片","shop_price":59.9,"market_price":240,"goods_img":"http://image.hmeili.com/yunifang/images/goods/85/goods_img/160819085747012099748482408.jpg","reservable":false,"efficacy":"水嫩舒爽 鲜活亮颜","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"684","goods_name":"花粹水润面膜套装10片","shop_price":29.9,"market_price":139,"goods_img":"http://image.hmeili.com/yunifang/images/goods/684/goods_img/160819095063413908186337181.jpg","reservable":false,"efficacy":"水润充盈 鲜嫩少女肌","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"6","goods_name":"亮颜水润蚕丝面膜（寂地定制版）","shop_price":59.9,"market_price":239.9,"goods_img":"http://image.hmeili.com/yunifang/images/goods/6/goods_img/160819084594721181484473699.jpg","reservable":false,"efficacy":"深层补水 提亮肤色","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"343","goods_name":"美白嫩肤润肤水150ml","shop_price":59,"market_price":79,"goods_img":"http://image.hmeili.com/yunifang/images/goods/343/goods_img/161109195539019451480402371.jpg","reservable":false,"efficacy":"补水保湿 美白嫩肤","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"772","goods_name":"清爽亮颜黑面膜套装21片","shop_price":99.9,"market_price":297,"goods_img":"http://image.hmeili.com/yunifang/images/goods/772/goods_img/1608191429278479187604212.jpg","reservable":false,"efficacy":"热销黑膜 净透亮肤","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"5","goods_name":"金桂花矿物眼膜贴60片","shop_price":69,"market_price":89,"goods_img":"http://image.hmeili.com/yunifang/images/goods/5/goods_img/16081908444051052195086751.jpg","reservable":false,"efficacy":"补水靓眼 改善熊猫眼","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"239","goods_name":"蜂蜜矿物蚕丝面膜7片","shop_price":89,"market_price":109,"goods_img":"http://image.hmeili.com/yunifang/images/goods/239/goods_img/16081909252442893599737067.jpg","reservable":false,"efficacy":"深层补水 长效保湿","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"9","goods_name":"玫瑰滋养矿物睡眠面膜180g","shop_price":59.9,"market_price":79.9,"goods_img":"http://image.hmeili.com/yunifang/images/goods/9/goods_img/160819084569920890610621654.jpg","reservable":false,"efficacy":"镇店之宝 彻夜补水","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"10","goods_name":"美白嫩肤睡眠面膜180g","shop_price":69,"market_price":99,"goods_img":"http://image.hmeili.com/yunifang/images/goods/10/goods_img/161109200044614716799834077.jpg","reservable":false,"efficacy":"补水美白 越睡越白","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"446","goods_name":"芦荟补水保湿凝胶150g","shop_price":49.9,"market_price":59,"goods_img":"http://image.hmeili.com/yunifang/images/goods/446/goods_img/16081909409518953549635059.jpg","reservable":false,"efficacy":"水水润润 抗痘修护","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"14","goods_name":"矿物泥浆鼻膜60g","shop_price":55,"market_price":69,"goods_img":"http://image.hmeili.com/yunifang/images/goods/14/goods_img/160819084841216186223194195.jpg","reservable":false,"efficacy":"草莓鼻小救星 收敛毛孔","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"83","goods_name":"黑玫瑰矿物蚕丝面膜7片","shop_price":119,"market_price":139,"goods_img":"http://image.hmeili.com/yunifang/images/goods/83/goods_img/16081908576425344499831215.jpg","reservable":false,"efficacy":"深度保养 补水提亮","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"},{"id":"95","goods_name":"多效套装丨补水滋养提亮修护多效蚕丝面膜28片","shop_price":139.9,"market_price":416,"goods_img":"http://image.hmeili.com/yunifang/images/goods/95/goods_img/160819085823018111120147866.jpg","reservable":false,"efficacy":"补水滋养 提亮修护","watermarkUrl":"http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CategoryBean> category;
        private List<GoodsBriefBean> goodsBrief;

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<GoodsBriefBean> getGoodsBrief() {
            return goodsBrief;
        }

        public void setGoodsBrief(List<GoodsBriefBean> goodsBrief) {
            this.goodsBrief = goodsBrief;
        }

        public static class CategoryBean implements Serializable {
            /**
             * id : 16
             * cat_name : 按功效
             * is_leaf : 0
             * children : [{"id":"17","cat_name":"补水保湿","is_leaf":"1"},{"id":"31","cat_name":"舒缓修护","is_leaf":"1"},{"id":"19","cat_name":"控油去痘","is_leaf":"1"},{"id":"18","cat_name":"美白提亮","is_leaf":"1"},{"id":"20","cat_name":"紧致抗皱","is_leaf":"1"}]
             */

            private String id;
            private String cat_name;
            private String is_leaf;
            private List<ChildrenBean> children;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCat_name() {
                return cat_name;
            }

            public void setCat_name(String cat_name) {
                this.cat_name = cat_name;
            }

            public String getIs_leaf() {
                return is_leaf;
            }

            public void setIs_leaf(String is_leaf) {
                this.is_leaf = is_leaf;
            }

            public List<ChildrenBean> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            public static class ChildrenBean implements  Serializable{
                /**
                 * id : 17
                 * cat_name : 补水保湿
                 * is_leaf : 1
                 */

                private String id;
                private String cat_name;
                private String is_leaf;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCat_name() {
                    return cat_name;
                }

                public void setCat_name(String cat_name) {
                    this.cat_name = cat_name;
                }

                public String getIs_leaf() {
                    return is_leaf;
                }

                public void setIs_leaf(String is_leaf) {
                    this.is_leaf = is_leaf;
                }
            }
        }

        public static class GoodsBriefBean  implements  Serializable{
            /**
             * id : 121
             * goods_name : 镇店之宝丨美白嫩肤矿物蚕丝面膜7片
             * shop_price : 39.9
             * market_price : 99.0
             * goods_img : http://image.hmeili.com/yunifang/images/goods/121/goods_img/161201135479872856902510.jpg
             * reservable : false
             * efficacy : 镇店之宝 美白爆款
             * watermarkUrl : http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png
             */

            private String id;
            private String goods_name;
            private double shop_price;
            private double market_price;
            private String goods_img;
            private boolean reservable;
            private String efficacy;
            private String watermarkUrl;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public double getShop_price() {
                return shop_price;
            }

            public void setShop_price(double shop_price) {
                this.shop_price = shop_price;
            }

            public double getMarket_price() {
                return market_price;
            }

            public void setMarket_price(double market_price) {
                this.market_price = market_price;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public boolean isReservable() {
                return reservable;
            }

            public void setReservable(boolean reservable) {
                this.reservable = reservable;
            }

            public String getEfficacy() {
                return efficacy;
            }

            public void setEfficacy(String efficacy) {
                this.efficacy = efficacy;
            }

            public String getWatermarkUrl() {
                return watermarkUrl;
            }

            public void setWatermarkUrl(String watermarkUrl) {
                this.watermarkUrl = watermarkUrl;
            }
        }
    }
}
