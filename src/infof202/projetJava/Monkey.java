package infof202.projetJava;

public class Monkey extends InTheZoo {
	private int _nbOfBananas;
	public static volatile int nbOfBananas;
	public Monkey(Multibox<Banana> multibox, int i, int nb)
	{
		super(multibox, i);
		_nbOfBananas = nb;
	}
	
	public void run()
	{
		System.out.println(getName() + " starts eating");
		while (_nbOfBananas > 0)
		{
			try {
				_multibox.getItem(_nbOfBananas%_multibox.getSize()).eat();
			} catch (Exception e) {
				e.printStackTrace();
			}
			--_nbOfBananas;
		}
		System.out.println(getName() + " has finished eating");
	}


}
