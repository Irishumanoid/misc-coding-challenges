import requests
import json
import re

url = 'https://www.googleapis.com/books/v1/volumes?q=intitle:hunger+games'
api_key = 'haha you\'ll never know...'

def get_data(url: str, write=False):
    try:
        res = requests.get(url=url, params={'api_key': api_key})
        out = json.loads(res.content)
        if write:
            with open('./out.json', 'w+') as f:
                json.dump(out, f, indent=4)
        return out
    except requests.exceptions.RequestException as e:
        print(f'error: {e}')

#data = get_data(url, write=False)
#print(data['items'][6]['volumeInfo']['description'])
print(re.search(r'^\d{4}', '2025-1-1').group())

'''
intitle: Returns results where the text following this keyword is found in the title.
inauthor: Returns results where the text following this keyword is found in the author.
inpublisher: Returns results where the text following this keyword is found in the publisher.
subject: Returns results where the text following this keyword is listed in the category list of the volume.
'''