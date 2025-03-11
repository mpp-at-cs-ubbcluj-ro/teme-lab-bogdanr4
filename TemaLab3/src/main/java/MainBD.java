import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainBD {
    public static void main(String[] args) {

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        CarRepository carRepo=new CarsDBRepository(props);
        carRepo.add(new Car("Tesla","Model S", 2019));

        carRepo.update(1, new Car("Dacia", "Logan", 2014));

        System.out.println("Toate masinile din db");
        for(Car car:carRepo.findAll())
            System.out.println(car);

       String manufacturer="Tesla";
       System.out.println("Masinile produse de "+manufacturer);
       for(Car car:carRepo.findByManufacturer(manufacturer))
            System.out.println(car);

        int min = 2014;
        int max = 2016;
        System.out.println("Masinile produse intre " + min + " si " + max);
        for (Car car : carRepo.findBetweenYears(min, max))
            System.out.println(car);
    }
}
