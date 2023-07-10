package subway;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany // (1)
    @JoinColumn(name = "member_id") // (2)
    private List<Favorite> favorites = new ArrayList<>(); // (3)

}

