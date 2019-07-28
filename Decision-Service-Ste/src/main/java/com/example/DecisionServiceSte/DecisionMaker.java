package com.example.DecisionServiceSte;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DecisionMaker {

	private static float mode(LinkedList<Float> numbers) {
		int maxCount = 0, i, j;
		float maxValue = 0;
		for (i = 0; i < numbers.size(); i++) {
			int count = 0;
			for (j = 0; j < numbers.size(); ++j) {
				if (numbers.get(j).compareTo(numbers.get(i)) == 0)
					++count;
			}
			if (count > maxCount) {
				maxCount = count;
				maxValue = numbers.get(i);
			}
		}
		return maxValue;
	}

	private static LinkedList<String> getAllSensorsNames(
			HashMap<String, ServiceDetailsRequestModel> availableServices) {
		LinkedList<String> names_list = new LinkedList<String>();
		Iterator it = availableServices.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			if (((ServiceDetailsRequestModel) pair.getValue()).getType().compareToIgnoreCase("sensor") == 0) {
				names_list.add((String) pair.getKey());
			}
		}
		return names_list;
	}

	private static float getMeasuredSensorData(String valueName, JSONObject measuredValues) {
		/*
		 * valueName is the name of the measured value i am looking for while measured
		 * values are ALL the measured values by the sensor in the form valueName :
		 * [measuredValues1, measuredValue2, ...]
		 */
		float returnValue = 0;
		int i = 0;
		Iterator it = measuredValues.keys();// entrySet().iterator();
		while (it.hasNext()) {
			// Map.Entry pair = (Map.Entry) it.next();
			String key = (String) it.next();
			// I need to check if this is the value i was looking for
			if (valueName.compareToIgnoreCase(key) == 0) {
				// This is what i was looking for!
				for (Object value : (measuredValues.getJSONArray(key))) {
					i++;
					try {
						returnValue += ((float) value);
					} catch (java.lang.ClassCastException e) {
						returnValue += ((Integer) value).floatValue();
					}
				}
				return returnValue / i; // return the mean of the measured values
			}

		}
		return returnValue;
	}

	private static float getWantedSensorValue(String valueName, JSONArray references, JSONObject suggestedValues,
			HashMap<String, ServiceDetailsRequestModel> availableServices) {
		/*
		 * Will be using modal value. This is because finding an average value may not
		 * make happy anybody. So let's just choose the most wanted one, the democratic
		 * way
		 */
		float returnValue = 0;
		LinkedList<Float> values = new LinkedList<Float>();
		for (Object reference : references) {
			JSONObject wanted;
			try {
				if (availableServices.get((String) reference).getType().compareToIgnoreCase("sensor") == 0)
					wanted = availableServices.get((String) reference).getData().getJSONObject("wanted");
				else
					wanted = availableServices.get((String) reference).getData().getJSONObject("suggested");
			} catch (JSONException e) {
				System.out.println(e);
				continue;
			} catch (java.lang.NullPointerException e) {
				System.out.println(e);
				continue;
			}
			try {
				values.add(wanted.getFloat(valueName));
			} catch (JSONException e) {
				System.out.println(e);
				continue;
			}
		}
		if (suggestedValues != null) {
			try {
				values.add(suggestedValues.getFloat(valueName));
			} catch (JSONException e) {
				System.out.println(e);
			}
		}
		returnValue = mode(values);
		return returnValue;
	}

	private static float computeCorrection(String valueName, float measuredValue, float wantedValue,
			JSONObject activeValues) {
		float activeValue;
		try {
			activeValue = activeValues.getFloat(valueName);
			// This will tell us of how much we should increment the activeValue
		} catch (RuntimeException e) {
			// This means the value may be a boolean, so it would see a string like "true"
			// or "false"
			return (wantedValue + (wantedValue - measuredValue));
			// So we just return the value we'd like to set.
		}
		return ((wantedValue + (wantedValue - measuredValue)) - activeValue);
	}

	private static JSONObject ComputeAnswer(String key, JSONObject value,
			HashMap<String, ServiceDetailsRequestModel> availableServices, JSONObject suggestedValues,
			JSONObject activeValues) {
		/*
		 * Key is the sensor's name value is needed_value : [references,...]
		 */
		JSONObject returnValue = new JSONObject();
		ServiceDetailsRequestModel sensorData = availableServices.get(key);
		if (sensorData == null)
			System.out.println("Servizio " + key + " non disponibile");
		else {
			Iterator it = value.keys();// entrySet().iterator();
			while (it.hasNext()) {
				// Map.Entry pair = (Map.Entry) it.next();
				String key2 = (String) it.next(); // We can use the key to retrieve the right sensor measured
													// value
													// from
				float measuredValue;
				try {
					measuredValue = getMeasuredSensorData(key2, sensorData.getData().getJSONObject("measured")); // This
																													// is
																													// the
																													// value
																													// measured
																													// by
																													// this
																													// sensor
				} catch (JSONException e) {
					System.out.println("Missing measured field in sensor " + key);
					continue;
				}
				float wantedValue = getWantedSensorValue(key2, value.getJSONArray(key2), suggestedValues,
						availableServices);
				// I am passing the ValueName i am evaluating, from which services i should get
				// the suggested value it should actually be, the suggested vlues from this
				// actuator
				returnValue.put(key2, computeCorrection(key2, measuredValue, wantedValue, activeValues));
				/*
				 * Will be passing the measured value and the wanted one, its name and all the
				 * active values on the actuator This way i will be computing how much i should
				 * correct the active value to make it get closer to the desired one given the
				 * me4asured one. For example, i may want a temperature of 24 °C, but my heaters
				 * are already running at 28°C, but the average temperature in the room is 22°C.
				 * To warm up the room faster, i may want to make the heaters run at 29°C. This
				 * may mean having an history of the measured temperatures and calculate the
				 * gradient 😭 At the moment, i will just do a simple difference between the
				 * measured value and the wanted one. m = measured, w = wanted, a = active. a =
				 * w + (w - m) Using the gradient will be possible once added the history of
				 * services
				 */
			}
		}
		return returnValue;
	}

	public static String takeDecision(ServiceDetailsRequestModel applicantInfo,
			HashMap<String, ServiceDetailsRequestModel> availableServices) {

		JSONObject returnValue = new JSONObject();
		JSONObject suggestedValues;
		JSONObject activeValues;
		// Map<String, Object> needed_sensors;
		JSONObject needed_sensors;
		try {
			needed_sensors = (applicantInfo.getData().getJSONObject("needed_sensors"));// .toMap();
		} catch (JSONException e) {
			return "Wrong protocol used.";
		}

		try {
			suggestedValues = (applicantInfo.getData().getJSONObject("suggested"));
		} catch (JSONException e) {
			suggestedValues = null; // WHich is empty
		}
		try {
			activeValues = (applicantInfo.getData().getJSONObject("active"));
		} catch (JSONException e) {
			System.out.println("No active values found!");
			activeValues = null;
		}
		Iterator it = needed_sensors.keys();// entrySet().iterator();
		while (it.hasNext()) {
			// Map.Entry pair = (Map.Entry) it.next();
			String key = (String) it.next();
			// String key = (String) pair.getKey();
			// JSONObject value = (JSONObject) pair.getValue();

			if (key.compareToIgnoreCase("*") != 0) {
				returnValue.put(key, ComputeAnswer(key, needed_sensors.getJSONObject(key), availableServices,
						suggestedValues, activeValues));
			} else {
				for (String key2 : getAllSensorsNames(availableServices)) {
					returnValue.put(key2, ComputeAnswer(key2, needed_sensors.getJSONObject(key), availableServices,
							suggestedValues, activeValues));
				}
			}

		}

		return returnValue.toString();
	}
}
