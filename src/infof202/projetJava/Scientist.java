package infof202.projetJava;

import java.util.List;
import java.util.Vector;

public class Scientist extends InTheZoo
{
	private int _nbOfHungryMonkeys;
	public Scientist(Multibox<Banana> multibox, int i, int nb_hungry) {
		super(multibox, i);
		_nbOfHungryMonkeys = nb_hungry;
	}
	
	public void run()
	{
		System.out.println(getName() + " starts putting the bananas");
		while (_nbOfHungryMonkeys > 0)
		{
			List<Banana> bananas = new Vector<Banana>();
			for (int i = 0; i < _multibox.getSize(); ++i)
			{
				bananas.add(Banana.get());
			}
			try {
				_multibox.putAll(bananas);
			} catch (Exception e) {
				e.printStackTrace();
			}
			--_nbOfHungryMonkeys;
		}
		System.out.println(getName() + " has finished putting the bananas");
	}
}
