package fr.sakad.chernogame;

public class Screen {
    private int width, height;
    public int[] pixels;

    public int xtime = 140;
    public int ytime = 140;
    public int counter;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height]; // 50,400
    }

    public void clear() {
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void render() {
        counter++;
        //if(counter % 50 == 0) {
            xtime++;
        //}

        if(counter % 100 == 0) {
//            ytime--;
        }

        /*if(ytime < 0 || ytime >= height || xtime < 0 || xtime >= width) {
            return;
        }*/

        if(xtime + (ytime * 300) < 0 || xtime + (ytime * width) >= height * width) {
            return;
        }
        pixels[xtime + (ytime * width)] = 0xff00ff;

/*        for (int y = 0; y < height; y++) {
            if(ytime >= height) {
                break;
            }
            for (int x = 0; x < width; x++) {
                if(xtime >= width) {
                    break;
                }
            }
        }
*/
        /*for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            }
        }*/
    }
}
