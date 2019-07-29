package evolutionaryGames;

import java.util.ArrayList;

import sim.util.Int2D;
import sweep.SimStateSweep;

public class Environment extends SimStateSweep {

	public int cooperators = 500;
	public int defectors = 500;
	public int walkaways = 0;
	public int walkawayD = 0;
	public int tftMobile = 0;
	public int tftStationary = 0;
	public int pavlovMobile = 0;
	public int pavlovStationary = 0;
	public int memorySize = 7; //MAY NEED TO BE CHANGED
	public double p = 0.1; //probability of random movement
	public boolean locationEmpty = true; //Must a location be empty before an agent can move into it?
	
	public double mutationRate = 0.0;
	public double resoucesToReproduce = 100;
	public double minResourcesStart = 0;
	public double maxResourcesStart = 50;
	public double carryingCapacity = 1000;
	public double parentalInvestment = 0.5;
	public int searchOpponent = 1;
	public double cooperate_cooperator = 3;
	public double defect_defector = 0;
	public double cooperate_defector = -1;
	public double defect_cooperator = 5;
	public int reproductionRadius = 1;
	public boolean localReproduction = true;
	public ArrayList<Strategy> mutationList = new ArrayList();
	public boolean mutationRange = true;
	
	public int getCooperators() {
		return cooperators;
	}

	public void setCooperators(int cooperators) {
		this.cooperators = cooperators;
	}

	public int getDefectors() {
		return defectors;
	}

	public void setDefectors(int defectors) {
		this.defectors = defectors;
	}

	public int getWalkaways() {
		return walkaways;
	}

	public void setWalkaways(int walkaways) {
		this.walkaways = walkaways;
	}

	public int getWalkawayD() {
		return walkawayD;
	}

	public void setWalkawayD(int walkawayD) {
		this.walkawayD = walkawayD;
	}

	public int getTftMobile() {
		return tftMobile;
	}

	public void setTftMobile(int tftMobile) {
		this.tftMobile = tftMobile;
	}

	public int getTftStationary() {
		return tftStationary;
	}

	public void setTftStationary(int tftStationary) {
		this.tftStationary = tftStationary;
	}

	public int getPavlovMobile() {
		return pavlovMobile;
	}

	public void setPavlovMobile(int pavlovMobile) {
		this.pavlovMobile = pavlovMobile;
	}

	public int getPavlovStationary() {
		return pavlovStationary;
	}

	public void setPavlovStationary(int pavlovStationary) {
		this.pavlovStationary = pavlovStationary;
	}

	public int getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public boolean isLocationEmpty() {
		return locationEmpty;
	}

