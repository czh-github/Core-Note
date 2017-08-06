package com.laochen.source.android.widget.recyclerview;

import java.io.Serializable;

/**
 * Date:2017/8/4 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class Photo implements Serializable{
    private String url;
    private String humanDate;
    private String explanation;

    public Photo() {

    }
    public Photo(String url) {
        this.url = url;
    }
    public Photo(String url, String humanDate, String explanation) {
        this.url = url;
        this.humanDate = humanDate;
        this.explanation = explanation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHumanDate() {
        return humanDate;
    }

    public void setHumanDate(String humanDate) {
        this.humanDate = humanDate;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
