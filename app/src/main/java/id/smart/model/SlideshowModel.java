package id.smart.model;

public class SlideshowModel {
    public String img_url;
    public String description;
    public String title;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SlideshowModel(String img_url, String description, String title) {
        this.img_url = img_url;
        this.description = description;
        this.title = title;
    }

    public SlideshowModel() {

    }
}
