
public class GreenMass {
	
	int[][] outline;
	public int mass;
	int xStart, yStart, width, height;
	int newXStart, xEnd, newXEnd;
	
	public GreenMass(int x, int y, int lineLength, int xend)
	{
		xStart = x;
		yStart = y;
		newXStart = xStart;
		xEnd = xend;
		newXEnd = xEnd;
		
		width = lineLength;
		height = 1;
		mass = 0;
		
		outline = new int[width][0];
		incrementMass(lineLength);
		updateOutline(lineLength, true);
	}
	
	public void addToMass(int xStart, int yStart, int lineLength, int xend)
	{
		newXStart = xStart;
		newXEnd = xend;
		height++;
		incrementMass(lineLength);
		updateOutline(lineLength, false);
	}
	
	private void incrementMass(int lineLength)
	{
		mass += lineLength;
//		System.out.println("mass: " + mass);
	}
	
	private void updateOutline(int lineLength, boolean firstTime)
	{
		int start = xStart;
		if (xStart > newXStart)
		{
			start = newXStart;
		}
		int end = xEnd;
		if (xEnd < newXEnd)
		{
			end = newXEnd;
		}
	//	System.out.println("xStart: " + start + ", xEnd: " + end);
		int widthDiff = returnNonOverlap();
//		System.out.println("widthDiff: " + widthDiff);
		int startMove = 0;
		int newStartMove = 0;
		if (widthDiff > 0)
		{
			newStartMove = widthDiff;
		}
		else if (widthDiff < 0)
		{
			startMove = Math.abs(widthDiff);
		}
		int w = end-start+1;
		int h = height;
		
//		System.out.println("h: " + h);
//		System.out.println("w: " + w);
		
		int[][] newOutline = new int[w][h];

		for (int y = 0; y < h-1; y++)//original mass
		{
			for (int x = 0; x < outline.length; x++)
			{
				newOutline[x+startMove][y] = outline[x][y];
			}
		}
//		System.out.println();//newLine
		for (int x = 0; x < lineLength; x++)
		{
				newOutline[x+newStartMove][newOutline[0].length-1] = 1;
		}
/*		for (int y = 0; y < h; y++)
		{
		System.out.println();
			for (int x = 0; x < w; x++)
			{
				System.out.print(newOutline[x][y] + ", ");
			}
			
		}*/
		
		xStart = start;
		xEnd = end;
		outline = newOutline;
		width = w;
	}
	/*
	 * ints:
	 *  mass,
	 *  xStart, 
	 *  yStart, 
	 *  width, 
	 *  height, 
	 *  xEnd, 
	 *  newXStart, 
	 *  newXEnd
	 *  */
	
	public void combineWith(GreenMass gPiece)
	{
		int[][] newOutline;
		int w, h;
		int yEndA = yStart+height-1, yEndB = gPiece.yStart+gPiece.height-1, yEnd;
		mass += gPiece.mass;
		int lastXStart = xStart, lastYStart = yStart;
		if (xEnd < gPiece.xEnd)
		{
			xEnd = gPiece.xEnd;
		}
		if (xStart > gPiece.xStart)
		{
			xStart = gPiece.xStart;
		}
		if (yStart > gPiece.yStart)
		{
			yStart = gPiece.yStart;
		}
		int x1StartDiff = lastXStart - xStart;
		int x2StartDiff = gPiece.xStart - xStart;
		int y1StartDiff = lastYStart - yStart;
		int y2StartDiff = gPiece.yStart - yStart;
//		System.out.println("xStart: " + xStart + ", xEnd: " + xEnd + ", x1StartDiff " + x1StartDiff);
		//...
		w = xEnd-xStart+1;
		if (yEndB > yEndA)
		{
			yEnd = yEndB;
		}
		else
		{
			yEnd = yEndA;
		}
		h = yEnd-yStart+1;
		newOutline = new int[w][h];
//		System.out.println("w: " + w + ", h: " + h);
//		System.out.println("xStart: " + xStart + ", xEnd: " + xEnd + ", x1StartDiff " + x1StartDiff);
//		System.out.println(width);
		//...
		for (int y = 0; y < height; y++)
		{//a first then b object (a will have the new overlapping line causing them to combine)
			for (int x = 0; x < width; x++)
			{
				newOutline[x+x1StartDiff][y+y1StartDiff] = outline[x][y];
			}
		}
		for (int y = 0; y < gPiece.height; y++)
		{
			for (int x = 0; x < gPiece.width; x++)
			{
				newOutline[x+x2StartDiff][y+y2StartDiff] = gPiece.outline[x][y];
			}
		}
		
		width = w;
		height = h;
		outline = newOutline;
	}
	
	private int returnNonOverlap()
	{
		int widthDiff = 0;
		widthDiff = newXStart-xStart;
		return widthDiff;//how much newLine is moving from oldLine
	}
}
