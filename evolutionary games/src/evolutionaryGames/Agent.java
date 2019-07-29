package evolutionaryGames;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Int2D;

public class Agent implements Steppable {
	public Stoppable event;
	long id;
	Strategy strategy;
	double resources;
	boolean played = false;
	Memory memory = null;
	int x; //location on the x-axis
	int y; //location on the y-axis
	int xdir; //x direction of change
	int ydir; //y direction of change

	public Agent(Environment state, long id, Strategy strategy, double resources, int x, int y, int xdir, int ydir) {
		super();
		this.id = id;
		this.strategy = strategy;
		this.resources = resources;
		this.x = x;
		this.y = y;
		this.xdir = xdir;
		this.ydir = ydir;
		switch(strategy) {
		case TFTM:
		case TFTS:
		case PAVLOVM: 
		case PAVLOVS: 
			memory = new Memory(state.memorySize);
		}	
	}
	
	public Strategy getStrategy(Agent opponent) {
		switch(strategy) {
		case COOPERATOR:
			return Strategy.COOPERATOR;
		case DEFECTOR:
			return Strategy.DEFECTOR;
		case WALKAWAY:
			return Strategy.COOPERATOR;
		case WALKAWAYD:
			return Strategy.DEFECTOR; 
		case TFTM:
			return getStrategyTFT(opponent);
		case TFTS:
			return getStrategyTFT(opponent);
		case PAVLOVM:
			return getStrategyPAVLOV(opponent);
		case PAVLOVS:
			return getStrategyPAVLOV(opponent);
		default:
			return Strategy.COOPERATOR;
		}
	}
	
	public Strategy getStrategyTFT(Agent opponent) {
        Triple m = memory.getLastMemory(opponent);
        if(m == null) {
            return Strategy.COOPERATOR;
        }
        switch(m.opponentStrategy) {
        case COOPERATOR:
            return Strategy.COOPERATOR;
        case DEFECTOR:
            return Strategy.DEFECTOR;
        default:
            return Strategy.COOPERATOR;
        }
    }
	
	public Strategy getStrategyPAVLOV(Agent opponent) {
		Triple m = memory.getLastMemory(opponent);
        if(m == null) {
            return Strategy.COOPERATOR;
        }
        switch(m.opponentStrategy) {
        case COOPERATOR:
            switch(this.strategy) {
            case COOPERATOR:
            		return Strategy.COOPERATOR;
            case DEFECTOR:
            		return Strategy.DEFECTOR;
            	default:
            		return Strategy.COOPERATOR; //IDFK
            }
        case DEFECTOR:
        		switch(this.strategy) {
            case COOPERATOR:
            		return Strategy.DEFECTOR;
            case DEFECTOR:
            		return Strategy.COOPERATOR;
            	default:
            		return Strategy.COOPERATOR; //IDFK
            }
        default:
            return Strategy.COOPERATOR; //IDFK
        }
	}
		
	public Strategy play(Environment state, Agent opponent) { //THIS MIGHT WORK NOW? MAYBE
		Strategy myStrategy = getStrategy(opponent);
		Strategy myOpponentStrategy = opponent.getStrategy(this);
		switch(myOpponentStrategy) {
		case COOPERATOR:
			switch(myStrategy) {
			case COOPERATOR:
				resources += state.cooperate_cooperator;
				break;
			case DEFECTOR:
				resources += state.defect_cooperator;
				break;
			}
			break;
		case DEFECTOR:
			switch(myStrategy) {
			case COOPERATOR:
				resources += state.cooperate_defector;
				break;
			case DEFECTOR:
				resources += state.defect_defector;
				break;
			}
			break;
		}
		this.played = true;
		switch(this.strategy) {
		case TFTM:
		case TFTS:
		case PAVLOVM:
		case PAVLOVS:
			this.memory.storeMemory(opponent, myOpponentStrategy, myStrategy);
			break;
		default:
			break;
		}
		return myOpponentStrategy;
	}

