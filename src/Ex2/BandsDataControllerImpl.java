package Ex2;

import java.io.IOException;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.Stack;

public class BandsDataControllerImpl implements BandsDataController {
	private static int number_of_objects = 0;	
	private BandsArrayList bandsArray;
	private BandsHashMap bandsHash = new BandsHashMap();
	private BandDataAccessObject bandsData;
	private ListIterator<Band> bandIterator;
	private Band band;
	private Stack<BandsDataCommand> commands = new Stack<>();

	public BandsDataControllerImpl() {
		
		bandsData=	BandDataAccessObject.getInstance();
		
		try {
			bandsArray = bandsData.readAllBands();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		bandIterator = bandsArray.listIterator();
		
		for(int i = 0; i< bandsArray.size(); i++){
			bandsHash.put(bandsArray.get(i).getName(), bandsArray.get(i));
		}

	}

	public Band getBand() {
		return band;
	}

	@Override
	public Band previous() {
		BandsDataCommand prev = new Previous();
		prev.execute();
		commands.push(prev);

		return band;
	}

	@Override
	public Band next() {
		BandsDataCommand next = new Next();
		next.execute();
		commands.push(next);

		return band;
	}

	@Override
	public void sort(Comparator<Band> comparator) {
		BandsDataCommand sort = new Sort(comparator);
		sort.execute();
		commands.push(sort);
	}

	@Override
	public void add(Band band) {
		BandsDataCommand add = new Add(band);
		add.execute();
		commands.push(add);
	}

	@Override
	public void remove() {
		BandsDataCommand remove = new Remove();
		remove.execute();
		commands.push(remove);
	}

	@Override
	public void undo() {
		if (!commands.isEmpty())
			commands.pop().undo();
	}

	@Override
	public void revert() {
		while (!commands.isEmpty())
			commands.pop().undo();
	}

	@Override
	public void save() throws IOException {
		bandsData.saveBands((Band[]) bandsArray.toArray());
	}

	@Override
	public Band getBandByName(String name) {	
		return bandsHash.get(name);
	}
	
	public static BandsDataControllerImpl getInstance() {
		if (number_of_objects > 1)
			return null;
		else {
			number_of_objects++;
			return new BandsDataControllerImpl();
		}
	}

	public static void reduceNumberOfObjects() {
		number_of_objects--;
	}

	public static int getNumberOfObjects() {
		return number_of_objects;
	}

	public static void resetNumberOfObjects() {
		number_of_objects = 0;
	}


	

	class Previous implements BandsDataCommand {

		@Override
		public void execute() {
			band = bandIterator.previous();
		}

		@Override
		public void undo() {
			band = bandIterator.next();

		}

	}

	class Next implements BandsDataCommand {

		@Override
		public void execute() {
			band = bandIterator.next();
		}

		@Override
		public void undo() {
			band = bandIterator.previous();
		}

	}

	class Sort implements BandsDataCommand {
		
		private Comparator<Band> comparator;
		
		public Sort(Comparator<Band> comparator){
			this.comparator=comparator;
		}

		@Override
		public void execute() {
			Band temp = new Band(0, null, 0, null, null, false, null);
			for (int i = 0; i < bandsArray.size(); i++) {
				for (int j = 1; j < (bandsArray.size() - i); j++) {
					if (comparator.compare(bandsArray.get(j - 1), bandsArray.get(j)) == 1) {
						// swap elements
						temp = bandsArray.get(j - 1);
						bandsArray.set((j - 1), bandsArray.get(j));
						bandsArray.set(j, temp);
					}

				}
			}
			bandIterator.previous();
			band= bandIterator.next();

		}

		@Override
		public void undo() {
			Band temp = new Band(0, null, 0, null, null, false, null);
			BandsComparatorBySerialNumber unSort = new BandsComparatorBySerialNumber();
			for (int i = 0; i < bandsArray.size(); i++) {
				for (int j = 1; j < (bandsArray.size() - i); j++) {
					if (unSort.compare(bandsArray.get(j - 1), bandsArray.get(j)) == 1) {
						// swap elements
						temp = bandsArray.get(j - 1);
						bandsArray.set((j - 1), bandsArray.get(j));
						bandsArray.set(j, temp);
					}

				}
			}
			bandIterator.previous();
			band= bandIterator.next();

		}

	}

	class Add implements BandsDataCommand {
		
		private Band tempBand;
		
		public Add (Band band){
			this.tempBand=band;
		}

		@Override
		public void execute() {
			bandIterator.add(tempBand);
			bandsHash.put(tempBand.getName(), tempBand);
		}

		@Override
		public void undo() {
			bandIterator.remove();
			bandsHash.remove(tempBand.getName());
		}

	}

	class Remove implements BandsDataCommand {

		private Band tempBand = band;

		@Override
		public void execute() {
			bandIterator.remove();
			bandsHash.remove(tempBand.getName());
			band=bandIterator.next();

		}

		@Override
		public void undo() {
			bandIterator.add(tempBand);
			bandsHash.put(tempBand.getName(), tempBand);
			bandIterator.next();
			band=bandIterator.previous();

		}

	}

}
