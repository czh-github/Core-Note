package com.laochen.source.java;

/**
 * Date:2017/8/15 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class CommonTest {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(System.nanoTime());
            System.out.println(System.currentTimeMillis());
        }
    }

    public static void f() {
        Object obj = new Object();
        synchronized (obj) {
            System.out.println();
        }
    }
}
/*
无论是在大街上、餐厅里、还是地铁中，如今，人们“勤奋”地刷着手机屏幕的景象随处可见。
据统计，截至2017年6月，我国网民规模达7.51亿，其中手机网民规模达7.24亿。
互联网尤其是移动互联网正在深刻地影响人们的生活。

手机支付正在渗入社会各个角落，让无现金支付成为现实，在商场、餐厅、超市，人们只需要掏出手机扫扫二维码就可以付款。免去了出门带钱带卡的繁琐，给人们生活带来了极大便利。
线上外卖用户和订单激增，“互联网+餐饮”为大众生活带来了诸多改变，动动手指，即可召唤各大“骑士”准时送餐，周边美食尽收眼底。
共享单车如雨后春笋一般大量涌现，解决出行“最后一公里”，符合人们低碳出行和锻炼身体的理念。
在线手游春风得意，以腾讯出品的《王者荣耀》为代表的手游风靡当下，攻城略池，深受广大年轻朋友们的喜爱。
网上购物日趋多样化，当人们对足不出户在网上购物习以为常时，超级淘APP“亦买亦存”的新鲜体验又吸引了大众目光。
        淘个宝，京个东，传个小票。。。
*/
