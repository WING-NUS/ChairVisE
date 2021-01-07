package sg.edu.nus.comp.chairvise4.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/7/2020 4:27 PM
 */

@Entity
@Data
@Table(name = "conference")
public class Conference implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @Column(name = "description")
    private String description;

    @Column(name = "records")
    private String records;

    @Column(name = "created_at")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "chair_id", nullable = false)
    private Person chair;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "conference_id")
    private List<Presentation> presentations;
}
