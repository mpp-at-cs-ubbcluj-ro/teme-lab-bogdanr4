import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry();

        ArrayList<Car> cars = new ArrayList<>();
 	    try (PreparedStatement statement = dbUtils.getConnection().prepareStatement("SELECT * FROM cars WHERE manufacturer = ?")) {
            statement.setString(1, manufacturerN);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");

                Car car = new Car(manufacturerN, model, year);
                car.setId(id);

                cars.add(car);
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry();

        ArrayList<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = dbUtils.getConnection().prepareStatement("SELECT * FROM cars WHERE year BETWEEN ? AND ?")) {
            statement.setInt(1, min);
            statement.setInt(2, max);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String manufacturerN = resultSet.getString("manufacturer");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");

                Car car = new Car(manufacturerN, model, year);
                car.setId(id);

                cars.add(car);
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        logger.traceExit(cars);
        return cars;
    }

    @Override
    public void add(Car car) {
        logger.traceEntry();

        try (PreparedStatement statement = dbUtils.getConnection().prepareStatement("INSERT INTO cars (manufacturer, model, year) VALUES (?, ?, ?)")) {
            statement.setString(1, car.getManufacturer());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }

        logger.traceExit();
    }

    @Override
    public void update(Integer carId, Car car) {
        logger.traceEntry();

        try (PreparedStatement statement = dbUtils.getConnection().prepareStatement("UPDATE cars SET manufacturer = ?, model = ?, year = ? WHERE id = ?")) {
            statement.setString(1, car.getManufacturer());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setInt(4, carId);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }

        logger.traceExit();
    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry();

        ArrayList<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = dbUtils.getConnection().prepareStatement("SELECT * FROM cars")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String manufacturerN = resultSet.getString("manufacturer");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");

                Car car = new Car(manufacturerN, model, year);
                car.setId(id);

                cars.add(car);
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        logger.traceExit(cars);
        return cars;
    }
}
