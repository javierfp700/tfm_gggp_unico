package gp;
import robocode.*;
import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * JaviRobot - a robot by (your name here)
 */
public class GPRobot extends AdvancedRobot
{
	/**
	 * run: JaviRobot's default behavior
	 */
	public void run() {
		setBodyColor(Color.red);
		setGunColor(Color.black);
		setRadarColor(Color.yellow);
		setBulletColor(Color.green);
		setScanColor(Color.green);

		// Robot main loop
		while(true) {
			/* RUN_CODE */
		}
	}

	/* This method is called when one of your bullets hits another robot */
	public void onBulletHit(BulletHitEvent event){
		/* ON_BULLET_HIT_CODE */
	}

	/* This method is called when one of your bullets hits another bullet */
	public void onBulletHitBullet(BulletHitBulletEvent event){
		/* ON_BULLET_HIT_BULLET_CODE */
	}

	/* This method is called when one of your bullets misses, i.e. hits a wall */
	public void onBulletMissed(BulletMissedEvent event){
		/* ON_BULLET_MISSED_CODE */
	}

	/* This method is called when your robot is hit by a bullet */
	public void onHitByBullet(HitByBulletEvent event) {
		/* ON_HIT_BY_BULLET_CODE */
	}

	/* This method is called when your robot collides with another robot */
	public void onHitRobot(HitRobotEvent event){
		/* ON_HIT_ROBOT_CODE */
	}

	/* This method is called when your robot collides with a wall */
	public void onHitWall(HitWallEvent event) {
		/* ON_HIT_WALL_CODE */
	}

	/* This method is called when your robot sees another robot, i.e. when the robot's radar scan "hits" another robot */
	public void onScannedRobot(ScannedRobotEvent event) {
		/* ON_SCANNED_ROBOT_CODE */
	}



}
