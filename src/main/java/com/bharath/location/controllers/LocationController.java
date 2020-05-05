package com.bharath.location.controllers;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bharath.location.model.Location;
import com.bharath.location.service.LocationService;

@Controller
public class LocationController {
	
	@Autowired
	LocationService service;
	
	@RequestMapping("/showCreate")
	public String showCreate() {
		return "createLocation";
	}
	
	@RequestMapping("/saveLoc")
	public String saveLocation(@ModelAttribute("location")Location location, ModelMap modelmap) {
		Location locationSaved = service.saveLocation(location);
		String msg = "Location saved with id: "+locationSaved.getId();
	    modelmap.addAttribute("msg", msg);
		return "createLocation";
	}
	@RequestMapping("/displayLocations")
	public String displayLocations(ModelMap modelMap ) {
		List<Location> locations = service.getAllLocation();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	
	@RequestMapping("deleteLocation")
	public String deleteLocation(@RequestParam("id") int id, ModelMap modelmap) {
		Optional<Location> locationById = service.getLocationById(id);
		
		//Location location2 = new Location();//This can help reduce database call to delete
		//location2.setId(id);//This goes with the above line of code
		
		Location location = locationById.get();
		service.deleteLocation(location);
		List<Location> locations = service.getAllLocation();//we are retrieving all the locations
		modelmap.addAttribute("locations", locations);//setting it to the model map
		return "displayLocations";//and sending it back to the same jsp.
	}
	
	@RequestMapping("/showUpdate")//Once this is hit
	public String showUpdate(@RequestParam("id") int id, ModelMap modelmap)  {//grab integer for id
		Optional<Location> locations = service.getLocationById(id);//Call database to retrieve id to edit
		Location location = locations.get();//we are retrieving the location from the list
		modelmap.addAttribute("location", location);//we are setting the location in the response in the modelMap
		return "updateLocation";
	}
	
	@RequestMapping("/updateLoc")//upon click of the save button from update page, this controller method will be invoked by spring container
	public String updateLocation(@ModelAttribute("location")Location location, ModelMap modelmap) {
		Location updateLocation = service.updateLocation(location);//we are updating the location, this will go through the services layer, data access layer, to the database
		List<Location> locations = service.getAllLocation();//and then we are retrieving all the locations, we are sending it back to the 'displayLocations.jsp'
		modelmap.addAttribute("locations", locations);//inside the 'displaylocations" we are using locations to iterate and display them.
		return "displayLocations";//Then we are sending it back to the displayLocations.jsp.
	}

}
