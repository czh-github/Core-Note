package com.laochen.jni.java5.enumtype;

/**
 * Date:2017/7/31 <p>
 * Author:chenzehao@danale.com <p>
 * Description:枚举中定义抽象方法，每个枚举实例都必须实现该方法。定义每个枚举实例的不同行为。
 */

public class AbstractEnumExample {
    public enum Season {
        WINTER {
            @Override
            public String getInfo() {
                return "冬天";
            }
        }, SPRING {
            @Override
            public String getInfo() {
                return "春天";
            }
        }, SUMMER {
            @Override
            public String getInfo() {
                return "夏天";
            }
        }, FALL {
            @Override
            public String getInfo() {
                return "秋天";
            }
        };


        public abstract String getInfo();
    }

    public static void main(String[] args) {
        for (AbstractEnumExample.Season s : AbstractEnumExample.Season.values()) {
            System.out.println(s.getInfo());
        }
    }
}
