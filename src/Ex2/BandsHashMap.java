package Ex2;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class BandsHashMap implements Map<String, Band> {
	private static int DEFAULT_CAPACITY = 4;
	private final int MAX_CAPACITY = 30;
	private final static float LOAD_FACTOR = 0.75f;
	private final int NOT_FOUND = -1;
	private float loadFactorThreshold;
	private int capacity;
	private int size;
	private BandEntry[] entries;

	/** Construct a map with the default capacity and load factor */
	public BandsHashMap() {
		this(DEFAULT_CAPACITY, LOAD_FACTOR);
	}

	/**
	 * Construct a map with the specified initial capacity and default load
	 * factor
	 */
	public BandsHashMap(int initialCapacity) {
		this(initialCapacity, LOAD_FACTOR);
	}

	/**
	 * Construct a map with the specified initial capacity and load factor
	 */

	public BandsHashMap(int initialCapacity, float loadFactorThreshold) {
		if (initialCapacity > MAX_CAPACITY)
			this.capacity = MAX_CAPACITY;
		else
			this.capacity = trimToPowerOf2(initialCapacity);
		this.loadFactorThreshold = loadFactorThreshold;
		entries = new BandEntry[capacity];
	}
	
	@Override
	public void clear() {
		size = 0;
		removeEntries();
	}

	@Override
	public boolean containsKey(Object key) {
		return indexOf(key) != NOT_FOUND;
	}

	@Override
	public boolean containsValue(Object value) {
		for (int i = 0; i < capacity; i++) {
			if (entries[i] != null && entries[i].getValue().equals(value)) 
						return true;
		}
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, Band>> entrySet() {
		java.util.Set<Map.Entry<String, Band>> set = new java.util.HashSet<Map.Entry<String, Band>>();
		for (int i = 0; i < capacity; i++) {
			if (entries[i] != null) {
					set.add(entries[i]);
			}
		}
		return set;
	}

	@Override
	public Band get(Object key) {
		int bucketIndex = hash(key.hashCode());
		if (entries[bucketIndex] != null) {
					return entries[bucketIndex].getValue();
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Set<String> keySet() {
		java.util.Set<String> set = new java.util.HashSet<String>();
		for (int i = 0; i < capacity; i++) {
			if (entries[i] != null) {
					set.add(entries[i].getKey());
			}
		}
		return set;
	}

	@Override	
	public Band put(String key, Band value) {
		int bucketIndex = hash(key.hashCode());
		if (get(key) != null) { // The key is already in the map
					Band oldValue = entries[bucketIndex].getValue();
					// Replace old value with new value
					entries[bucketIndex].setValue(value);// update entry 
					// Return the old value for the key
					return oldValue;
				}
		// Check load factor
		if (size >= capacity * loadFactorThreshold) {
			if (capacity == MAX_CAPACITY)
				throw new RuntimeException("Exceeding maximum capacity");
			rehash();
		}
		// Create a linked list for the bucket if it is not created
		if (entries[bucketIndex] == null) {
			entries[bucketIndex] = new BandEntry(key, value);
		}
//		// Add a new entry (key, value) to hashTable[index]
//		entries[bucketIndex].add(new BandEntry(key, value));
		size++; // Increase size
		return value;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Band> m) {
		@SuppressWarnings("unchecked")
		java.util.Set<String> set=	(Set<String>) m.keySet();
		Band temp = new Band(0, null, 0, null, null, true, null);
		for(String s: set){
			temp=m.get(s);
			if(temp!=null)
				put(s, temp);
		}
		
	}

	@Override
	public Band remove(Object key) {
		int bucketIndex = hash(key.hashCode());
		// Remove the first entry that matches the key from a bucket
		if (entries[bucketIndex] != null) {
			entries[bucketIndex]=null;
					size--; // Decrease size
	 // Remove just one entry that matches the key
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Collection<Band> values() {
		java.util.Set<Band> set = new java.util.HashSet<Band>();
		for (int i = 0; i < capacity; i++) {
			if (entries[i] != null) 
				set.add(entries[i].getValue());			
		}
		return set;
	}
	
	private int indexOf(Object key){
		int index = key.hashCode() % entries.length;
		
		if(entries[index] != null)
			return index;
		
		return NOT_FOUND;
	}
	
	private int hash(int hashCode) {
		return supplementalHash(hashCode) & (capacity - 1);
	}

	private static int supplementalHash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	private void rehash() {	
		java.util.Set<Map.Entry<String, Band>> set = entrySet(); // Get entries
		capacity <<= 1; // Double capacity
		entries = new BandEntry[capacity]; // Create a new hash table
		size = 0; // Reset size to 0
		for (Map.Entry<String, Band> entry : set) {
			put(entry.getKey(), entry.getValue()); // Store to new table
		}
	}
	
	private void removeEntries() {
		for (int i = 0; i < capacity; i++) {
			if (entries[i] != null) {
				entries[i]=null;
			}
		}
	}
	
	private int trimToPowerOf2(int initialCapacity) {
		int capacity = 1;
		while (capacity < initialCapacity) {
			capacity <<= 1;
		}
		return capacity;
	}
	
	class BandEntry implements Map.Entry<String, Band>{
		private String key;
		private Band band;
		
		public BandEntry(String key, Band band) {
			setKey(key);
			setBand(band);
		}
		
		@Override
		public String getKey() {
			return key;
		}

		@Override
		public Band getValue() {
			return band;
		}

		@Override
		public Band setValue(Band value) {
			return band = value;
		}

		private void setKey(String key) {
			this.key = key;
		}

		private void setBand(Band band) {
			this.band = band;
		}
		
	}
}
