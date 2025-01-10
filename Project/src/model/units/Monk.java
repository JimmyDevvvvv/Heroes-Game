package model.units;

import exceptions.InvalidActionException;
import exceptions.InvalidTargetException;

public class Monk extends Hero {

	public Monk(String name, int maxHp, int maxActions, int range) {
		super(name, HeroType.PACIFIST, maxHp, maxActions, range);
	}

	@Override
	public void useSpecial(SupportUnit u) throws InvalidActionException {
		u.setCurrentHp(u.getMaxHp());
		setSpecialActionCooldown(2);
	}

}
