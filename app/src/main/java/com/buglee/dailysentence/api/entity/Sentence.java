package com.buglee.dailysentence.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by LEE on 2017/7/18 0018.
 */

public class Sentence implements Parcelable {

    /**
     * sid : 2662
     * tts : http://news.iciba.com/admin/tts/2017-07-18-day
     * content : The good thing about being young is that you are not experienced enough to know you cannot possibly do the things you are doing.
     * note : 年轻的好处在于，你还没有太多经验，并天生相信一切皆可能。
     * love : 2178
     * translation : 词霸小编：因为年轻,才有的是时间;因为年轻,才更容易接受新事物;因为年轻,才有最为饱满的激情与最智慧的大脑。趁年轻,相信希望,相信梦想,为自己的梦想拼搏闯一次！！！
     * picture : http://cdn.iciba.com/news/word/20170718.jpg
     * picture2 : http://cdn.iciba.com/news/word/big_20170718b.jpg
     * caption : 词霸每日一句
     * dateline : 2017-07-18
     * s_pv : 0
     * sp_pv : 0
     * tags : [{"id":null,"name":null}]
     * fenxiang_img : http://cdn.iciba.com/web/news/longweibo/imag/2017-07-18.jpg
     */

    private String sid;
    private String tts;
    private String content;
    private String note;
    private String love;
    private String translation;
    private String picture;
    private String picture2;
    private String caption;
    private String dateline;
    private String s_pv;
    private String sp_pv;
    private String fenxiang_img;
    private List<TagsBean> tags;

    protected Sentence(Parcel in) {
        sid = in.readString();
        tts = in.readString();
        content = in.readString();
        note = in.readString();
        love = in.readString();
        translation = in.readString();
        picture = in.readString();
        picture2 = in.readString();
        caption = in.readString();
        dateline = in.readString();
        s_pv = in.readString();
        sp_pv = in.readString();
        fenxiang_img = in.readString();
    }

    public static final Creator<Sentence> CREATOR = new Creator<Sentence>() {
        @Override
        public Sentence createFromParcel(Parcel in) {
            return new Sentence(in);
        }

        @Override
        public Sentence[] newArray(int size) {
            return new Sentence[size];
        }
    };

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getS_pv() {
        return s_pv;
    }

    public void setS_pv(String s_pv) {
        this.s_pv = s_pv;
    }

    public String getSp_pv() {
        return sp_pv;
    }

    public void setSp_pv(String sp_pv) {
        this.sp_pv = sp_pv;
    }

    public String getFenxiang_img() {
        return fenxiang_img;
    }

    public void setFenxiang_img(String fenxiang_img) {
        this.fenxiang_img = fenxiang_img;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sid);
        parcel.writeString(tts);
        parcel.writeString(content);
        parcel.writeString(note);
        parcel.writeString(love);
        parcel.writeString(translation);
        parcel.writeString(picture);
        parcel.writeString(picture2);
        parcel.writeString(caption);
        parcel.writeString(dateline);
        parcel.writeString(s_pv);
        parcel.writeString(sp_pv);
        parcel.writeString(fenxiang_img);
    }

    public static class TagsBean {
        /**
         * id : null
         * name : null
         */

        private Object id;
        private Object name;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }
    }
}
