package com.vehicle_routing;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Component
public class GeolocationService {

    private final DatabaseReader databaseReader;

    public GeolocationService() throws IOException {
        File database = new File("C:\\Vehicle Routing\\GeoLite2-City.mmdb");
        this.databaseReader = new DatabaseReader.Builder(database).build();
    }

    public GeolocationResult getGeolocation(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            CityResponse cityResponse = databaseReader.city(inetAddress);
            Country country = cityResponse.getCountry();
            Location location = cityResponse.getLocation();

            // return Geo location result
            return new GeolocationResult(
                    ipAddress,
                    country.getName(),
                    cityResponse.getCity().getName(),
                    location.getLatitude(),
                    location.getLongitude()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}