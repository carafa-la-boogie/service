package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.UUID;

@Document(collection = "gift_ideas") // MongoDB collection name
public class GiftIdeas {

    @Id
    private UUID id; // Unique identifier for the gift idea

    @Field("giftName")
    private String giftName; // Name of the gift idea

    @Field("giftLink")
    private String giftLink; // Link to the gift

    @Field("giftGiver")
    private UUID giftGiver; // UUID of the person giving the gift

    @Field("giftReceiver")
    private UUID giftReceiver; // UUID of the person receiving the gift

    // Getter and Setter for 'id'
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
    public UUID getGiftGiver() {
        return giftGiver;
    }

    public void setGiftGiver(UUID giftGiver) {
        this.giftGiver = giftGiver;
    }

    // Getter and Setter for 'giftReceiver'
    public UUID getGiftReceiver() {
        return giftReceiver;
    }

    public void setGiftReceiver(UUID giftReceiver) {
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
