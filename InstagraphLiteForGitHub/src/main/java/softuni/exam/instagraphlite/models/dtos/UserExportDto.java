package softuni.exam.instagraphlite.models.dtos;

public class UserExportDto {
    private String username;
    private Integer postCount;
    private String caption;
    private Double pictureSize;
    public UserExportDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Double getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(Double pictureSize) {
        this.pictureSize = pictureSize;
    }

    public UserExportDto(String username, Integer postCount, String caption, Double pictureSize) {
        this.username = username;
        this.postCount = postCount;
        this.caption = caption;
        this.pictureSize = pictureSize;
    }
}
