package evolutionaryGames;

import observer.Observer;
import sim.engine.SimState;
import sim.util.Bag;
import sweep.ParameterSweeper;
import sweep.SimStateSweep;

public class Experimenter extends Observer {
	public int cooperators = 0;
	public int defectors = 0;
	public int walkaways = 0;
	public int walkawaysD = 0;
	public int tftMobile = 0;
	public int tftStationary = 0;
	public int pavlovMobile = 0;
	public int pavlovStationary = 0;
	
	public void stop(Environment state) {
		Bag agents = state.sparseSpace.getAllObjects();
		if(agents == null || agents.numObjs == 0) {
			event.stop();
		}
	}
	

	public void countStrategies(Environment state) {
		Bag agents = state.sparseSpace.getAllObjects();
		for(int i=0;i<agents.numObjs;i++) {
			Agent a =(Agent)agents.objs[i];
			switch(a.strategy) {
			case COOPERATOR:
				cooperators ++;
				break;
			case DEFECTOR:
				defectors++;
				break;
			case WALKAWAY:
				walkaways++;
				break;
			case WALKAWAYD:
				walkawaysD++;
				break;
			case TFTM:
				tftMobile++;
				break;
			case TFTS:
				tftStationary++;
				break;
			case PAVLOVM:
				pavlovMobile++;
				break;
			case PAVLOVS:
				pavlovStationary++;
				break;
			}
		}
	}

	public boolean reset(SimState state) {
		super.reset();
		cooperators =0;
		defectors = 0;
		walkaways = 0;
		walkawaysD = 0;
		tftMobile = 0;
		tftStationary = 0;
		pavlovMobile = 0;
		pavlovStationary = 0;
		return true;
	}
	
	public boolean nextInterval() {
		double total = cooperators+defectors+walkaways;
		data.add(total);
		data.add(cooperators/total);
		data.add(defectors/total);
		data.add(walkaways/total);
		data.add(walkawaysD/total);
		data.add(tftMobile/total);
		data.add(tftStationary/total);
		data.add(pavlovMobile/total);
		data.add(pavlovStationary/total);
		return false;
	}

	public Experimenter(String fileName, String folderName, SimStateSweep state, ParameterSweeper sweeper,
			String precision, String[] headers) {
		super(fileName, folderName, state, sweeper, precision, headers);

	}
	
	public void upDatePopulation(Environment state) {
		Bag agents = state.sparseSpace.getAllObjects();
		for(int i=0;i<agents.numObjs;i++) {
			Agent a = (Agent)agents.objs[i];
			a.played=false;
		}
	}

	public void step(SimState state) {
		super.step(state);
		upDatePopulation((Environment) state);
		reset(state);
		countStrategies((Environment) state);
		nextInterval();
	}
}
