package gpx;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import com.topografix.gpx._1._1.*;

import java.nio.file.Path;

public class GpxReader {

    public static void main(String[] args) {
        try {
            var context = JAXBContext.newInstance(GpxType.class);
            var unmarshaller = context.createUnmarshaller();
            var gpx = (JAXBElement<GpxType>) unmarshaller.unmarshal(Path.of("src/main/resources/track.gpx").toFile());
            var points = gpx.getValue().getTrk().get(0).getTrkseg().get(0).getTrkpt();
            for (var point: points) {
                System.out.println(point.getLat() + " " + point.getLon());
            }

        } catch (JAXBException e) {
            throw new IllegalStateException("Cannot read", e);
        }
    }
}