	public void setLocationEmpty(boolean locationEmpty) {
		this.locationEmpty = locationEmpty;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public double getResoucesToReproduce() {
		return resoucesToReproduce;
	}

	public void setResoucesToReproduce(double resoucesToReproduce) {
		this.resoucesToReproduce = resoucesToReproduce;
	}

	public double getMinResourcesStart() {
		return minResourcesStart;
	}

	public void setMinResourcesStart(double minResoucesStart) {
		this.minResourcesStart = minResoucesStart;
	}

	public double getMaxResourcesStart() {
		return maxResourcesStart;
	}

	public void setMaxResourcesStart(double maxResourcesStart) {
		this.maxResourcesStart = maxResourcesStart;
	}

	public double getCarryingCapacity() {
		return carryingCapacity;
	}

	public void setCarryingCapacity(double carryingCapacity) {
		this.carryingCapacity = carryingCapacity;
	}

	public double getParentalInvestment() {
		return parentalInvestment;
	}

	public void setParentalInvestment(double parentalInvestment) {
		this.parentalInvestment = parentalInvestment;
	}

	public int getSearchOpponent() {
		return searchOpponent;
	}

	public void setSearchOpponent(int searchOpponent) {
		this.searchOpponent = searchOpponent;
	}

	public double getCooperate_cooperator() {
		return cooperate_cooperator;
	}

	public void setCooperate_cooperator(double cooperate_cooperator) {
		this.cooperate_cooperator = cooperate_cooperator;
	}

	public double getDefect_defector() {
		return defect_defector;
	}

	public void setDefect_defector(double defect_defector) {
		this.defect_defector = defect_defector;
	}

	public double getCooperate_defector() {
		return cooperate_defector;
	}

	public void setCooperate_defector(double cooperate_defector) {
		this.cooperate_defector = cooperate_defector;
	}

	public double getDefect_cooperator() {
		return defect_cooperator;
	}

	public void setDefect_cooperator(double defect_cooperator) {
		this.defect_cooperator = defect_cooperator;
	}

	public int getReproductionRadius() {
		return reproductionRadius;
	}

	public void setReproductionRadius(int reproductionRadius) {
		this.reproductionRadius = reproductionRadius;
	}

	public boolean isLocalReproduction() {
		return localReproduction;
	}

	public void setLocalReproduction(boolean localReproduction) {
		this.localReproduction = localReproduction;
	}

	public Environment(long seed, Class observer) {
		super(seed, observer);
		// TODO Auto-generated constructor stub
	}	

	public Int2D uniqueXY() {
		int x = random.nextInt(gridWidth);
		int y = random.nextInt(gridHeight);
		while(sparseSpace.getObjectsAtLocation(x, y)!= null) {
			x = random.nextInt(gridWidth);
			y = random.nextInt(gridHeight);
		}
		return new Int2D(x,y);
	}
	
	public Int2D locationXY() {
		int x = random.nextInt(gridWidth);
		int y = random.nextInt(gridHeight);
		return new Int2D(x,y);
	}

	public void makeMutationList() {
		if(cooperators > 0) {
			mutationList.add(Strategy.COOPERATOR);
		}
		if(defectors > 0) {
			mutationList.add(Strategy.DEFECTOR);
		}
		if(walkaways > 0) {
			mutationList.add(Strategy.WALKAWAY);
		}
		if(walkawayD > 0) {
			mutationList.add(Strategy.WALKAWAYD);
		}
		if(tftMobile > 0) {
			mutationList.add(Strategy.TFTM);
		}
		if(tftStationary > 0) {
			mutationList.add(Strategy.TFTS);
		}
		if(pavlovMobile > 0) {
			mutationList.add(Strategy.PAVLOVM);
		}
		if(pavlovStationary > 0) {
			mutationList.add(Strategy.PAVLOVS);
		}
}
	
	public void makeAgents() { 
		if(locationEmpty) {
			int total =  cooperators + defectors + walkaways + walkawayD + 
					tftMobile + tftStationary + pavlovMobile + pavlovStationary;
			int total2 = gridWidth * gridHeight;
			if(total > total2) {
				System.out.println("Too many agents");
				return;
			}
		}
		for(int i=0;i<cooperators;i++) {
			Int2D location;
			if(locationEmpty)
				location = uniqueXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			long id = random.nextLong();
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			Agent agent = new Agent(this,id,Strategy.COOPERATOR,resources,location.x,location.y,xdir,ydir);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,random.nextInt(gridWidth), random.nextInt(gridHeight));
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<defectors;i++) {
			Int2D location;
			if(locationEmpty)
				location = uniqueXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			long id = random.nextLong();
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			Agent agent = new Agent(this, id,Strategy.DEFECTOR,resources,location.x,location.y,xdir,ydir);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,random.nextInt(gridWidth), random.nextInt(gridHeight));
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<walkaways;i++) {
			Int2D location;
			if(locationEmpty)
				location = uniqueXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			long id = random.nextLong();
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			Agent agent = new Agent(this,id,Strategy.WALKAWAY,resources,location.x,location.y,xdir,ydir);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,random.nextInt(gridWidth), random.nextInt(gridHeight));
			agent.colorByStrategy(agent.strategy, this, agent);
		}

		for(int i=0;i<walkawayD;i++) {
			Int2D location;
			if(locationEmpty)
				location = uniqueXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			long id = random.nextLong();
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			Agent agent = new Agent(this,id,Strategy.WALKAWAYD,resources,location.x,location.y,xdir,ydir);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,random.nextInt(gridWidth), random.nextInt(gridHeight));
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<tftMobile;i++) { 
			Int2D location;
			if(locationEmpty)
				location = uniqueXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			long id = random.nextLong();
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			Agent agent = new Agent(this,id,Strategy.TFTM,resources,location.x,location.y,xdir,ydir);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,random.nextInt(gridWidth), random.nextInt(gridHeight));
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<tftStationary;i++) {
			Int2D location;
			if(locationEmpty)
				location = uniqueXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			long id = random.nextLong();
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			Agent agent = new Agent(this,id,Strategy.TFTS,resources,location.x,location.y,xdir,ydir);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,random.nextInt(gridWidth), random.nextInt(gridHeight));
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<pavlovMobile;i++) {
			Int2D location;
			if(locationEmpty)
				location = uniqueXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			long id = random.nextLong();
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			Agent agent = new Agent(this,id,Strategy.PAVLOVM,resources,location.x,location.y,xdir,ydir);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,random.nextInt(gridWidth), random.nextInt(gridHeight));
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<pavlovStationary;i++) {
			Int2D location;
			if(locationEmpty)
				location = uniqueXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			long id = random.nextLong();
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			Agent agent = new Agent(this,id,Strategy.PAVLOVS,resources,location.x,location.y,xdir,ydir);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,random.nextInt(gridWidth), random.nextInt(gridHeight));
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		makeMutationList();
	}

	public void start() {
		super.start();
		makeSpace(gridWidth,gridHeight);
		makeAgents();
		if(observer != null)
			observer.initialize(space, spaces);
	}
}
