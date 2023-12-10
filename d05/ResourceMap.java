import java.util.ArrayList;

public class ResourceMap {
	String src, dst;
	ArrayList<MapLine> mapLines = new ArrayList<>();

	public ResourceMap(String mapStr) {
		String tokens[] = mapStr.split("[- ]");

		this.src = tokens[0];
		this.dst = tokens[2];
	}

	public void addMap(String mapVals) {
		mapLines.add(new MapLine(mapVals));
	}

	public void addMapReverse(String mapVals) {
		mapLines.add(0, new MapLine(mapVals));
	}

	public long translateAll(long toTrans, boolean reverse) {
		for (MapLine mapLine : mapLines) {
			long transValue = mapLine.translate(toTrans, reverse);

			if (transValue != -1)
				return transValue;
		}
		return toTrans;
	}
}