import java.util.Scanner;

public class Planetary_Coordinates_Calculator {
    int d;
    double N, i, w, a, e, M;
    int DD, MM, YY;
    String planet;

    Planetary_Coordinates_Calculator() {
        d = 0;// Day number
        N = 0;// Longitude of Ascending node
        i = 0;// Inclination
        w = 0;// Argument of Perihelion
        a = 0;// Semi-Major axis
        e = 0;// Eccentricity
        M = 0;// Mean Anomaly
        planet = "";
    }

    public static void main(String[] args) {
        Planetary_Coordinates_Calculator ob = new Planetary_Coordinates_Calculator();
        Scanner sc = new Scanner(System.in);

        ob.chooseDate();// Input the date and compute the Day Number
        ob.choosePlanet();// Input planet and initialise the Orbital Elements
        ob.computeAndDisplay();// Compute the coordinates and display them
    }

    void chooseDate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("This program computes planetary coordinates at any given date.");
        System.out.println("Enter the date:");

        System.out.println("Enter the year(all four digits) between 1900 and 2100:");
        YY = sc.nextInt();
        System.out.println("Enter the month number(between 1 to 12):");
        MM = sc.nextInt();
        System.out.println("Enter the date(between 1 to 31). Please enter a valid date according to the month:");
        DD = sc.nextInt();

