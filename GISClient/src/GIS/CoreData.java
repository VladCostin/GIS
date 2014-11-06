package GIS;
import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.postgis.Polygon;

import Mediator.ConstantsId;


/**
 * contains the types for each table/server, the colors associated
 * @author Vlad Herescu
 *
 */
public class CoreData {
	
	/**
	 * for each string type an id is associated
	 */
	static LinkedHashMap<String,Integer> _hashMapTypeOSM;
	
	
	/**
	 * for each integer id a fill color is associated;
	 */
	static HashMap<Integer,Color> _hashMapFillColor;
	
	/**
	 * for each integer id a border color is associated;
	 */
	static HashMap<Integer, Color> _hashMapBorderColor;
	
	
	/**
	 * associate for each table the name of the column where the type is mentioned
	 */
	static HashMap<Tables_Austria_OSM, String> _hashMapTypeColumn;

	/**
	 * initializing the Hashmap
	 */
	CoreData(){

		_hashMapTypeOSM = new LinkedHashMap<String,Integer>();
		_hashMapFillColor = new HashMap<Integer, Color>();
		_hashMapBorderColor = new HashMap<Integer, Color>();
		_hashMapTypeColumn = new HashMap<Tables_Austria_OSM, String>();

		init_type();
		init_FillColor();
		init_BorderColor();
		init_TypeColumn();
	}



	/**
	 * setting the key and the value in the _hashMapTypeColumn
	 */
	public void init_TypeColumn() {
		_hashMapTypeColumn.put(Tables_Austria_OSM.amenity_austria_points, "amenity");
		_hashMapTypeColumn.put(Tables_Austria_OSM.city_austria_polygons, "landuse");
		_hashMapTypeColumn.put(Tables_Austria_OSM.railroads_austria_polylines, "railway");
		_hashMapTypeColumn.put(Tables_Austria_OSM.roads_austria_polylines, "highway");
		_hashMapTypeColumn.put(Tables_Austria_OSM.water_austria_polylines, "waterway");
		_hashMapTypeColumn.put(Tables_Austria_OSM.water_austria_polygons, "waterway");
		
		
	}



	private static void init_type() {
		
		_hashMapTypeOSM.put("city_austria_polygons.residential", ConstantsId.residential);
		_hashMapTypeOSM.put("city_austria_polygons.retail", ConstantsId.retail);
		_hashMapTypeOSM.put("city_austria_polygons.industrial", ConstantsId.industrial);
		_hashMapTypeOSM.put("city_austria_polygons.commercial", ConstantsId.commercial);
		
		
		_hashMapTypeOSM.put("amenity_austria_points.post_office", ConstantsId.post_office);
		_hashMapTypeOSM.put("amenity_austria_points.police",  ConstantsId.police);
		_hashMapTypeOSM.put("amenity_austria_points.doctors", ConstantsId.doctors);
		_hashMapTypeOSM.put("amenity_austria_points.university", ConstantsId.university);
		_hashMapTypeOSM.put("amenity_austria_points.pharmacy", ConstantsId.pharmacy);
		_hashMapTypeOSM.put("amenity_austria_points.bank", ConstantsId.bank);
		_hashMapTypeOSM.put("amenity_austria_points.school", ConstantsId.school);
		
		
		_hashMapTypeOSM.put("roads_austria_polylines.residential", ConstantsId.residentialRoad);
		_hashMapTypeOSM.put("roads_austria_polylines.secondary", ConstantsId.secondary);
		_hashMapTypeOSM.put("roads_austria_polylines.tertiary", ConstantsId.tertiary);
		_hashMapTypeOSM.put("roads_austria_polylines.primary", ConstantsId.primary);
		_hashMapTypeOSM.put("roads_austria_polylines.motorway_link", ConstantsId.motorway_link);
		_hashMapTypeOSM.put("roads_austria_polylines.living_street", ConstantsId.living_street);
		_hashMapTypeOSM.put("roads_austria_polylines.primary_link", ConstantsId.primary_link);
		_hashMapTypeOSM.put("roads_austria_polylines.motorway", ConstantsId.motorway);
		_hashMapTypeOSM.put("roads_austria_polylines.trunk_link", ConstantsId.trunk_link);
		_hashMapTypeOSM.put("roads_austria_polylines.secondary_link", ConstantsId.secondary_link);
		_hashMapTypeOSM.put("roads_austria_polylines.tertiary_link", ConstantsId.tertiary_link);
		_hashMapTypeOSM.put("roads_austria_polylines.trunk", ConstantsId.trunk);
		
		
		_hashMapTypeOSM.put("railroads_austria_polylines.rail", ConstantsId.rail);
		_hashMapTypeOSM.put("railroads_austria_polylines.subway", ConstantsId.subway);
		_hashMapTypeOSM.put("railroads_austria_polylines.tram", ConstantsId.tram);
		
		
		_hashMapTypeOSM.put("water_austria_polylines.fish_pass", 1);
		_hashMapTypeOSM.put("water_austria_polylines.culvert", 2);
		_hashMapTypeOSM.put("water_austria_polylines.waterfall", 3);
		_hashMapTypeOSM.put("water_austria_polylines.weir", 4);
		_hashMapTypeOSM.put("water_austria_polylines.stream;river", 5);
		_hashMapTypeOSM.put("water_austria_polylines.cascade", 6);
		_hashMapTypeOSM.put("water_austria_polylines.wadi", 7);
		_hashMapTypeOSM.put("water_austria_polylines.lock_gate", 8);
		_hashMapTypeOSM.put("water_austria_polylines.stream", 9);
		_hashMapTypeOSM.put("water_austria_polylines.riverbank", 10);
		_hashMapTypeOSM.put("water_austria_polylines.drain", 11);
		_hashMapTypeOSM.put("water_austria_polylines.trench", 12);
		_hashMapTypeOSM.put("water_austria_polylines.canal", 13);
		_hashMapTypeOSM.put("water_austria_polylines.check_dam", 14);
		_hashMapTypeOSM.put("water_austria_polylines.beam_dam", 15);
		_hashMapTypeOSM.put("water_austria_polylines.underground_drain", 16);
		_hashMapTypeOSM.put("water_austria_polylines.wadi", 17);
		_hashMapTypeOSM.put("water_austria_polylines.slide", 18);
		_hashMapTypeOSM.put("water_austria_polylines.river", 19);
		_hashMapTypeOSM.put("water_austria_polylines.ditch", 20);
		_hashMapTypeOSM.put("water_austria_polylines.dam", 21);
		_hashMapTypeOSM.put("water_austria_polylines.stream;ditch", 22);
		_hashMapTypeOSM.put("water_austria_polylines.groyne", 23);
		_hashMapTypeOSM.put("water_austria_polylines.brook", 24);
		_hashMapTypeOSM.put("water_austria_polylines.fairway", 25);
		_hashMapTypeOSM.put("water_austria_polylines.stream; riverbank", 26);
		
		
		
		_hashMapTypeOSM.put("water_austria_polygons.waterfall", 30);
		_hashMapTypeOSM.put("water_austria_polygons.weir", 31);
		_hashMapTypeOSM.put("water_austria_polygons.reservoir", 32);
		_hashMapTypeOSM.put("water_austria_polygons.water", 33);
		_hashMapTypeOSM.put("water_austria_polygons.lock gate", 34);
		_hashMapTypeOSM.put("water_austria_polygons.riverbank", 35);
		_hashMapTypeOSM.put("water_austria_polygons.dock", 36);
		_hashMapTypeOSM.put("water_austria_polygons.stream", 37);
		_hashMapTypeOSM.put("water_austria_polygons.drain", 38);
		_hashMapTypeOSM.put("water_austria_polygons.canal", 39);
		_hashMapTypeOSM.put("water_austria_polygons.lock", 40);
		_hashMapTypeOSM.put("water_austria_polygons.pond", 41);
		_hashMapTypeOSM.put("water_austria_polygons.river", 42);
		_hashMapTypeOSM.put("water_austria_polygons.dam", 43);
		_hashMapTypeOSM.put("water_austria_polygons.ditch", 44);
		_hashMapTypeOSM.put("water_austria_polygons.boatyard", 45);
		_hashMapTypeOSM.put("water_austria_polygons.stream; riverbank", 46);
		_hashMapTypeOSM.put("water_austria_polygons.lake", 47);

	}
	
