import com.mashibing.netty.Client;
import com.mashibing.tank.*;


public class Main {

    public static void main(String[] args) {

        TankFrame tf=TankFrame.INSTANCE;
        tf.setVisible(true);

        /**
         *         int initTankCount =Integer.parseInt((String)PropertyMgr.get("initTankCount")) ;
        //初始化敌方坦克
        for(int i=0;i<initTankCount;i++){
            tf.tanks.add(new Tank(50+i*80,200, Dir.DOWN, Group.BAD,tf));
        }


        while(true){
            try {
                Thread.sleep(50);
                tf.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         */

        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

       Client.INSTANCE.connect();
    }
}
