package model.units;

public class Assassin extends Hero {
	
	private int attackDmg;

	public Assassin(String name, int maxHp, int maxActions,
			int range, int attackDmg) {
		super(name, HeroType.AGGRESSOR, maxHp, maxActions, range);
		this.attackDmg = attackDmg;
	}

	public int getAttackDmg() {
		return attackDmg;
	}

	@Override
	public void useSpecial(SupportUnit u) {
		u.setCurrentHp(0);
		setSpecialActionCooldown(3);
	}

}
