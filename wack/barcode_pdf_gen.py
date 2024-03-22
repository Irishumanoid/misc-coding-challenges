from PIL import Image
from reportlab.pdfgen import canvas
from PIL import Image
import os
import barcode
from barcode.writer import ImageWriter
import math

def get_barcodes(all_ids):
    for i in range(len(all_ids)):   
        code = barcode.get('Code128', all_ids[i], writer=ImageWriter())
        filename = code.save('/Users/irislitiu/Documents/barcodes/'+str(i+2))

def get_files_in_dir(dir:str) -> [str]:
    all_files = []
    files = os.listdir(dir)
    for file in files:
        all_files.append(os.path.join("/Users/irislitiu/Documents/barcodes/", file))
    return all_files

def combine_images(image_paths, output_path):
    first_image = Image.open(image_paths[0])
    width, height = first_image.size

    combined_image = Image.new('RGBA', (width*5, height * len(image_paths)))

    for i, image_path in enumerate(image_paths):
        img = Image.open(image_path)
        combined_image.paste(img, (1*math.ceil(i/10), i * height))

    combined_image.save(output_path)


def fit_image_to_page(image_path, pdf_path):
    img = Image.open(image_path)

    pdf_width, pdf_height = img.size

    c = canvas.Canvas(pdf_path, pagesize=(pdf_width, pdf_height))
    c.drawImage(image_path, 0, 0, width=pdf_width, height=pdf_height)
    c.save()


#print(get_files_in_dir("/Users/irislitiu/Documents/barcodes/"))
combine_images(get_files_in_dir("/Users/irislitiu/Documents/barcodes/"), '/Users/irislitiu/Documents/combined_image.png')
#fit_image_to_page('/Users/irislitiu/Documents/combined_image.png', '/Users/irislitiu/Documents/output_document.pdf')
