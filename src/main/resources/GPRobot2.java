package gp;
import robocode.*;
import java.awt.Color;
import java.io.*;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * JaviRobot - a robot by (your name here)
 */
public class GPRobot extends AdvancedRobot
{

	int hitWallsCounter=0;
	boolean[][] reachedSpaces=new boolean[10][10];
	int bulletHitAnotherRobot=0;
	int scannedRobot=0;
	int bulletHitMissed=0;
	int hitByBullet=0;

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

	public void onHitWall(HitWallEvent event) {
		hitWallsCounter=hitWallsCounter+1;
	}

	public void onRoundEnded(RoundEndedEvent event){
		PrintStream w = null;
		try {
			w = new PrintStream(new RobocodeFileOutputStream("/home/jfernandez/robocode/robots/gp/GPRobot.data/battleData.dat",true));
			String roundNum=String.valueOf(getRoundNum());
			w.println("Round "+roundNum);
			w.println("Hit_wall "+hitWallsCounter);
			w.println("Total_turns "+event.getTurns());
			w.println("Scanned_robots "+scannedRobot);
			w.println("Bullet_hit_another_robot "+bulletHitAnotherRobot);
			w.println("Bullet_hit_missed "+bulletHitMissed);
			w.println("Hit_by_bullet "+hitByBullet);
			int reachedSpacesNum=0;
			for(int i=0;i<10;i++){
				for(int j=0;j<10;j++){
					if(reachedSpaces[i][j]){
						reachedSpacesNum=reachedSpacesNum+1;
					}
				}
			}
			w.println("Reached_spaces "+reachedSpacesNum);
			w.flush();
			w.close();
		} catch (IOException exception) {
			out.println("Impossible to write hit wall");
		} finally {
			if (w != null) {
				w.close();
			}
		}
	}

	public void onStatus(StatusEvent event){
		double cellWidth=getBattleFieldWidth()/10;
		double cellHeight=getBattleFieldHeight()/10;
		int posRobotWidth=(int)(getX()/cellWidth);
		int posRobotHeight=(int)(getY()/cellHeight);
		reachedSpaces[posRobotWidth][posRobotHeight]=true;
	}

	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedRobot(ScannedRobotEvent event) {
		scannedRobot=scannedRobot+1;
		/* ON_SCANNED_ROBOT_CODE */

		// Call scan again, before we turn the gun
		scan();
	}

	public void onBulletHit(HitWallEvent event) {
		bulletHitAnotherRobot=bulletHitAnotherRobot+1;
	}

	public void onBulletMissed(BulletMissedEvent event) { bulletHitMissed=bulletHitMissed+1; }

	public void onHitByBullet(HitByBulletEvent event) { hitByBullet=hitByBullet+1; }


}
