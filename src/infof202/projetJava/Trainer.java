package infof202.projetJava;

public class Trainer extends InTheZoo {
	private int _nbOfBananas;
	public static volatile int nbOfBananas;
	public Trainer(Multibox<Banana> multibox, int i, int nb)
	{
		super(multibox, i);
		_nbOfBananas = nb;
	}
	
	public void run()
	{
		System.out.println(getName() + " starts putting the bananas");
		while (_nbOfBananas > 0)
		{
			try {
				_multibox.put(_nbOfBananas%_multibox.getSize(), Banana.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
			--_nbOfBananas;
		}
		System.out.println(getName() + " has finished putting the bananas");
	}

}
