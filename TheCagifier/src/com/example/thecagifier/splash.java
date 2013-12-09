package com.example.thecagifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class splash extends Activity{
	
	String[] facts={"Born Nicolas Coppola, he chose Cage as his stage name to honor comic book superhero Luke Cage.",   "Inspired by Superman's birth name, he christened his (now 5-year-old) son Kal-El.",   "Cage's father, the late August Coppola, worked as a comparative literature professor at Cal State Long Beach and served as a trustee of the California state university system.",   "In 2001, Cage received an honorary doctorate from Cal State Fullerton. He had previously dropped out of UCLA.",   "Cage has two older brothers, Marc and Christopher Coppola, both of whom have worked in the film biz.",   "Through his father, Cage is also related to director Francis Ford Coppola (uncle), actress Talia Shire (aunt), director Sofia Coppola (cousin), and actor Jason Schwartzman (cousin).",   "Cage's first movie role was as a cook in 1982's 'Fast Times at Ridgemont High' (as seen in the background behind Judge Reinhold in the clip below).",   "His next job after that was selling popcorn at a movie theater.",   "But in 1983, he made a name for himself with the cult comedy 'Valley Girl' and his uncle Francis Ford Coppola's 'Rumble Fish,' and he never looked back.",   "A proponent of Method acting, Cage had two of his teeth surgically removed for his role in 1984's 'Birdy.'",   "Cage filed suit against Kathleen Turner, his co-star in the 1986 film 'Peggy Sue Got Married,' after she wrote in her 2008 tell-all that he'd once stolen a Chihuahua.",   "Jim Carrey, who had a small role in 'Peggy Sue Got Married' as one of Cage's high-school pals, has been quoted as saying that Cage has 'elephant balls.'",   "Cage ate a live cockroach for his role in 1989's 'Vampire's Kiss' -- although, allegedly, the film's director only wanted him to down a raw egg.",   "When Cage met first wife Patricia Arquette in 1987, he declared, 'I love you and I'm going to marry you.'",   "Arquette decided to test his dedication by giving him a list of seemingly impossible tasks that he'd need to perform before she'd say yes. One of them included procuring the autograph of notoriously reclusive author J.D. Salinger -- which Cage improbably managed to do.",   "It still took eight years for Cage to convince Arquette to marry him, which they finally did in 1995. The couple separated after just nine months -- although they did not file for divorce until 2000.",   "Cage was 32 years old when he won the Academy Award for Best Actor for 'Leaving Las Vegas,' which currently ranks him as the fifth-youngest performer to ever win in that category. (Sixth-youngest? Jimmy Stewart, who had 205 days on Cage when he won for 'The Philadelphia Story' in 1940.)",   "The second time Cage earned an Oscar nomination in the Best Actor category -- for his dual role as twins Charlie and Donald Kaufman in 2002's 'Adaptation' -- he lost to 'The Pianist' star Adrien Brody, who became the youngest-ever Best Actor Oscar winner, at 29.", "The sixth chapter of the 1998 biography 'Nicolas Cage: Hollywood's Wild Talent' is entitled 'Jimmy Stewart from Mars.'",   "Nicolas Cage has owned a pet octopus.",   "He has also admitted to taking hallucinogenic mushrooms with his cat on at least one occasion. Seeing that the cat, Lewis, had snuck into the refrigerator and eaten the 'shrooms 'voraciously,' Cage reasoned, 'What the heck, I better do it with him.'",   "Cage was appointed a goodwill ambassador for the U.N. Office on Drugs and Crime in 2009.",   "He once donated $2 million to Amnesty International to aid former child soldiers.",   "Cage has earned soundtrack credits in five of his films in which his characters either sang and/or played the mandolin: 'Peggy Sue Got Married,' 'Wild at Heart,' 'Leaving Las Vegas,' 'The Family Man' and 'Captain Correlli's Mandolin.'",   "On his back, Cage has a tattoo of a lizard wearing a top hat.",   "He's apparently fascinated by ley lines, defined by The Skeptic's Dictionary as 'alignments of ancient sites or holy places, such as stone circles, standing stones, cairns and churches.'",   "Purportedly, Cage, who married Lisa Marie Presley in 2002 (they divorced in 2004), is the only person outside the immediate Presley family to have ever been inside Elvis Presley's Graceland bedroom.",   "Cage's middle name, Kim, is also third wife Alice's surname.",   "Nicolas Kim Copolla met Alice Kim in 2004 when she was his waitress at Le Priv�, a Korean nightclub in Los Angeles (which has since closed). The club was known for 'booking,' a 'K-club' practice in which young women are brought over to men's tables for on-the-spot dates.",   "He bought a Bahamanian island in 2006, near the one owned by Faith Hill and Tim McGraw. He also bought a German castle that year. He was forced to sell both in 2009, when his wild spending sprees caught up to him.",   "According to records, Cage had purchased 22 cars in the year 2007; nine of those were Rolls Royces.",   "In 2007, Cage and his first son, Weston, now 20, co-authored a limited-edition comic book series entitled 'Voodoo Child.'",   "Weston's mother is Christina Fulton, a sometime-actress who had a part in Cage's 1998 flick 'Snake Eyes.'",   "When filming 'Bad Lieutenant: Port of Call New Orleans' in the Big Easy, he bought a two-headed snake for protection. Later, he donated it to a zoo.", "When a six-bedroom Las Vegas mansion that Cage owned went into foreclosure in late 2009 and was later sold at auction, numerous news outlets covered the events with the headline 'Nicolas Cage Leaving Las Vegas,' or something very similar.",   "In addition to his well-documented predilection for house- and castle-buying, Cage bought a nine-foot-tall, pyramid-shaped tomb in a New Orleans cemetery last year. It is assumed this will be his final resting place.",   "In 2009, film critic Roger Ebert wrote,'Cage has two speeds: intense and intenser.'",   "This was said of the man who has starred in 'The Wicker Man,' 'The Weather Man,' 'The Family Man' and 'Matchstick Men.'",   "You might say that Cage is a 'carnalvore,' basing his meat-consuming choices on an animal's mating habits.",   "In fact, he told a London newspaper in 2010: 'I actually choose the way I eat according to the way animals have sex. I think fish are very dignified with sex. So are birds. But pigs, not so much. So I don't eat pig meat or things like that. I eat fish and fowl.'",   "Cage has done ads for Montblanc watches and Sankyo pachinko machines (which are like a cross between pinball machines and slot machines, and popular in Japan).",   "He paid $276,000 at auction for a dinosaur skull of a Tarbosaurus; one of the people he outbid was Leonardo DiCaprio.",   "The blog Nic Cage As Everyone was 'founded on the belief that everything in life would be better with a little more Nic Cage. It includes doctored photos of Cage as Santa Claus, Princess Leia, Justin Bieber, Sarah Palin, Weezer frontman Rivers Cuomo, and even Cage's wife, Alice.",   "Amateur video of Cage somewhere in Bucharest (where he's filming the 'Ghost Rider' sequel) surfaced a few weeks ago. In it, he yells such inanities as \'I\'ll die in the name of honor!'"};  
    int factnum=(int)(Math.random()*facts.length);
    
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash);
		
		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}
		
	private class IntentLauncher extends Thread{
			public void run(){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		Intent intent = new Intent(splash.this, MainScreen.class);
		splash.this.startActivity(intent);
		splash.this.finish();
			}
		};
		
		
	
@Override
protected void onPause() {
	super.onPause();
}

}