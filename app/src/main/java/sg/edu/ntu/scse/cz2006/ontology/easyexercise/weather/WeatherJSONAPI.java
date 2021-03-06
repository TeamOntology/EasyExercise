package sg.edu.ntu.scse.cz2006.ontology.easyexercise.weather;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherDataType;

/**
 * The common API used for weather JSON parsers.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public abstract class WeatherJSONAPI {
    private final WeatherDataType type;

    public WeatherJSONAPI(WeatherDataType type) {
        this.type = type;
    }

    public abstract boolean apiIsHealthy();

    public String getDataTitle() {
        return type.toString();
    }

    public WeatherDataType getType() {
        return type;
    }
}
