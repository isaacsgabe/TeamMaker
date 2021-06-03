package personal.project.objects;

import java.util.Objects;

public class Player implements Comparable<Player> {
	private String name;
	private double skillLevel;
	private String position;
	private int positionTotal;

	public Player(String name, double skillLevel) {
		this.name = name;
		this.skillLevel = skillLevel;
	}
	public Player() {
		this.name = null;
		this.skillLevel = 0;
		this.position = null;
		this.positionTotal =0;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSkillLevel() {
		return this.skillLevel;
	}

	public void setSkillLevel(double skillLevel) {
		this.positionTotal++;
		this.skillLevel += skillLevel;
		this.skillLevel = this.skillLevel/positionTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, skillLevel);
	}

	@Override
	public boolean equals(Object o) {
		if (o==null) {
			return false;
		}
		if (this==o) {
			return true;
		}
		if (!(o instanceof Player)) {
			return false;
		}
		return this.name.equals(((Player)o).name);
	}

	@Override
	public int compareTo(Player o) {
		if (o==null) {
			throw new IllegalArgumentException();
		}
		Double a = this.skillLevel;
		Double b = o.skillLevel;
		return b.compareTo(a);
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}