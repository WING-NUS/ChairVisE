package sg.edu.nus.comp.chairvise4.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/7/2020 3:19 PM
 */

@Entity
@Data
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

//    @OneToMany(orphanRemoval = true)
//    @JoinColumn(name = "chair_id")
//    private List<Conference> conferences;
}
