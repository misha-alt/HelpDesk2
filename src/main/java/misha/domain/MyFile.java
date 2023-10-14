package misha.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name ="MYFILE")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable =  false)
    private int id;
    @Column(nullable = false)
    String file_name;

    @Column(nullable = false)
    private String fileType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ticked ticked;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private Tickethistory tickethistories;

    public MyFile(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Ticked getTicked() {
        return ticked;
    }

    public void setTicked(Ticked ticked) {
        this.ticked = ticked;
    }

    public Tickethistory getTickethistories() {
        return tickethistories;
    }

    public void setTickethistories(Tickethistory tickethistories) {
        this.tickethistories = tickethistories;
    }

    @Override
    public String toString() {
        return "attached file:" + file_name;
    }
}
