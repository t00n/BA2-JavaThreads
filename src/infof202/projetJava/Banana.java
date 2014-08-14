package infof202.projetJava;

public class Banana {
	private Banana() {
	}
	public static volatile int banana_eaten = 0;
	public static volatile int banana_total = 0;
	public void eat()
	{
		synchronized(this.getClass())
		{
			++banana_eaten;
		}
	}
	public static Banana get()
	{
		++banana_total;
		return new Banana();
	}
}
