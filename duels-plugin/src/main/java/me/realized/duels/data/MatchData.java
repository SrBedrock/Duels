package me.realized.duels.data;

import me.realized.duels.api.user.MatchInfo;

public class MatchData implements MatchInfo {

	private String winner;
	private String loser;
	private String kit;
	private long time;
	private long duration;
	private double health;

	public MatchData(final String winner, final String loser, final String kit, final long time, final long duration, final double health) {
		this.winner = winner;
		this.loser = loser;
		this.kit = kit;
		this.time = time;
		this.duration = duration;
		this.health = health;
	}

	public String getWinner() {
		return winner;
	}

	public String getLoser() {
		return loser;
	}

	public String getKit() {
		return kit;
	}

	public long getTime() {
		return time;
	}

	public long getDuration() {
		return duration;
	}

	public double getHealth() {
		return health;
	}

	@Override
	public long getCreation() {
		return time;
	}

	@Override
	public String toString() {
		return "MatchData{" +
				"winner='" + winner + '\'' +
				", loser='" + loser + '\'' +
				", time=" + time +
				", duration=" + duration +
				", health=" + health +
				'}';
	}

}