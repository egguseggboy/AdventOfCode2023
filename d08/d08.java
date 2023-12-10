import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

public class d08 {
	public static Node nodeSearch(ArrayList<Node> nodeList, String toSearch) {
		for (Node node : nodeList)
			if (node.name.equals(toSearch))
				return node;

		return null;
	}

	public static NodeInfo nodeInfo(ArrayList<Node> nodes, Node toGet, String turns) {
		NodeInfo info = new NodeInfo(toGet.name);

		int turnNum = 0;

		Node currNode = toGet;
		String nextNodeName = "";

		ArrayList<String> visited = new ArrayList<>();

		while (true) {
			char dir = turns.charAt(turnNum % turns.length());

			String nodeAndDir = currNode.name + '|' + dir;
			info.cycleOffset = visited.indexOf(nodeAndDir);

			if (info.cycleOffset != -1 && info.cycleOffset % turns.length() == turnNum % turns.length()) {
				info.cycleLen = turnNum - info.cycleOffset;
				break;
			}
			visited.add(nodeAndDir);

			if (currNode.name.charAt(2) == 'Z')
				info.endsInZInd.add(turnNum);

			nextNodeName = (dir == 'L') ? currNode.left : currNode.right;
			currNode = nodeSearch(nodes, nextNodeName);

			turnNum++;
		}

		return info;
	}

	public static ArrayList<Integer> getFactors(int num) {
		ArrayList<Integer> factors = new ArrayList<>();

		int i = 2;
		while (num > 1) {
			if (num % i == 0) {
				factors.add(i);
				num /= i;
			} else
				i++;
		}
		return factors;
	}

	public static int part1(ArrayList<String> lines) {
		String turns = lines.get(0);
		int turnNum = 0;

		ArrayList<Node> nodes = new ArrayList<>();

		for (String line : lines)
			if (line.contains("="))
				nodes.add(new Node(line.split("[^A-Z]+")));

		Node currNode = nodeSearch(nodes, "AAA");
		String nextNodeName = "";

		while (!currNode.name.equals("ZZZ")) {
			char dir = turns.charAt(turnNum++ % turns.length());

			nextNodeName = (dir == 'L') ? currNode.left : currNode.right;
			
			currNode = nodeSearch(nodes, nextNodeName);
		}

		return turnNum;
	}

	public static long part2(ArrayList<String> lines) {
		String turns = lines.get(0);

		ArrayList<Node> nodes = new ArrayList<>();

		for (String line : lines) {
			if (line.contains("=")) {
				line = line.replaceAll("[^A-Z0-9]+", " ");
				String tokens[] = line.split(" ");

				nodes.add(new Node(tokens));
			}
		}

		Predicate<Node> endsInNotA = l -> l.name.charAt(2) != 'A';
		ArrayList<Node> allToSearch = new ArrayList<>(nodes);
		allToSearch.removeIf(endsInNotA);

		ArrayList<NodeInfo> nodeInfos = new ArrayList<>();

		for (Node toSearch : allToSearch)
			nodeInfos.add(nodeInfo(nodes, toSearch, turns));

		ArrayList<Long> allFactors = new ArrayList<>();

		for (NodeInfo n : nodeInfos) {
			// System.out.print(String.format("%dx + %d + %d", n.cycleLen, n.cycleOffset, n.endsInZInd.get(0)));

			for (long f : getFactors(n.cycleLen))
				if (allFactors.indexOf(f) == -1)
					allFactors.add(f);
		}

		long lcm = 1;
		for (long f : allFactors) lcm *= f;

		return lcm;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File("d08.txt"));
		ArrayList<String> lines = new ArrayList<String>();

		while (input.hasNextLine())
			lines.add(input.nextLine());

		System.out.print(String.format("Part 1: %d\n", part1(lines)));
		System.out.print(String.format("Part 2: %d\n", part2(lines)));
	}
}