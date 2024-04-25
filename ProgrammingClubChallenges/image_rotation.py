from PIL import Image
import numpy as np

def load_image(image_path) -> list:
    im_frame = Image.open(image_path)
    np_frame = np.array(im_frame)
    width, height = np_frame.shape[1], np_frame.shape[0]
    im_pixels = []
    for i in range(width):
        im_pixels.append([])
        for j in range(height):
            im_pixels[i].append(im_frame.getpixel((i, j)))
    print("width of arr is: " + str(len(im_pixels)))
    print("height of arr is: " + str(len(im_pixels[0])))
    return im_pixels

def rotate90Clockwise(A):
    A_new = A
    N = len(A_new[0])
    for i in range(N // 2):
        for j in range(i, N - i - 1):
            temp = A_new[i][j]
            A_new[i][j] = A_new[N - 1 - j][i]
            A_new[N - 1 - j][i] = A_new[N - 1 - i][N - 1 - j]
            A_new[N - 1 - i][N - 1 - j] = A_new[j][N - 1 - i]
            A_new[j][N - 1 - i] = temp
    return A_new

pixels = load_image('/Users/irislitiu/Desktop/test.png')
rotated = rotate90Clockwise(pixels)
width, height = len(rotated), len(rotated[0])
im = Image.new(mode="RGB", size=(width, height))
for i in range(width):
    for j in range(height):
        im.putpixel((i,j), rotated[i][j])

im.show("rotated image")



