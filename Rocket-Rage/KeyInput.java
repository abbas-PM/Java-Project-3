import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random; 

public class KeyInput extends KeyAdapter{
    
    private Main main; 
    private Player player;  
    private Random random; 
    private int[] baseCosts = new int[4]; 

    public KeyInput(Main main){
        this.main = main; 
        this.player = this.main.getPlayer(); 
        this.random = new Random(); 
        this.baseCosts[0] = 3; this.baseCosts[1] = 5; this.baseCosts[2] = 7; this.baseCosts[3] = 9;  
    }

    public void keyPressed(KeyEvent evt){

        int c = evt.getKeyCode(); 

        if (c == KeyEvent.VK_ESCAPE && (main.gameState == Main.STATE.GAME || main.gameState == Main.STATE.PAUSED)){
            if (main.paused){
                main.paused = false; 
                main.gameState = Main.STATE.GAME;
            }
            else {
                main.paused = true; 
                main.gameState = Main.STATE.PAUSED; 
            }
        }

        //Movements
        if (c == KeyEvent.VK_LEFT && !main.paused){
            if (!player.KeyDown[4]) main.getPlayer().setVelX(-player.getVelocity());
            else main.getPlayer().setVelX(-player.getVM());
            player.KeyDown[0] = true; 
            player.KeyDown[1] = false; 
        }

        if (c == KeyEvent.VK_RIGHT && !main.paused){
            if (!player.KeyDown[4]) main.getPlayer().setVelX(player.getVelocity());
            else main.getPlayer().setVelX(player.getVM());
            player.KeyDown[0] = false; 
            player.KeyDown[1] = true; 
         }

        if (c == KeyEvent.VK_UP && !main.paused){
            if (!player.KeyDown[4]) main.getPlayer().setVelY(-player.getVelocity());
            else main.getPlayer().setVelY(-player.getVM());
            player.KeyDown[2] = true; 
            player.KeyDown[3] = false; 
        }

        if (c == KeyEvent.VK_DOWN && !main.paused){
            if (!player.KeyDown[4]) main.getPlayer().setVelY(player.getVelocity());
            else main.getPlayer().setVelY(player.getVM());
            player.KeyDown[2] = false; 
            player.KeyDown[3] = true; 
        }

        if (c == KeyEvent.VK_SPACE && !main.paused){
            player.KeyDown[4] = true; 
            if (player.KeyDown[0]) main.getPlayer().setVelX(-player.getVM());
            if (player.KeyDown[1]) main.getPlayer().setVelX(player.getVM());
            if (player.KeyDown[2]) main.getPlayer().setVelY(-player.getVM());
            if (player.KeyDown[3]) main.getPlayer().setVelY(player.getVM());
        }

    }

    public void keyReleased(KeyEvent evt){

        int c = evt.getKeyCode();

        //Movements
        if(c == KeyEvent.VK_LEFT) player.KeyDown[0] = false;   
        if(c == KeyEvent.VK_RIGHT) player.KeyDown[1] = false;  
        if(c == KeyEvent.VK_UP) player.KeyDown[2] = false;  
        if(c == KeyEvent.VK_DOWN) player.KeyDown[3] = false;  
            
        //horizontal movement
        if((player.KeyDown[0] == false && player.KeyDown[1] == false)) main.getPlayer().setVelX(0);
        //vertical movement
        if((player.KeyDown[2] == false && player.KeyDown[3] == false)) main.getPlayer().setVelY(0);

        if (c == KeyEvent.VK_SPACE){
            player.KeyDown[4] = false; 
            if (player.KeyDown[0]) main.getPlayer().setVelX(-player.getVelocity());
            if (player.KeyDown[1]) main.getPlayer().setVelX(player.getVelocity());
            if (player.KeyDown[2]) main.getPlayer().setVelY(-player.getVelocity());
            if (player.KeyDown[3]) main.getPlayer().setVelY(player.getVelocity()); 
         }

        //Health powerUp is selected 
        if (c == KeyEvent.VK_W && !player.Enter2 && !main.paused){

            if (player.pSelected == 0){

                if (player.getPoints() >= player.getCosts()[0]){

                    player.setLives(player.getLives() + 3);
                    player.setPoints(player.getPoints() - player.getCosts()[0]);
                    player.setCosts(0, player.getCosts()[0] + 3);
                    player.pSelected = -1; 

                } else player.pSelected = -2; 

            } else player.pSelected = 0; 
        }  

        //Slow down powerUp is selected
        if (c == KeyEvent.VK_A && !player.Enter2 && !main.paused){

            if (player.pSelected == 1){

                if (player.getPoints() < player.getCosts()[1]){

                    player.pSelected = -2; 
                }

                else if (player.counter == 0 && player.getPoints() >= player.getCosts()[1]){

                    player.pSelected = -4; 
                }

                else{
                    
                    player.Enter1 = true; 
                    player.setPoints(player.getPoints() - player.getCosts()[1]);
                    player.setCosts(1, player.getCosts()[1] + 5); 
                    player.pSelected = -1; 
                }

            } else player.pSelected = 1; 

        }
        
        //Reset powerUp is selected
        if (c == KeyEvent.VK_S && !player.Enter2 && !main.paused){

            if (player.pSelected == 2){

                if (player.getPoints() >= player.getCosts()[2]){

                    int counter = 4; 
                    for (int i = 0; i < 4; i++){
                        if (player.getCosts()[i] == baseCosts[i]) counter--;  
                    }

                    if (counter == 0){
                        player.pSelected = -3;  
                    }

                    else if (counter == 1){
                        for (int i = 0; i < 4; i++){
                            if (player.getCosts()[i] != baseCosts[i]){
                                player.setPoints(player.getPoints() - player.getCosts()[2]);
                                player.setCosts(2, player.getCosts()[2] + 7);
                                player.setCosts(i, baseCosts[i]);
                                break; 
                            }
                        } 
                        player.pSelected = -1; 
                    }

                    else if (counter == 2 || counter == 3){
                        int randWay = random.nextInt(1, counter+1); 
                        int counter2 = 0; 
                        for(int i = 0; i < 4; i++){
                            if (player.getCosts()[i] != baseCosts[i]){
                                counter2++; 
                                if (counter2==randWay){
                                    player.setPoints(player.getPoints() - player.getCosts()[2]);
                                    player.setCosts(2, player.getCosts()[2] + 7);
                                    player.setCosts(i, baseCosts[i]);
                                    break; 
                                }
                            }
                        }
                        player.pSelected = -1; 
                    }

                    else if (counter == 4){
                        player.setPoints(player.getPoints() - player.getCosts()[2]);
                        int randWay = random.nextInt(0, 4);
                        player.setCosts(randWay, baseCosts[randWay]);
                        player.pSelected = -1; 
                    }

                } else player.pSelected = -2; 
                
            } else player.pSelected = 2; 
        }

        if (c == KeyEvent.VK_D && !main.paused){

            if (player.pSelected == 3 && !player.Enter2){

                if (player.getPoints() >= player.getCosts()[3]){
                    player.Enter2 = true; 
                    player.setPoints(player.getPoints() - player.getCosts()[3]);
                    player.setCosts(3, player.getCosts()[3] + 9);
                     
                } else player.pSelected = -2; 

            } else player.pSelected = 3; 
            
        } 
                
    }
        
    public void keyTyped(KeyEvent evt){}   
}