	public void mobileStrategy(Environment state) {
		Bag agents = search(state);
		if(agents == null || agents.numObjs == 0) 
		{
			if(state.random.nextBoolean(state.p)) {
				xdir = state.random.nextInt(3)-1;
				ydir = state.random.nextInt(3)-1;
			}
			placeAgent( state);
			agents = search(state);
			if(agents == null) {return;}
			Agent opponent = findOpponent(state, agents);
			if(opponent == null) {return;}
			play(state, opponent);
			opponent.play(state, this);
		}
		else {
			Agent opponent = findOpponent(state, agents);
			if(opponent == null) {return;}
			play(state, opponent);
			opponent.play(state, this);
		}
	}

	public void walkawayStrategy(Environment state) {
		Bag agents = search(state);
		Agent opponent;
		Strategy opponentStrategy;
		if(agents == null || agents.numObjs == 0) {
			if(state.random.nextBoolean(state.p)) {
				xdir = state.random.nextInt(3)-1;
				ydir = state.random.nextInt(3)-1;
			}
			placeAgent( state);
			agents = search(state);
			if(agents == null) {return;}
			opponent = findOpponent(state, agents);
			if(opponent == null) {return;}
			opponentStrategy = play(state, opponent);
			opponent.play(state, this);
		}
		else {
			opponent = findOpponent(state, agents);
			if(opponent == null) {return;}
			opponentStrategy = play(state, opponent);
			opponent.play(state, this);
		}
		if(opponentStrategy == Strategy.DEFECTOR) { //walk away
			if(state.random.nextBoolean(state.p)) {
				xdir = state.random.nextInt(3)-1;
				ydir = state.random.nextInt(3)-1;
			}
			placeAgent( state);
		}
	}
	
	public void stationaryStrategy(Environment state) {
		Bag agents = search(state);
		Agent opponent;
		if(agents == null || agents.numObjs == 0) {
			return;
		}
		else {
			opponent = findOpponent(state, agents);
			if(opponent == null) {return;}
			play(state, opponent);
			opponent.play(state, this);
		}
	}

	
	public void play(Environment state) {
		switch(strategy) {
		case COOPERATOR:
		case DEFECTOR:
		case TFTM:
		case PAVLOVM:
			mobileStrategy(state);
			break;
		case WALKAWAY: 
		case WALKAWAYD:
			walkawayStrategy(state);
			break;
		case TFTS:
		case PAVLOVS:
			stationaryStrategy(state);
			break;
		}
	}

	public Agent findOpponent(Environment state, Bag agents) {
		if(agents == null || agents.numObjs == 0) return null;
		int r = state.random.nextInt(agents.numObjs);
		for(int i=r;i<agents.numObjs;i++) {
			Agent a = (Agent)agents.objs[i];
			if(!a.played && !a.equals(this)) {
				return a;
			}
		}
		for(int i=0;i<r;i++) {
			Agent a = (Agent)agents.objs[i];
			if(!a.played && !a.equals(this)) {
				return a;
			}
		}
		return null;
	}


	public Strategy mutation(Environment state) {
		Strategy newStrategy;
		if(state.mutationRange) {
			if(state.random.nextBoolean(state.mutationRate)) {
				newStrategy = state.mutationList.get(state.random.nextInt(state.mutationList.size()));
				while(newStrategy == this.strategy) { //find a strategy different from the parent
					newStrategy = state.mutationList.get(state.random.nextInt(state.mutationList.size()));
				}
				return newStrategy;
			}
			else {
				return this.strategy; //the current agent
			}
		}
		else {
			if(state.random.nextBoolean(state.mutationRate)) {
				newStrategy = Strategy.values()[state.random.nextInt(Strategy.values().length)];
				while(newStrategy == this.strategy) { //find a strategy different from the parent
					newStrategy = Strategy.values()[state.random.nextInt(Strategy.values().length)];
				}
				return newStrategy;
			}
			else {
				return this.strategy; //the current agent
			}
		}
	}

	public Int2D findLocation(Environment state) {
		if(state.locationEmpty) {
			Int2D location = state.sparseSpace.getRandomEmptyMooreLocation(state, x, y, state.reproductionRadius, state.sparseSpace.TOROIDAL, false);
			return location;
		}
		else {
			if(state.reproductionRadius == 0) {
				return new Int2D(x,y);
			}
			else {
				int xch = state.random.nextInt(state.reproductionRadius+1);
				int ych = state.random.nextInt(state.reproductionRadius+1);
				int newx = state.sparseSpace.stx(x+xch);
				int newy = state.sparseSpace.sty(y+ych);
				return new Int2D(newx,newy);
			}
		}
	}

