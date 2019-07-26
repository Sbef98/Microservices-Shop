package example.service;

public class ResponseComputer 
{
	protected static void elabResponse(String response, SensorController sensor)
	{
		if(response.compareToIgnoreCase(sensor.toString()) != 0) //TODO make a better check
			System.out.println("Errore nella comunicazione");
	}
}