        d = 367 * YY - (7 * (YY + ((MM + 9) / 12))) / 4 + (275 * MM) / 9 + DD - 730530;
    }

    void choosePlanet() {
        Scanner sc = new Scanner(System.in);

        // Basic Data of all the Primary Orbital Elements
        double[] N_array = { (48.3313 + 0.0000324587 * d), (76.6799 + 0.0000246590 * d), (0),
                (49.5574 + 0.0000211081 * d), (100.4542 + 0.0000276854 * d), (113.6634 + 0.0000238980 * d),
                (74.0005 + 0.000013978 * d), (131.7806 + 0.000030173 * d) };
        double[] i_array = { (7.0047 + 0.00000005 * d), (3.3946 + 0.0000000275 * d), (0), (1.8497 - 0.0000000178 * d),
                (1.3030 - 0.0000001557 * d), (2.4886 - 0.0000001081 * d), (0.7733 + 0.000000019 * d),
                (1.7700 - 0.000000255 * d) };
        double[] w_array = { (29.1241 + 0.0000101444 * d), (54.8910 + 0.0000138374 * d), (282.9404 + 0.0000470935 * d),
                (286.5016 + 0.0000292961 * d), (273.8777 + 0.0000164505 * d), (339.3939 + 0.0000297661 * d),
                (96.6612 + 0.000030565 * d), (272.8461 - 0.000006027 * d) };
        double[] a_array = { (0.387098), (0.723330), (1.000000), (1.523688), (5.20256), (9.55475),
                (19.18171 - 0.0000000155 * d), (30.05826 + 0.00000003313 * d) };
        double[] e_array = { (0.205635 + 0.000000000559 * d), (0.006773 - 0.000000001302 * d),
                (0.016709 - 0.000000001151 * d), (0.093405 + 0.000000002516 * d), (0.048498 + 0.000000004469 * d),
                (0.055546 - 0.000000009499 * d), (0.047318 + 0.00000000745 * d), (0.008606 + 0.00000000215 * d) };
        double[] M_array = { (168.6562 + 4.0923344368 * d), (48.0052 + 1.6021302244 * d), (356.0470 + 0.9856002585 * d),
                (18.6021 + 0.5240207766 * d), (19.8950 + 0.0830853001 * d), (316.9670 + 0.0334442282 * d),
                (142.5905 + 0.011725806 * d), (260.2471 + 0.005995147 * d), };
        String[] planet_array = { "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", };

        System.out.println("Choose the planet:");
        for (int i = 0; i < 8; i++) {
            System.out.println((i + 1) + " - " + planet_array[i]);
        }
        int ch = sc.nextInt();

        N = N_array[ch - 1];
        i = i_array[ch - 1];
        w = w_array[ch - 1];
        a = a_array[ch - 1];
        e = e_array[ch - 1];
        M = M_array[ch - 1];
        M = reduce(M);// To reduce the angle between 360 and 0
        planet = planet_array[ch - 1];
    }

    double reduce(double angle) {
        if (angle >= 360)
            return (angle % 360);
        else if (angle < 0)
            return (360 + angle % 360);
        else
            return angle;
    }

    void computeAndDisplay() {
        double E; // Eccentric Anomaly
        double x; // Basic Cartesian Coordinate
        double y; // Basic Cartesian Coordinate
        double r; // Distance from the Sun in Astronomical Units
        double v; // True Anomaly (angle between position and perihelion
        double x_eclip; // Rectangular Ecliptic Heliocentric Coordinate
        double y_eclip; // Rectangular Ecliptic Heliocentric Coordinate
        double z_eclip; // Rectangular Ecliptic Heliocentric Coordinate
        double longitude; // Spherical Ecliptic Heliocentric Coordinate
        double latitude; // Spherical Ecliptic Heliocentric Coordinate
        double ecl; // Obliquity of the Ecliptic
        double x_equat; // Rectangular Equatorial Heliocentric Coordinate
        double y_equat; // Rectangular Equatorial Heliocentric Coordinate
        double z_equat; // Rectangular Equatorial Heliocentric Coordinate
        double RA; // Right Ascension, Spherical Equatorial Heliocentric Coordinate
        double Decl; // Declination, Spherical Equatorial Heliocentric Coordinate

        // To display the information for the specified date
        System.out.println("\fPlanet: " + planet);
        System.out.println("Date: " + DD + "/" + MM + "/" + YY);
        System.out.println("Day number: " + d);
        System.out.println("\nOrbital Elements of " + planet + " at the specified date:");
        System.out.println("Longitude of Ascending Node = " + N + "°");
        System.out.println("Inclination = " + i + "°");
        System.out.println("Argument of Perihelion = " + w + "°");
        System.out.println("Semi-major axis = " + a);
        System.out.println("Eccentricity = " + e);
        System.out.println("Mean Anomaly = " + M + "°");

        ecl = 23.4393 - 0.0000003563 * d;// To calculate the Angle of the Ecliptic relative to the Equatorial Plane;

        E = get_E();// To calculate Eccentric Anomaly with minimum error
        E = reduce(E);// To reduce the angle between 360 and 0

        // To calculate the basic Cartesian Coordinates
        x = a * (Math.cos(Math.toRadians(E)) - e);
        y = a * Math.sqrt(1 - e * e) * Math.sin(Math.toRadians(E));

        r = Math.sqrt(x * x + y * y);// Distance from Sun in Astronomical Units
        v = Math.toDegrees(Math.atan2(y, x));// True Anomaly
        v = reduce(v);// To reduce the angle between 360 and 0

        // To compute the Rectangular Ecliptic Heliocentric coordinates
        x_eclip = r * (Math.cos(Math.toRadians(N)) * Math.cos(Math.toRadians(v + w))
                - Math.sin(Math.toRadians(N)) * Math.sin(Math.toRadians(v + w)) * Math.cos(Math.toRadians(i)));
        y_eclip = r * (Math.sin(Math.toRadians(N)) * Math.cos(Math.toRadians(v + w))
                + Math.cos(Math.toRadians(N)) * Math.sin(Math.toRadians(v + w)) * Math.cos(Math.toRadians(i)));
        z_eclip = r * Math.sin(Math.toRadians(v + w)) * Math.sin(Math.toRadians(i));

        // Since the orbital elements were taken for the Sun's apparent orbit around the
        // Earth, we need to invert the values
        if (planet == "Earth") {
            x_eclip = -x_eclip;
            y_eclip = -y_eclip;
        }

        // To compute the Spherical Ecliptic Heliocentric coordinates
        longitude = Math.toDegrees(Math.atan2(y_eclip, x_eclip));//
        longitude = reduce(longitude);// To reduce the angle between 360 and 0
        latitude = Math.toDegrees(Math.atan2(z_eclip, Math.sqrt(x_eclip * x_eclip + y_eclip * y_eclip)));
        r = Math.sqrt(x_eclip * x_eclip + y_eclip * y_eclip + z_eclip * z_eclip);

        // To compute the Rectangular Equatorial Heliocentric coordinates
        x_equat = x_eclip;
        y_equat = y_eclip * Math.cos(Math.toRadians(ecl)) - z_eclip * Math.sin(Math.toRadians(ecl));
        z_equat = y_eclip * Math.sin(Math.toRadians(ecl)) + z_eclip * Math.cos(Math.toRadians(ecl));

        // To compute the Spherical Equatorial Heliocentric coordinates
        RA = Math.toDegrees(Math.atan2(y_equat, x_equat));
        RA = reduce(RA);// To reduce the angle between 360 and 0
        Decl = Math.toDegrees(Math.atan2(z_equat, Math.sqrt(x_equat * x_equat + y_equat * y_equat)));

        Scanner sc = new Scanner(System.in);

        System.out.println("\nEnter the coordinate system in which you want the result:");
        System.out.println("1 - Rectangular Ecliptic");
        System.out.println("2 - Spherical Ecliptic");
        System.out.println("3 - Rectangular Equatorial");
        System.out.println("4 - Spherical Equatorial\n");

        switch (sc.nextInt()) {
            case 1:
                System.out.println("x = " + x_eclip);
                System.out.println("y = " + y_eclip);
                System.out.println("z = " + z_eclip);
                break;

            case 2:
                System.out.println("Longitude = " + longitude + "°");
                System.out.println("Latitude = " + latitude + "°");
                System.out.println("Distance = " + r + " Astronomical Units");
                break;

            case 3:
                System.out.println("x = " + x_equat);
                System.out.println("y = " + y_equat);
                System.out.println("z = " + z_equat);
                break;

            case 4:
                System.out.print("Right Ascension = ");
                toTimeAngle(RA);
                System.out.print("Declination = ");
                toArcAngle(Decl);
                System.out.println("Distance = " + r + " Astronomical Units");
                break;
        }
    }

    double get_E() {
        // First value of eccentric anomaly
        double E0 = M + (180 / Math.PI) * e * (Math.sin(Math.toRadians(M))) * (1 + e * Math.cos(Math.toRadians(M)));

        double E1;// Second value of Eccentric Anomaly
        E0 = reduce(E0);// To reduce the angle between 360 and 0
        while (true) {
            E1 = E0 - (E0 - (180 / Math.PI) * e * Math.sin(Math.toRadians(E0)) - M)
                    / (1 - e * Math.cos(Math.toRadians(E0)));
            E1 = reduce(E1);// To reduce the angle between 360 and 0
            if (E1 - E0 <= 0.005)// If difference is less than 0.005 the function stops
                return E1;
            else // If difference is larger, the process repeats
                E0 = E1;
        }
    }

    void toTimeAngle(double angle) {
        double degrees = reduce(angle);
        double seconds;
        int minutes;
        int hours;
        int days;

        seconds = Math.toRadians(degrees) * (43200 / Math.PI);
        minutes = (int) seconds / 60;
        seconds = seconds % 60;
        hours = minutes / 60;
        minutes = minutes % 60;
        days = hours / 24;
        hours = hours % 24;

        System.out.println(days + " days, " + hours + " hours, and " + minutes + " minutes " + seconds + "seconds");
    }

    void toArcAngle(double angle) {
        double seconds;
        int minutes;
        int degrees;

        seconds = angle * 3600;
        minutes = (int) seconds / 60;
        seconds = seconds % 60;
        degrees = minutes / 60;
        minutes = minutes % 60;

        if (angle < 0) {
            seconds = -seconds;
            minutes = -minutes;
        }
        System.out.println(degrees + "°" + minutes + "\"" + seconds + "\'");
    }
}
