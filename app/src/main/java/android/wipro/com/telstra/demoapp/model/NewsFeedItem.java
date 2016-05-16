package android.wipro.com.telstra.demoapp.model;

/**
 * Created by priyag on 13-May-16.
 * Model class for each item in the rows array
 * of teh specified JSON
 */
public class NewsFeedItem {
    public String title;
    public String description;
    public String imageHref;

    public NewsFeedItem(String title, String description, String imageHref){
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }
}
