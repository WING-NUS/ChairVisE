package sg.edu.nus.comp.chairvise4.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/7/2020 9:41 PM
 */
@Entity
@Data
@Table(name = "presentation")
public class Presentation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "conference_id", nullable = false)
    private int conferenceId;

    @ManyToOne
    @JoinColumn(name = "chair_id", nullable = false)
    private Person chair;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "chart_list")
    private String sectionList;

    @Column(name = "created_at")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date createdAt;
}
