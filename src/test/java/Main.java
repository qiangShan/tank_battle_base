import com.mashibing.tank.*;


public class Main {

    public static void main(String[] args) {

        TankFrame tf=new TankFrame();

        int initTankCount =Integer.parseInt((String)PropertyMgr.get("initTankCount")) ;


        //初始化敌方坦克
        for(int i=0;i<initTankCount;i++){
            tf.tanks.add(tf.gf.createTank(50+i*80,200, Dir.DOWN, Group.BAD,tf));
        }

        while(true){
            try {
                Thread.sleep(50);
                tf.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
