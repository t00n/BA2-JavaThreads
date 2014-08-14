package infof202.projetJava;

public class InTheZoo extends Thread
{
	protected Multibox<Banana> _multibox;
	public InTheZoo(Multibox<Banana> multibox, int i)
	{
		setName(this.getClass().getSimpleName() + " " + i);
		_multibox = multibox;
	}
}
