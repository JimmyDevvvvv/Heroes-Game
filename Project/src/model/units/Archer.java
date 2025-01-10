package model.units;

import exceptions.InvalidActionException;

public class Archer extends SupportUnit {

	public Archer() {
		super(100, 2, 3, 35);
	}
	
	@Override
	public void upgrade() throws InvalidActionException {
		super.upgrade();
		this.setRange(this.getRange() + 1);
	}

}
