from PIL import Image

# Specify the size of the image
image_width = 300
image_height = 200

# Create a new image with the specified size and a white background
image = Image.new(mode="RGB", size=(image_width, image_height), color="white")

# Specify the pixel coordinates and color
pixel_x = 50
pixel_y = 100
pixel_color = (255, 0, 0)  # RGB color, in this case, red

# Assign the color to the specified pixel
image.putpixel((pixel_x, pixel_y), pixel_color)

# Save the image to a file
image.save("output_image.png")

# Show the image (optional)
image.show()