	public Agent replicate(Environment state) {
		Int2D location;
		if(state.localReproduction) {
			location = this.findLocation(state);
		}
		else {
			if(state.locationEmpty)
				location = state.uniqueXY();
			else
				location = state.locationXY();
		}
		if(location == null) {
			return null; //reproduction cannot proceed because there is not place for the agent
		}
		int xdir = state.random.nextInt(3)-1;
		int ydir = state.random.nextInt(3)-1;
		Strategy newStrategy = mutation(state);
		long newId = state.random.nextLong();
		double parentalInvestment = resources * state.parentalInvestment;
		resources -= parentalInvestment;
		Agent a = new Agent(state,newId, newStrategy,parentalInvestment,location.x,location.y,xdir,ydir);
		a.event = state.schedule.scheduleRepeating(a);
		state.sparseSpace.setObjectLocation(a, location.x, location.y);
		colorByStrategy(a.strategy, state,  a);
		return a;
	}

	public void colorByStrategy(Strategy strategy, Environment state, Agent a) {
		switch(strategy) {
		case COOPERATOR: 
			state.gui.setOvalPortrayal2DColor(a, (float)0, (float)0, (float)1, (float)1);  //blue
			break;
		case DEFECTOR:
			state.gui.setOvalPortrayal2DColor(a, (float)1, (float)0, (float)0, (float)1); //red
			break;
		case WALKAWAY:
			state.gui.setOvalPortrayal2DColor(a, (float)0, (float)1, (float)0, (float)1);  //green
			break;
		case WALKAWAYD:
			state.gui.setOvalPortrayal2DColor(a, (float)1, (float)0.6, (float)0, (float)1); //orange
			break;
		case TFTM:
			state.gui.setOvalPortrayal2DColor(a, (float)0.2, (float)1, (float)1, (float)1); //cyan
			break;
		case TFTS:
			state.gui.setOvalPortrayal2DColor(a, (float)0.2, (float)1, (float)1, (float)1); //cyan
			break;
		case PAVLOVM:
			state.gui.setOvalPortrayal2DColor(a, (float)0, (float)0.2, (float)0, (float)1); //dark green
			break;
		case PAVLOVS:
			state.gui.setOvalPortrayal2DColor(a, (float)0, (float)0.2, (float)0, (float)1); //dark green
			break;
		default:
			state.gui.setOvalPortrayal2DColor(a, (float)1, (float)1, (float)1, (float)1);  //white
			break;
		}
	}

	public void placeAgent(Environment state) {
		if(state.locationEmpty) {
			int tempx = state.sparseSpace.stx(x + xdir);
			int tempy = state.sparseSpace.sty(y + ydir);
			Bag b = state.sparseSpace.getObjectsAtLocation(tempx, tempy);
			if(b == null || b.numObjs == 0){
				x = tempx;
				y = tempy;
				state.sparseSpace.setObjectLocation(this, x, y);
			}
		}
		else {
			x = state.sparseSpace.stx(x + xdir);
			y = state.sparseSpace.sty(y + ydir);
			state.sparseSpace.setObjectLocation(this, x, y);
		}
	}

	public Bag search(Environment state) {
		Bag agents = null;
		if(state.searchOpponent == 0) {
			agents = state.sparseSpace.getObjectsAtLocation(x, y);
		}
		else {
			agents = state.sparseSpace.getMooreNeighbors(x, y, state.searchOpponent, state.sparseSpace.TOROIDAL,true);
		}
		if(agents == null)
			return null;
		agents.remove(this);
		return agents;
	}

	public void die(Environment state) {
		state.sparseSpace.remove(this);
		event.stop();
	}

	public void checkCarringCapacity(Environment state) {
		Bag agents = state.sparseSpace.getAllObjects();
		if(agents.numObjs<=state.carryingCapacity) {
			return;
		}
		int index = state.random.nextInt(agents.numObjs);
		Agent a = (Agent)agents.objs[index];
		a.die(state);
	}

	public void step(SimState state) {
		Environment eState = (Environment)state;
		if(resources <= 0) {
			die(eState);
			return;
		}
		if(resources >= eState.resoucesToReproduce) {
			replicate(eState);
			checkCarringCapacity(eState);
		}
		if(played) {return;} //if played
		play(eState);
	}

}
