package Misc;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

// This class should create actual image representations of the data we have.
// Just for fun so we can see what the digits actually look like.
// Should be able to just modify stuff we did in the Evo Image project.
public class SaveToImg {

	static int[][][]				tstData;
	public final static String	fileLoc	= "train.csv";

	public static void main(String[] args) throws Exception {
		init();
		for (int i = 0; i < 42000; i++) {
			if (i % 100 == 0)
				System.out.println(i);
			exportImageToFile(tstData[i], Integer.toString(i));
		}
	}

	public static void init() throws Exception {
		tstData = new int[42000][28][28];
		BufferedReader br = new BufferedReader(new FileReader("res\\" + fileLoc));
		String s = br.readLine();
		String[] splts = null;
		for (int i = 0; i < 42000; i++) {
			s = br.readLine();
			splts = s.split(",");
			for (int r = 0; r < 28; r++)
				for (int c = 0; c < 28; c++) {
					tstData[i][r][c] = 255-Integer.parseInt(splts[r * 28 + c + 1]);
				}
		}
		br.close();
	}

	public static void exportImageToFile(int[][] rgbValue, String fileName) throws IOException {
		ImageIO.write(convertRGBImage(rgbValue), "png", new File("imgs\\" + fileName + ".png"));
	}

	public static BufferedImage convertRGBImage(int[][] rgbValue) {
		int height = rgbValue.length;
		int width = rgbValue[0].length;

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				int value = rgbValue[y][x] << 16 | rgbValue[y][x] << 8 | rgbValue[y][x];
				bufferedImage.setRGB(x, y, value);
			}
		return bufferedImage;
	}
}