	private void init_FillColor() {
		_hashMapFillColor.put(ConstantsId.residential, Color.white);
		_hashMapFillColor.put(ConstantsId.retail, Color.gray);
		_hashMapFillColor.put(ConstantsId.industrial, Color.darkGray);
		_hashMapFillColor.put(ConstantsId.commercial, Color.black);
	
		//
		_hashMapFillColor.put(ConstantsId.rail, new Color(51,0,102));
		_hashMapFillColor.put(ConstantsId.tram, Color.magenta);
		_hashMapFillColor.put(ConstantsId.subway, Color.red);
		
		_hashMapFillColor.put(ConstantsId.school, Color.white);
		_hashMapFillColor.put(ConstantsId.bank, Color.green);
		_hashMapFillColor.put(ConstantsId.doctors, Color.orange);
		_hashMapFillColor.put(ConstantsId.pharmacy, Color.green);
		_hashMapFillColor.put(ConstantsId.police, Color.BLUE);
		_hashMapFillColor.put(ConstantsId.university, Color.magenta);
		_hashMapFillColor.put(ConstantsId.post_office, Color.cyan);
		

		
		_hashMapFillColor.put(1112, Color.BLUE);
		_hashMapFillColor.put(233, Color.white);
		_hashMapFillColor.put(931, Color.red);
		_hashMapFillColor.put(932, Color.orange);
		_hashMapFillColor.put(933, Color.black);
		_hashMapFillColor.put(1101, Color.black);
		
		int i;
		for(i=0; i < 27; i++)
			_hashMapFillColor.put(i, Color.BLUE);
		
		for(i= 30; i< 47; i++)
			_hashMapFillColor.put(i, Color.cyan);
		
		
		
	}
	
	private void init_BorderColor() {
		_hashMapBorderColor.put(ConstantsId.residential, Color.black);
		_hashMapBorderColor.put(ConstantsId.retail, Color.black);
		_hashMapBorderColor.put(ConstantsId.industrial, Color.black);
		_hashMapBorderColor.put(ConstantsId.commercial, Color.black);
		
		
		_hashMapBorderColor.put(1112, Color.green);
		_hashMapBorderColor.put(233, Color.black);
		_hashMapBorderColor.put(931, Color.red);
		_hashMapBorderColor.put(932, Color.orange);
		_hashMapBorderColor.put(933, Color.black);
		_hashMapBorderColor.put(1101, Color.black);
		
	}
	
	
	
}
