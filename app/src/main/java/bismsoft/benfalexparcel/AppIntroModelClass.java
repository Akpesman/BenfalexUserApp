package bismsoft.benfalexparcel;

public class AppIntroModelClass {

    String Title,desc;
    int ScreenImg;

    public String getDesc() {
        return desc;
    }

    public AppIntroModelClass(String title, String desc, int screenImg) {
        Title = title;
        this.desc = desc;
        ScreenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getScreenImg() {
        return ScreenImg;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }
}
