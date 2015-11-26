package StarcraftAI;
import java.awt.List;
import java.util.ArrayList;

import bwapi.*;

/**
 * BuildingManager Class
 * 	Responsible for managing all the buildings under the agent�s control
 *  
 * @author Kenny Trowbridge
 * @author Casey Sigelmann
 *
 */
public class BuildingManager extends ProductionManager{

	private ArrayList<Unit> buildingList;
	
	public BuildingManager(){ }
	
	/**
	 * addUnit
	 * Adds a given unit the list of buildings if it does not already exist.
	 * 
	 * @param unit - unit to add to the list
	 */
	public void addUnit(Unit unit)
	{
		buildingList.add(unit);
	}
	
	/**
	 * build
	 * Builds a unit of the given type with the builder unit
	 * 
	 * @param buildingType - unit type to build
	 * @param builder - unit used to build
	 */
	public void build(UnitType buildingType, Unit builder)
	{ 
		TilePosition placement = getPlacement(buildingType);
		if(placement != null)
		{
			builder.build(placement, buildingType);
		}
	}
	
	/**
	 * getPlacement()
	 * Finds the best location to place a given type of building
	 * 
	 * @param buildingType - type of building to be placed
	 * @return TilePosition - returns the location to place the building
	 */
	private TilePosition getPlacement(UnitType buildingType)
	{
    	int maxDist = 3;
    	int stopDist = 40;
    	TilePosition aroundTile = self.getStartLocation();
    	
    	// loop until we find the thing
    	while((maxDist < stopDist))
    	{
    		// loop through the defined area
    		for(int i = aroundTile.getX()-maxDist; i <= aroundTile.getX()+maxDist; i++)
    		{
    			for(int j = aroundTile.getY()-maxDist; j <= aroundTile.getY()+maxDist; j++)
    			{
    				if(game.canBuildHere(null, new TilePosition(i,j), buildingType, false))
    				{
    					return new TilePosition(i,j);
    				}
    			}
    		}
    		// we didn't find a valid tile, so increase max distance
    		maxDist+=2;
    	}
    	
    	game.printf("Unable to find suitable build position for "+buildingType.toString());
    	return null;
	}
	
	/**
	 * update()
	 * This updates the building list in order to prune dead units
	 */
	public void update()
	{
		for(Unit building : buildingList)
		{
			if (!building.exists())
			{
				buildingList.remove(building);
			}
		}
	}
	
	/**
	 * checkBuildings()
	 * Checks the buildings list and returns a list of buildings that are 
	 * damaged or incomplete
	 * 
	 * @return - list of damaged or incomplete buildings
	 */
	public List checkBuildings()
	{
		return null;
	}
	
	/**
	 * getBuilding()
	 * Finds a building of the given building type
	 * 
	 * @param building - type of building to find
	 * @return - building
	 */
	public Unit getBuilding(UnitType buildingType)
	{
		for(Unit building : buildingList)
		{
			if(building.getType() == buildingType)
			{
				return building;
			}
		}
		return null;
	}
}
