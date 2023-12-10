public class MapLine {
	public long dstRangeStart, srcRangeStart, rangeLen;

	public MapLine(String mapLineStr) {
		String tokens[] = mapLineStr.split(" ");

		this.dstRangeStart = Long.parseLong(tokens[0]);
		this.srcRangeStart = Long.parseLong(tokens[1]);
		this.rangeLen = Long.parseLong(tokens[2]);
	}

	public long translate(long toTrans, boolean reverse) {
		long fromRangeStart = reverse ? this.dstRangeStart : this.srcRangeStart;
		long toRangeStart = reverse ? this.srcRangeStart : this.dstRangeStart;
		long offset = toTrans - fromRangeStart;

		return (offset >= 0 && offset < this.rangeLen) ? (toRangeStart + offset) : -1;
	}
}