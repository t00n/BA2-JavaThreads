package infof202.projetJava;
import java.util.Vector;
import java.util.List;
import java.lang.Exception;

public class Multibox<T> {

	private Vector<T> _box;
	private int _size;
	private volatile boolean _lockPut;
	private volatile boolean _lockGet;
	private volatile boolean _lockBox;
	
	synchronized public final Vector<T> getBox() { return _box; }
	synchronized public int getSize() { return _size; }

	private boolean full()
	{
		boolean ret = true;
		for (int i = 0; i < _size; ++i)
		{
			if (_box.get(i) == null)
			{
				ret = false;
			}
		}
		return ret;
	}
	
	private boolean empty()
	{
		boolean ret = true;
		for (int i = 0; i < _size; ++i)
		{
			if (_box.get(i) != null)
			{
				ret = false;
			}
		}
		return ret;
	}
	
	public Multibox(int size) throws Exception
	{
		if (size < 0)
		{
			throw new Exception("Multibox: constructor: size not in range");
		}
		else
		{
			_size = size;
			_lockPut = false;
			_lockGet = false;
			_lockBox = false;
			_box = new Vector<T>();
			_box.setSize(_size);
		}
	}
	
	synchronized public void put (int pos, T value) throws Exception
	{
		if (pos < 0 || pos >= _size)
		{
			throw new Exception("Multibox: put: pos not in range");
		}
		while (_box.get(pos) != null || _lockPut == true)
		{
			this.wait();
		}
		_box.set(pos, value);
		this.notifyAll();
	}
	
	synchronized public void putAll(List<T> items) throws Exception {
		if (items.size() != getSize())
		{
			throw new IllegalArgumentException();
		}
		while (_lockBox == true)
		{
			this.wait();
		}
		_lockBox = true;
		_lockPut = true;
		while (! empty())
		{
			this.wait();
		}
		_box = new Vector<T>(items);
		_lockBox = false;
		_lockPut = false;
		this.notifyAll();
	}
	
	synchronized public T getItem(int pos) throws Exception
	{
		T ret;
		if (pos < 0 || pos >= _size)
		{
			throw new Exception("Multibox: getItem: pos not in range");
		}
		while (_box.get(pos) == null || _lockGet == true)
		{
			this.wait();
		}
		ret = _box.get(pos);
		_box.set(pos, null);
		this.notifyAll();
		return ret;
	}

	synchronized public List<T> getAll() throws Exception
	{
		List<T> ret;
		while(_lockBox == true)
		{
			this.wait();
		}
		_lockBox = true;
		_lockGet = true;
		while(! full())
		{
			this.wait();
		}
		ret = new Vector<T>(_box);
		_box.clear();
		_box.setSize(_size);
		_lockBox = false;
		_lockGet = false;
		this.notifyAll();
		return ret;
	}
}
