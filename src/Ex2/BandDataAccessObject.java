package Ex2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BandDataAccessObject implements BandDataAccess {
	private static int number_of_objects = 0;	
	private final String FILE_NAME= "bands.bin";
	private ObjectInputStream input;
	private BandsArrayList bandsList;
	private BandsHashMap bandsHash;
	private ObjectOutputStream output;
	
	@Override
	public BandsArrayList readAllBands() throws IOException, ClassNotFoundException {
		input = new ObjectInputStream(new FileInputStream(new File(FILE_NAME)));
		Band [] bandsArray = (Band[]) input.readObject();
		bandsList = new BandsArrayList(bandsArray);
		input.close();
		return bandsList;
	}

	@Override
	public BandsHashMap getBandsMappedByName() throws IOException, ClassNotFoundException {
		input = new ObjectInputStream(new FileInputStream(new File(FILE_NAME)));
		for(Band band : bandsList)
			bandsHash.put(band.getName(), band);
		input.close();
		return bandsHash;
			
			
	}

	@Override
	public void saveBands(Band[] bands) throws IOException {
		output=new ObjectOutputStream(new FileOutputStream(new File(FILE_NAME)));
		output.writeObject(bands);
		output.close();

	}

	public static BandDataAccessObject getInstance() {
		if (number_of_objects > 1)
			return null;
		else {
			number_of_objects++;
			return new BandDataAccessObject();
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


}
