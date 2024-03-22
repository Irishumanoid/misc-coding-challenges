from PIL import Image
import math

def blur_pixel(index:tuple, image: Image):
    num_vals = 0
    rgb_sum = [0, 0, 0]
    for i in range(index[0]-1, index[0]+2):
        for j in range(index[1]-1, index[1]+2):
            if i >= 0 and j >= 0:
                rgb_sum[0] += image[index[0], index[1]][0]
                rgb_sum[1] += image[index[0], index[1]][1]
                rgb_sum[2] += image[index[0], index[1]][2]
                num_vals += 1
    rgb_av = tuple([round(entry/num_vals) for entry in rgb_sum])
    return rgb_av

def blur(in_path:str, out_path:str):
    image = Image.open(in_path)
    blur_image = Image.new(mode="RGB", size=(image.width, image.height), color="white")
    im_pixels = image.convert('RGB')

    for i in range(im_pixels.width):
        for j in range(im_pixels.height):
            print(blur_pixel((i, j), image.load()))
            blur_image.putpixel((i,j), blur_pixel((i, j), image.load()))

    blur_image.save(out_path)

blur("/Users/irislitiu/Documents/galaxy.jpg", "/Users/irislitiu/Documents/blurred.png")

        