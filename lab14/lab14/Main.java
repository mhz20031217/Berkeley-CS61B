package lab14;

import lab14lib.*;

public class Main {
	public static void main(String[] args) {
//		Generator generator =  new SineWaveGenerator(440);
//		GeneratorPlayer gp = new GeneratorPlayer(generator);
//		gp.play(100000000);

//		Generator generator = new SineWaveGenerator(200);
//		GeneratorDrawer gd = new GeneratorDrawer(generator);
//		gd.draw(4096);

		Generator generator = new SineWaveGenerator(200);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(4096, 10000000);
	}
} 