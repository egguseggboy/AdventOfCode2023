import java.util.ArrayList;

public class NodeInfo {
	int cycleLen, cycleOffset;
	String nodeName;
	ArrayList<Integer> endsInZInd = new ArrayList<>();

	public NodeInfo(String nodeName) {
		this.nodeName = nodeName;
	}
}
