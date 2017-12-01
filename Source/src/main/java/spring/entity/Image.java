package spring.entity;


import javax.persistence.*;
import java.io.InputStream;
import java.util.Arrays;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=16777215)
    private byte[] image;

    public Image() {}

    public Image(byte[] image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image1 = (Image) o;

        if (getId() != image1.getId()) return false;
        return Arrays.equals(getImage(), image1.getImage());
    }

}
