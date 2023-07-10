package subway;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "line")
    private List<Station> stations = new ArrayList<>();

    protected Line() {

    }

    protected Line(final String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void addStation(Station station) {
        station.setLine(this);
        stations.add(station);
    }
}

