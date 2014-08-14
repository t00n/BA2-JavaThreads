package infof202.projetJava;

public class Main {

	public static void main(String[] args) throws Exception 
	{		
		if (args.length != 3)
		{
			throw new Exception("Main: Wrong arguments: expected 3 integers");
		}
		int nb_h = Integer.parseInt(args[0]);
		int nb_m = Integer.parseInt(args[1]);
		int box_size = Integer.parseInt(args[2]);
		
		int nb_scientists = nb_h/2;
		int nb_trainers = (nb_h+1)/2;
		int nb_hungry_monkeys = nb_m/2;
		int nb_monkeys = (nb_m+1)/2;
		int nbOfBananas = nb_trainers*nb_monkeys*box_size;
		int bananas_total = nbOfBananas+nb_scientists*box_size;

		Multibox<Banana> multibox = null;
		try {
			multibox = new Multibox<Banana>(box_size);
		} catch (Exception e) {
			throw new Exception("Main: " + e.getMessage());
		}

		System.out.println(nb_h + " human beings feeding the monkeys : ");
		System.out.println("     " + nb_trainers + " trainers who give bananas one by one");
		System.out.println("     " + nb_scientists + " scientists who fill the box with bananas");
		System.out.println();
		System.out.println(nb_m + " monkeys in the zoo : ");
		System.out.println("     " + nb_monkeys + " monkeys who eat one banana at a time");
		System.out.println("     " + nb_hungry_monkeys + " hungry monkeys who eat the entire contents of the box");
		System.out.println();
		System.out.println(box_size + " manger and " + bananas_total + " bananas in the zoo");
		System.out.println();
		
		Trainer[] trainers = new Trainer[nb_trainers];
		Scientist[] scientists = new Scientist[nb_scientists];
		Monkey[] monkeys = new Monkey[nb_monkeys];
		HungryMonkey[] hungrymonkeys = new HungryMonkey[nb_hungry_monkeys];
		
		int nb_all;
		// if there is the same number of hungry monkeys and scientist
		// each scientist feeds a hungry monkey
		if (nb_hungry_monkeys == nb_scientists)
		{
			nb_all = nb_hungry_monkeys;
		}
		// else each scientist but one feeds a hungry monkey
		else
		{
			nb_all = Math.min(nb_hungry_monkeys, nb_scientists) -1;
		}
		
		for (int i = 0; i < nb_all; ++i)
		{
			hungrymonkeys[i] = new HungryMonkey(multibox, i, 1);
			hungrymonkeys[i].start();
			scientists[i] = new Scientist(multibox, i, 1);
			scientists[i].start();
		}
		// and the last scientist/hungry monkey gets to finish the rest
		if (nb_hungry_monkeys < nb_scientists)
		{
			for (int i = nb_all; i < nb_scientists; ++i)
			{
				scientists[i] = new Scientist(multibox, i, 1);
				scientists[i].start();
			}
			hungrymonkeys[nb_all] = new HungryMonkey(multibox, nb_all, nb_scientists-nb_all);
			hungrymonkeys[nb_all].start();
		}
		else if (nb_scientists < nb_hungry_monkeys)
		{
			for (int i = nb_all; i < nb_hungry_monkeys; ++i)
			{
				hungrymonkeys[i] = new HungryMonkey(multibox, i, 1);
				hungrymonkeys[i].start();
			}
			scientists[nb_all] = new Scientist(multibox, nb_all, nb_hungry_monkeys-nb_all);
			scientists[nb_all].start();
		}
		
		// the other monkeys receive a fixed quantity of 18 bananas each
		for (int i = 0; i < nb_trainers; ++i)
		{
			trainers[i] = new Trainer(multibox, i, nbOfBananas/nb_trainers);
			trainers[i].start();
		}
		for (int i = 0; i < nb_monkeys; ++i)
		{
			monkeys[i] = new Monkey(multibox, i, nbOfBananas/nb_monkeys);
			monkeys[i].start();
		}
		
		// Waiting for threads to finish ...
		
		for (int i = 0; i < nb_hungry_monkeys; ++i)
		{
			hungrymonkeys[i].join();			
		}
		for (int i = 0; i < nb_scientists; ++i)
		{
			scientists[i].join();
		}
		
		for (int i = 0; i < nb_monkeys; ++i)
		{
			monkeys[i].join();
		}
		for (int i = 0; i < nb_trainers; ++i)
		{
			trainers[i].join();
		}
		
		System.out.println();
		System.out.println("Dinner is over!");
		System.out.println("Final state of multibox : " + multibox.getBox());
		System.out.println("Total of bananas created : " + Banana.banana_total);
		System.out.println("Total of bananas eaten : " + Banana.banana_eaten);
	}

}
