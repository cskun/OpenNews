package me.jinkun.opennews.data.domain;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here

import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.List;

import me.jinkun.common.utils.L;
// KEEP INCLUDES END


/**
 * Entity mapped to table "NEWS_DETAIL".
 */
public class NewsDetail implements java.io.Serializable {

    private Long id;
    /*标题*/
    private String title;
    /*内容*/
    private String body;
    /*更多*/
    private Boolean hasNext;

    // KEEP FIELDS - put your custom fields here
    private List<NewsImage> img = new ArrayList<>();
    // KEEP FIELDS END

    public NewsDetail() {
    }

    public NewsDetail(Long id) {
        this.id = id;
    }

    public NewsDetail(Long id, String title, String body, Boolean hasNext) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.hasNext = hasNext;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JavascriptInterface
    public String getTitle() {
        return title;
    }

    @JavascriptInterface
    public void setTitle(String title) {
        this.title = title;
    }

    @JavascriptInterface
    public String getBody() {
        //这里先使用本地图片占位，然后图片加载完了替换原来的占位的图片
        //<img id="img_0" src="file:///android_asset/web/no_loading.png"/>
        for (int i = 0; i < img.size(); i++) {
            NewsImage newsImage = img.get(i);
            L.e("img_" + i + " --> " + newsImage.getSrc());
            String imgTag = "<img id='img_" + i + "' src='file:///android_asset/web/no_loading.png' width='100%'/>";
            body = body.replace(newsImage.getRef(), imgTag);
        }
        return body;
    }

    @JavascriptInterface
    public void setBody(String body) {
        this.body = body;
    }

    @JavascriptInterface
    public Boolean getHasNext() {
        return hasNext;
    }

    @JavascriptInterface
    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    // KEEP METHODS - put your custom methods here
    @JavascriptInterface
    public String getTitlePre() {
        return "<h3>" + title + "</h3>";
    }

    @JavascriptInterface
    public List<NewsImage> getImg() {
        return img;
    }

    @JavascriptInterface
    public void setImg(List<NewsImage> img) {
        this.img = img;
    }

    @JavascriptInterface
    public void requestImgLoading(String start, String end) {
        L.e("start --> " + start + ",end --> " + end);
    }

    @JavascriptInterface
    public int getTextSize() {
        return 2;
    }
    // KEEP METHODS END


    public static class Image {
        //        "alt": "",
//        "pixel": "550*309",
//        "ref": "<!--IMG#0-->",
//        "src":
        private String alt;
        private String pixel;
        private String ref;
        private String src;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getPixel() {
            return pixel;
        }

        public void setPixel(String pixel) {
            this.pixel = pixel;
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
