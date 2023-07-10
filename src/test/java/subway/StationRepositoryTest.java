package subway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class StationRepositoryTest {

    private final StationRepository stations;
    private final LineRepository lines;

    public StationRepositoryTest(StationRepository stations, LineRepository lines) {
        this.stations = stations;
        this.lines = lines;
    }

    @Test
    void save() {
        final Station expected = new Station("잠실역");
        final Station actual = stations.save(expected);
//        // 이건 되고 -> 영속성 컨텍스트로 인해 이건 됨
//        final Station actual2 = stations.save(expected);
//        // 이건 안 된
//        final Station actual2 = stations.save(new Station("잠실역"));
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stations.save(new Station(expected));
        final Station actual = stations.findByName(expected);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(expected);
    }

    @Test
    void identity() {
        final Station station1 = stations.save(new Station("잠실역"));
        final Station station2 = stations.findById(station1.getId()).get();
        assertThat(station1 == station2).isTrue();
    }

    @Test
    void identity2() {
        final Station station1 = stations.save(new Station("잠실역"));
        final Station station2 = stations.findByName("잠실역");
        assertThat(station1 == station2).isTrue();
    }

    @Test
    void update() {
        final Station station1 = stations.save(new Station("잠실역"));
        station1.changeName("선릉역");
//        final Station station2 = stations.findByName("선릉역");
//        assertThat(station2).isNotNull();
    }

    @Test
    public void saveWithLine() {
        final Station expected = new Station("선릉역");
        expected.setLine(lines.save(new Line("2호선")));
        final Station actual = stations.save(expected);
        stations.flush();
    }

    @Test
    void findByNameWithLine() {
        final Station actual = stations.findByName("교대역");
        assertThat(actual).isNotNull();
        assertThat(actual.getLine()).isNotNull();
    }

    @Test
    void updateWithLine() {
        final Station expected = stations.findByName("교대역");
        expected.setLine(lines.save(new Line("2호선")));
        stations.flush();
    }

    @Test
    void removeLineInStation() {
        final Station expected = stations.findByName("교대역");
        expected.setLine(null);
        stations.flush();
    }

    @Test
    void removeLine() {
        final Line line = lines.findByName("3호선");
        final Station expected = stations.findByName("교대역");
        expected.setLine(null);
        lines.delete(line);
        stations.flush();
    }
}
