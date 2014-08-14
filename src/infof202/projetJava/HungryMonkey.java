package infof202.projetJava;

import java.util.List;

public class HungryMonkey extends InTheZoo {
	private int _nbOfScientists;
	public HungryMonkey(Multibox<Banana> multibox, int i, int nb) {
		super(multibox, i);
		_nbOfScientists = nb;
	}
	
	public void run()
	{
		System.out.println(getName() + " starts eating");
		while (_nbOfScientists > 0)
		{
			try {
				List<Banana> bananas = _multibox.getAll();
				for (Banana b : bananas)
				{
					b.eat();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			--_nbOfScientists;
		}
		System.out.println(getName() + " has finished eating");
	}

}
