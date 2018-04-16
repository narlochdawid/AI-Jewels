
package jewels;

import Gems.JewelBlack;
import Gems.JewelBlue;
import Gems.JewelBrown;
import Gems.JewelGreen;
import Gems.JewelOrange;
import Gems.JewelRed;
import Gems.JewelViolet;
import Gems.JewelWhite;
import Gems.JewelYellow;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.abs;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jewels.Jewels.size;



public class Game extends Canvas implements Runnable {  
    private boolean running = false;
    public BufferedImageLoader ss = new BufferedImageLoader();
    private Thread thread;
    private int score = 0;
    Handler handler;
    PTR pointer;
    PTR pick;
    Graphics g;
    static int stopRender = 0;
    
    public int swap = 0;
    public int[][] gems;
    
    List<Solution> solutions = new ArrayList<Solution>();
    
    private TextBox totalScore;
    
    
    public Game(){
        handler = new Handler();
        try {
            ss.LoadImgs();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void keyPressed(KeyEvent e){

        int key = e.getKeyCode();
        g.setColor(Color.lightGray);
        int vx = pointer.getVX();
        int vy = pointer.getVY();
        g.fillRect(pointer.getX()*vx-vx, pointer.getY()*vy-vy,vx+1 ,vy+1);
        switch (key) {
            case KeyEvent.VK_A:
               
                if(checkBoarders(pointer.getX()-1, pointer.getY())==1)
                    pointer.setX(pointer.getX()-1);
                break;
            case KeyEvent.VK_D:
                if(checkBoarders(pointer.getX()+1, pointer.getY())==1)
                    pointer.setX(pointer.getX()+1);
                break;
            case KeyEvent.VK_W:
                if(checkBoarders(pointer.getX(), pointer.getY()-1)==1)
                    pointer.setY(pointer.getY()-1);
                break;
            case KeyEvent.VK_S:
                if(checkBoarders(pointer.getX(), pointer.getY()+1)==1)
                    pointer.setY(pointer.getY()+1);
                break;
            case KeyEvent.VK_SPACE:
                
                if(swap == 0){
                    pick.setX(pointer.getX());
                    pick.setY(pointer.getY());
                    swap = 1;
                }
                
                else if(swap == 1){
                    swap(pick.getX()-1, pick.getY()-1, pointer.getX()-1, pointer.getY()-1);
                    g.setColor(Color.lightGray);
                    g.fillRect(pick.getX()*vx-vx, pick.getY()*vy-vy,vx+1 ,vy+1);
                    swap = 0;
                    int prevscored;
                    
                    do{
                        prevscored = score;
                        score+=checkMap();
                        try {
                            Thread.sleep(200);
                                    } catch (InterruptedException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }while(score!=prevscored);
                    
                    totalScore.setTxt(Integer.toString(score));
                }  
                break;
            case KeyEvent.VK_F:
                findSolutions();
                break;
            case KeyEvent.VK_N:
            {
                try {
                    saveNewMap();
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
            case KeyEvent.VK_L:
            {
                try {
                    startLearning();
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case KeyEvent.VK_R:
            {
                try {
                    randomPlayer(100, 100);
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case KeyEvent.VK_I:
            {
                try {
                    try {
                        SIPlayer(50, 700);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
            default:
                break;
        }
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_A:
                break;
            case KeyEvent.VK_D:
                break;
            case KeyEvent.VK_W:
                break;
            case KeyEvent.VK_S:
                break;
            default:
                break;
        }
    }
    
    private void SIPlayer(int moves,int games) throws IOException, ClassNotFoundException{
        loadMap();

        List<Solution> learnList;
        List<int[][]> mpList;
        
        String map = "SI-"+games+"-games-"+size+"v"+size+".txt";
        FileWriter outputStream = new FileWriter("src/"+map);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("BestSolutions"+size+"_"+20+".ser"));
        learnList =  (List<Solution>) ois.readObject();
        ois.close();
        ois = new ObjectInputStream(new FileInputStream("src/Maps"+size+"_"+20+".ser"));
        mpList =  (List<int[][]>) ois.readObject();
        ois.close();
        
        int x = -1,y = -1,xt = -1,yt = -1;
        int found = 0;
        int prevscored;
        Random r = new Random();
        int i;
        double scored = 0;
        stopRender=1;
        CellNetwork cn = new CellNetwork(3*3*3*3, learnList, mpList);
        for(int b=0;b<500;b++)
            cn.uczSie(b);
        for(int k =0;k<games;k++){
            loadMap();
            score = 0;
            for(int j=0;j<moves;j++){
                
                int res = cn.dajWynik(gems);
                int tmp = res;
                if(res!=-1){
                    tmp = res/27;
                    x = tmp;
                    res = res - tmp*(27);
                    tmp = res/9;
                    y = tmp;
                    res = res - tmp*9;
                    tmp = res/3;
                    xt = tmp;
                    yt = res - tmp*3;
                }
                findSolutions();
                loadSolutions();
                found = 0;
                for(int a=0;a<solutions.size();a++){
                    int xtmp = solutions.get(a).getX();
                    int ytmp = solutions.get(a).getY();
                    int xttmp = solutions.get(a).getXt();
                    int yttmp = solutions.get(a).getYt();
                    if(x == xtmp && y == ytmp && xt == xttmp && yt == yttmp){
                        found =1;
                        System.out.println("znaleziono");
                    }
                }
                if(found !=1){
                    i = r.nextInt(solutions.size());
                    x = solutions.get(i).getX();
                    y = solutions.get(i).getY();
                    xt = solutions.get(i).getXt();
                    yt = solutions.get(i).getYt();
                }
                swap(x, y, xt, yt);
                do{
                    prevscored = score;
                    score+=checkMap();
                }while(score!=prevscored);
            }
            scored +=score;
            outputStream.write(String.valueOf(score));
            outputStream.write("\n");
        }
        score = 0;
        scored = scored/games;
        outputStream.write(String.valueOf(scored));
        outputStream.write("\n");
        outputStream.close();
        loadMap();
        stopRender=0;
    }
    
    public static void mouseClicked(MouseEvent e,Game game){
          int mx = e.getX();
          int my = e.getY();    
    }
    
    private void randomPlayer(int moves,int games) throws IOException, InterruptedException{
        loadMap();
        String map = "Random-"+games+"-moves-"+size+"v"+size+".txt";
        FileWriter outputStream = new FileWriter("src/"+map);
        int x,y,xt,yt;
        int prevscored;
        Random r = new Random();
        int i;
        double scored = 0;
        stopRender=1;
        for(int k =0;k<games;k++){
            loadMap();
            score = 0;
            for(int j=0;j<moves;j++){
                findSolutions();
                loadSolutions();
                i = r.nextInt(solutions.size());
                x = solutions.get(i).getX();
                y = solutions.get(i).getY();
                xt = solutions.get(i).getXt();
                yt = solutions.get(i).getYt();
                swap(x, y, xt, yt);
                do{
                    prevscored = score;
                    score+=checkMap();
                }while(score!=prevscored);
            }
            scored +=score;
            outputStream.write(String.valueOf(score));
            outputStream.write("\n");
        }
        score = 0;
        scored = scored/games;
        outputStream.write(String.valueOf(scored));
        outputStream.write("\n");
        outputStream.close();
        loadMap();
        stopRender=0;
    }
    
    private void startLearning() throws IOException{
        loadSolutions();
        List<Solution> learnList = new ArrayList<Solution>();
        List<int[][]> mpList = new ArrayList<int[][]>();
        int x,y,xt,yt;
        int prevscored;
        double avgScore = 0;
        int n = 20;
        stopRender = 1;
        ObjectOutputStream mp = new ObjectOutputStream(new FileOutputStream(new File("src/Maps"+size+"_"+n+".ser")));
        for(int k=0;k<1000;k++){
            saveNewMap();
            findSolutions();
            loadSolutions();
            
            for(int i=0;i<solutions.size();i++){
                loadMap();
                avgScore = 0;
                score = 0;
                for(int j=0;j<n;j++){
                    loadMap();

                    x = solutions.get(i).getX();
                    y = solutions.get(i).getY();
                    xt = solutions.get(i).getXt();
                    yt = solutions.get(i).getYt();

                    swap(x, y, xt, yt);

                    do{
                        prevscored = score;
                        score+=checkMap();
                    }while(score!=prevscored);
                    if(j==n-1){
                        avgScore = score/n;
                        solutions.get(i).setW(avgScore);
                    }
                }

            }
            Solution best = solutions.get(0);
            for(int i=0;i<solutions.size();i++){
                if(best.getW() < solutions.get(i).getW())
                    best = solutions.get(i);
            }
        mpList.add(gems);
        learnList.add(best);
        }
        loadMap();
        FileOutputStream fos = new FileOutputStream("BestSolutions"+size+"_"+n+".ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(learnList);
        mp.writeObject(mpList);
        mp.close();
        oos.close();
        fos.close();
        
      score = 0;
      stopRender = 0;
      return;  
    }
    
    private void loadSolutions() throws FileNotFoundException, IOException{
        FileInputStream fis = new FileInputStream("solutions"+size+".ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            solutions = (ArrayList<Solution>)ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        ois.close();
        fis.close();
    }
    
    private void findSolutions(){
        List<Solution> sol = new ArrayList<Solution>();

        try {
            int x,y,xt,yt,p;
            FileOutputStream fos = new FileOutputStream("solutions"+size+".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            for(int i=0;i<size;i++)
                for(int j=0;j<size;j++){
                    int pat = gems[i][j];
                    for(int k=-2;k<3;k++)
                        for(int l=-2;l<3;l++){
                            if(checkNear(i+k, j+l)==1 && (abs(k)+abs(l)!=4) && (l!=0 || k!=0)){
                               if((k!=0||l!=-1)&&(k!=-1||l!=0)&&(k!=1||l!=0)&&(k!=0||l!=1)){
                                   if(gems[i+k][j+l] == pat){
                                       if((l/2!=0 || k/2!=0)){
                                           x=i;
                                           y=j;
                                            if(abs(k)== 2)
                                                xt=i+k/2;
                                            else
                                                xt=i+k;
                                            if(abs(l)== 2)
                                                yt=j+l/2;
                                            else
                                                yt=j+l;
                                            sol.add(new Solution(x, y, xt, yt, 0));
                                       }
                                       else{
                                           x=i;
                                           y=j;
                                           xt=i+k;
                                           yt=j;
                                           sol.add(new Solution(x, y, xt, yt, 0));
                                           x=i;
                                           y=j;
                                           xt=i;
                                           yt=j+l;
                                           sol.add(new Solution(x, y, xt, yt, 0));
                                       }
                                   }
                               } 
                            }
                        }
                }
            oos.writeObject(sol);
            oos.close();
            fos.close();
        }catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void loadMap() throws IOException{
        String map = "map"+size+"v"+size+".txt";
        File f = new File("src/"+map);
        FileReader fr = new FileReader(f);
        BufferedReader buffer = new BufferedReader(fr);
        int c = 0;
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if((c = buffer.read()) != -1){
                    char character = (char) c;
                    int pat = character;
                    gems[i][j] = pat-48;
                }
            }
        }
        loadGems();
        fr.close();
        buffer.close();
    }
    
    private void loadGems(){
        handler.clear();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                switch(gems[i][j]){
                    case 0:
                        handler.addObject(new JewelBlue(i, j, "B", this));
                        break;
                    case 1:
                        handler.addObject(new JewelGreen(i, j, "G", this));
                        break;
                    case 2:
                        handler.addObject(new JewelYellow(i, j, "Y", this));
                        break;
                    case 3:
                        handler.addObject(new JewelBlack(i, j, "BCK", this));
                        break;
                    case 4:
                        handler.addObject(new JewelWhite(i, j, "W", this));
                        break;
                    case 5:
                        handler.addObject(new JewelOrange(i, j, "O", this));
                        break;
                    case 6:
                        handler.addObject(new JewelBrown(i, j, "BWN", this));
                        break;
                    case 7:
                        handler.addObject(new JewelViolet(i, j, "V", this));
                        break;
                    case 8:
                        handler.addObject(new JewelRed(i, j, "R", this));
                        break;
                }
            }
        }
    }
    
    private int checkEnv(int x,int y,int size,int n,Game game){
        if(x + 1 < size){
            if(game.gems[x+1][y] == n)
                return 1;
        }
        if(x - 1 >= 0){
            if(game.gems[x-1][y] == n)
                return 1;
        }
        if(y + 1 <size){
            if(game.gems[x][y+1] == n)
                return 1;
        }
        if(y - 1 >= 0){
            if(game.gems[x][y-1] == n)
                return 1;
        }
        return 0;       
    }
    
    private void swap(int xt,int yt,int xs,int ys){
        if(abs(xt-xs) <= 1 && abs(yt-ys) <=1 && gems[xt][yt] != gems[xs][ys] && (xs!=xt || ys!=yt)){
            GameObject target = handler.findByPos(xt, yt);
            GameObject src = handler.findByPos(xs, ys);
            int pat = gems[xs][ys];
            gems[xs][ys] = gems[xt][yt];
            gems[xt][yt] = pat;
            if(checkEnv(xs, ys, size, gems[xs][ys], this)!=0){
                int tmpX = target.getX();
                int tmpY = target.getY();
                target.setX(src.getX());
                target.setY(src.getY());
                src.setX(tmpX);
                src.setY(tmpY);
            }
            else {
                pat = gems[xt][yt];
                gems[xt][yt] = gems[xs][ys];
                gems[xs][ys] = pat;
            }
        }
    }
    
    private void initMap(int i,int j){
        Random r = new Random();
        int rand;
        do{
            rand = r.nextInt(5);
            gems[i][j] = rand;
        }
        while(checkEnv(i, j, size, rand,this)!=0);                 
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        g = bs.getDrawGraphics();
        
        g.setColor(Color.lightGray);
        g.fillRect(0,0,getWidth(),getHeight());
        if(swap == 1)
            pick.render(g);
        if(stopRender==0){
            pointer.render(g);
            handler.render(g);
            totalScore.render(g);
        }
        g.dispose();
        bs.show();
    }
        
    @Override
    public void run(){
        
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        
        while(running){
            long now = System.nanoTime();
            delta +=(now - lastTime) / ns;
            lastTime = now;
            
            while(delta >= 1){
                delta--;
            }
            if(running)
                render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){
                timer +=1000;
                System.out.println("FPS: " + frames);
                frames = 0;
                     
            }
        }
        stop();
    }
    
    public synchronized void start(){
        
        if(running){
            return;
        }
        init();
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void init(){
        totalScore = new TextBox(size*45+10, 100, Integer.toString(score) , 15, 75, 40);
        pointer = new PTR(size/2+1, size/2+1, "PTR", this,Color.DARK_GRAY);
        pick = new PTR(size/2+1, size/2+1, "PCK", this,Color.GRAY);
        gems = new int[size][];
        
        for(int i=0;i<size;i++)
            gems[i] = new int[size];
        
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                gems[i][j] = 0;
        
        addKeyListener(new KeyInput(this));
        /*try {
            saveNewMap();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.addMouseListener(new MouseInput());
        /*for(int i=0;i<size;i++)
            for(int j=0;j<size;j++){
               initMap(i,j);       
            }
        createGems();
        */
        try {
            loadMap();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private int checkBoarders(int x,int y){
        if(x<1 || x>size || y<1 || y>size)
            return 0;
        else return 1;
    }
    
    private int checkNear(int x,int y){
        if(x<0 || x>=size || y<0 || y>=size)
            return 0;
        else return 1;
    }
    
    private void saveNewMap() throws IOException{
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                initMap(i, j);
        String map = "map"+size+"v"+size+".txt";
        FileWriter outputStream = new FileWriter("src/"+map);  ;
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                outputStream.write((char)gems[i][j]+48);
        
        outputStream.close();
    }
    
    private void sortMap(){
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++){
                if(gems[i][j] == -1){
                    int k = j;
                    int pat;
                    while(k>0){
                        GameObject target = handler.findByPos(i, k);
                        GameObject src = handler.findByPos(i, k-1);
                        if(target!=null)
                            target.setY(k-1);
                        if(src!=null)
                            src.setY(k);
                        pat = gems[i][k-1];
                        gems[i][k-1] =gems[i][k];
                        gems[i][k] = pat;
                        k--;
                    }
                    initMap(i, 0);
                    
                    j--;
                }       
            }
        loadGems();
    }
    
    private int checkMap(){
        int cnt = 0;
        int pts = 0;
        int p=10;
        boolean scored;
        
        do{
            scored = false;
            for(int i=0;i<size;i++){
                for(int j =0;j<size;j++){
                    int pat = gems[i][j];
                    if(pat>=9)
                        continue;
                    int xtmp = i;
                    int ytmp = j;
                    while(xtmp+1<size && (gems[xtmp+1][ytmp] == pat || gems[xtmp+1][ytmp]==pat%9)){
                        scored = true;
                        if(gems[xtmp+1][ytmp]>=9)
                            scored = false;
                        gems[i][j]= pat+9;
                        gems[xtmp+1][ytmp] = pat+9;
                        pts +=p*(1+cnt/5);
                        cnt++;
                        xtmp++;
                    }
                    xtmp = i;
                    ytmp = j;
                    while(xtmp - 1 >= 0 && (gems[xtmp-1][ytmp] == pat || gems[xtmp-1][ytmp]==pat%9)){
                        scored = true;
                        if(gems[xtmp-1][ytmp]>=9)
                            scored = false;
                        gems[i][j]= pat+9;
                        gems[xtmp-1][ytmp] = pat+9;
                        pts +=p*(1+cnt/5);
                        cnt++;
                        xtmp--;
                    }
                    xtmp = i;
                    ytmp = j;
                    while(ytmp - 1 >= 0 && (gems[xtmp][ytmp-1] == pat || gems[xtmp][ytmp-1]==pat%9)){
                        scored = true;
                        if(gems[xtmp][ytmp-1]>=9)
                            scored = false;
                        gems[i][j]= pat+9;
                        gems[xtmp][ytmp-1] = pat+9;
                        pts +=p*(1+cnt/5);
                        cnt++;
                        ytmp--;
                    }
                    xtmp = i;
                    ytmp = j;
                    while(ytmp + 1 < size && (gems[xtmp][ytmp+1] == pat || gems[xtmp][ytmp+1]==pat%9)){
                        scored = true;
                        if(gems[xtmp][ytmp+1]>=9)
                            scored = false;
                        gems[i][j]= pat+9;
                        gems[xtmp][ytmp+1] = pat+9;
                        pts +=p*(1+cnt/5);
                        cnt++;
                        ytmp++;
                    }
                }
            }             
        }while(scored == true);
        
        for(int i=0;i<size;i++){
                for(int j =0;j<size;j++){
                    if(gems[i][j] >= 9){
                        handler.object.remove(handler.findByPos(i, j));
                        gems[i][j] = -1;
                    }
                }
            }
        sortMap();
        return pts;
    }
    
}
