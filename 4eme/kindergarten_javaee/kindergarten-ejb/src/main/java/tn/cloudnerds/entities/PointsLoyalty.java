package tn.cloudnerds.entities;

public class PointsLoyalty {
	
	int Id ;
	int parentId ;
	int points;
	
	public PointsLoyalty() {
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public PointsLoyalty(int id, int parentId, int points) {
		Id = id;
		this.parentId = parentId;
		this.points = points;
	}
	
	

	
	

}
