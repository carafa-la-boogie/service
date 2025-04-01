package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "gift_ideas") // MongoDB collection name
public class GiftIdeas {

    @Id
    private String id; // Unique identifier for the gift idea

    @Field("giftName")
    private String giftName; // Name of the gift idea

    @Field("giftLink")
    private String giftLink; // Link to the gift

    @Field("giftGiver")
    private String giftGiver; // UUID of the person giving the gift

    @Field("giftReceiver")
    private String giftReceiver; // UUID of the person receiving the gift

    public GiftIdeas() {
    }

    public GiftIdeas(String id, String giftName, String giftLink, String giftGiver, String giftReceiver) {
        this.id = id;
        this.giftGiver = giftGiver;
        this.giftReceiver = giftReceiver;
        this.giftName = giftName;
        this.giftLink = giftLink;
    }

    // Getter and Setter for 'id'
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for 'giftName'
    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    // Getter and Setter for 'giftLink'
    public String getGiftLink() {
        return giftLink;
    }

    public void setGiftLink(String giftLink) {
        this.giftLink = giftLink;
    }

    // Getter and Setter for 'giftGiver'
    public String getGiftGiver() {
        return giftGiver;
    }

    public void setGiftGiver(String giftGiver) {
        this.giftGiver = giftGiver;
    }

    // Getter and Setter for 'giftReceiver'
    public String getGiftReceiver() {
        return giftReceiver;
    }

    public void setGiftReceiver(String giftReceiver) {
        this.giftReceiver = giftReceiver;
    }

    @Override
    public String toString() {
        return "GiftIdeas{" +
                "id=" + id +
                ", giftName='" + giftName + '\'' +
                ", giftLink='" + giftLink + '\'' +
                ", giftGiver=" + giftGiver +
                ", giftReceiver=" + giftReceiver +
                '}';
    }
}
