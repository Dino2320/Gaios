package dev.codenmore.Gaois;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.codenmore.Gaois.State.GameState;
import dev.codenmore.Gaois.State.MenuState;
import dev.codenmore.Gaois.State.State;
import dev.codenmore.Gaois.gfx.Assets;

public class Game implements Runnable{

	private Display display;
	public int width, height;
	public String title;
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States >>>>
	private State gameState;
	private State menuState;
	// <<<<

	
	public Game(String title,int width,int height){
		this.width = width;
		this.height = height;
		this.title = title;
		
	}
	private void init(){
	display = new Display(title, width, height);
	Assets.init();
	
	gameState = new GameState();
	menuState = new MenuState();
	State.setState(gameState);
	}
	
	private void tick(){
		if(State.getState() != null);
		State.getState().tick();
	}
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//clear screen
		g.clearRect(0,0,width,height);
		// Draw here faggot
		
		if(State.getState() != null);
		State.getState().render(g);
		
		
		// end here faggot
		bs.show();
		g.dispose();
	}
	
	public void run(){
		
		init();
		
		int fps = 60;
		double TimePerTick = 1000000000/fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		
		while(running){
			now = System.nanoTime();
			delta +=(now-lastTime)/TimePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
			tick();
			render();
			ticks++;
			delta--;
			}
			if(timer >= 1000000000){
				System.out.println("ticks and Frames: "+ticks);
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
